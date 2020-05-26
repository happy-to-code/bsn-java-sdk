package chain.tj.service.systemtx;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.SysContractStatusTxDto;
import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.dto.TransactionHeaderDto;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.SystemTx;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static chain.tj.util.GmUtils.sm2Sign;
import static chain.tj.util.GmUtils.sm3Hash;
import static chain.tj.util.PeerUtil.*;
import static chain.tj.util.PeerUtil.int2Bytes;
import static chain.tj.util.TjParseEncryptionKey.readKeyFromPem;
import static chain.tj.util.TransactionUtil.*;

/**
 * @Describe: 冻结或者激活合约
 * @Author: zhangyifei
 * @Date: 2020/5/25 18:20
 */
@Service
@Slf4j
public class CreateSystemFASC implements SystemTx {

    @Value("${peer.pubKey}")
    private String pubKey = "2c7f6f353d828e99692bb8bf960186f218674581495b399db753c00dd636c4f0583f7a833ce67d352e7d32be5d6e3fc899d7004efe1f450fc1a078ee856a8b75";


    /**
     * 冻结或者激活合约
     *
     * @param newTxQueryDto
     * @return
     */
    @Override
    public RestResponse newTransaction(NewTxQueryDto newTxQueryDto) {
        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // 获取当前时间戳
        long currentTime = System.currentTimeMillis() / 1000;

        // 构建TransactionDto对象
        TransactionDto transactionDto = createTransactionDto(peerPubKey, currentTime, newTxQueryDto.getSysContractStatusTxDto());

        // 获取SysContractStatusTxDto对象的字节对象
        byte[] sysContractStatusTxDtoBytes = getSysContractStatusTxDtoBytes(newTxQueryDto.getSysContractStatusTxDto());

        // 获取sysData字节数组
        byte[] sysData = getSysData(peerPubKey, sysContractStatusTxDtoBytes, newTxQueryDto.getSysContractStatusTxDto().getOp());

        // sysData字节数组set进transactionDto对象
        transactionDto.setData(sysData);

        // 序列化transactionDto
        byte[] transactionDtoBytes = serialTransactionDto(transactionDto);

        // sm3加密
        byte[] hashVal = sm3Hash(transactionDtoBytes);

        byte[] priKeyBytes = new byte[0];
        try {
            priKeyBytes = readKeyFromPem("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] signBytes = new byte[0];
        try {
            signBytes = sm2Sign(priKeyBytes, transactionDtoBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        transactionDto.getTransactionHeader().setTransactionHash(hashVal);
        transactionDto.setSign(signBytes);

        MyPeer.PeerRequest request = getPeerRequest(transactionDto, peerPubKey);

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(newTxQueryDto.getAddr(), newTxQueryDto.getRpcPort());
        // 调用接口
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);
        log.info("peerResponse--->{}", peerResponse);




        return null;
    }

    /**
     * 获取sysData字节数组
     *
     * @param peerPubKey
     * @param sysContractStatusTxDtoBytes
     * @param op
     * @return
     */
    private byte[] getSysData(ByteString peerPubKey, byte[] sysContractStatusTxDtoBytes, String op) {
        ByteBuf buf = Unpooled.buffer();

        buf.writeBytes(int2Bytes(peerPubKey.toByteArray().length));
        buf.writeBytes(peerPubKey.toByteArray());

        if (StringUtils.equals("F", op)) {
            buf.writeBytes(int2Bytes(9));
        } else if (StringUtils.equals("A", op)) {
            buf.writeBytes(int2Bytes(10));
        }

        // 写入数据
        buf.writeBytes(int2Bytes(sysContractStatusTxDtoBytes.length));
        buf.writeBytes(sysContractStatusTxDtoBytes);

        return convertBuf(buf);
    }


    /**
     * 获取SysContractStatusTxDto对象的字节对象
     *
     * @param sysContractStatusTxDto
     * @return
     */
    private byte[] getSysContractStatusTxDtoBytes(SysContractStatusTxDto sysContractStatusTxDto) {
        ByteBuf buf = Unpooled.buffer();

        if (StringUtils.isNoneBlank(sysContractStatusTxDto.getName())) {
            buf.writeBytes(int2Bytes(sysContractStatusTxDto.getName().length()));
            buf.writeBytes(sysContractStatusTxDto.getName().getBytes());
        }

        if (StringUtils.isNoneBlank(sysContractStatusTxDto.getVersion())) {
            buf.writeBytes(int2Bytes(sysContractStatusTxDto.getVersion().length()));
            buf.writeBytes(sysContractStatusTxDto.getVersion().getBytes());
        }

        if (StringUtils.isNoneBlank(sysContractStatusTxDto.getOp())) {
            buf.writeBytes(int2Bytes(sysContractStatusTxDto.getOp().length()));
            buf.writeBytes(sysContractStatusTxDto.getOp().getBytes());
        }

        return convertBuf(buf);

    }

    /**
     * 构建TransactionDto对象
     *
     * @param peerPubKey
     * @param currentTime
     * @param sysContractStatusTxDto
     * @return
     */
    private TransactionDto createTransactionDto(ByteString peerPubKey, long currentTime, SysContractStatusTxDto sysContractStatusTxDto) {
        TransactionDto transactionDto = new TransactionDto();

        TransactionHeaderDto transactionHeaderDto = new TransactionHeaderDto();
        transactionHeaderDto.setVersion(0);
        transactionHeaderDto.setType(4);
        transactionHeaderDto.setSubType(0);
        transactionHeaderDto.setTimestamp(currentTime);

        if (StringUtils.equals("F", sysContractStatusTxDto.getOp())) {
            transactionHeaderDto.setSubType(9);
        } else if (StringUtils.equals("A", sysContractStatusTxDto.getOp())) {
            transactionHeaderDto.setSubType(10);
        }


        transactionDto.setTransactionHeader(transactionHeaderDto);
        transactionDto.setPubKey(peerPubKey.toByteArray());

        return transactionDto;

    }
}
