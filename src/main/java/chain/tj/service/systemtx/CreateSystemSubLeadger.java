package chain.tj.service.systemtx;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.SubLedgerTxDto;
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

import static chain.tj.util.PeerUtil.*;
import static chain.tj.util.TransactionUtil.*;

/**
 * @Describe:子链变更
 * @Author: zhangyifei
 * @Date: 2020/5/25 16:18
 */
@Slf4j
@Service
public class CreateSystemSubLeadger implements SystemTx {

    @Value("${peer.pubKey}")
    private String pubKey = "2c7f6f353d828e99692bb8bf960186f218674581495b399db753c00dd636c4f0583f7a833ce67d352e7d32be5d6e3fc899d7004efe1f450fc1a078ee856a8b75";


    /**
     * 子链变更
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
        // long currentTime = 1590390219;

        // 给subLedgerTxDto赋值
        newTxQueryDto.getSubLedgerTxDto().setTimeStamp(currentTime);
        newTxQueryDto.getSubLedgerTxDto().setPubKeyFromSend(peerPubKey.toByteArray());

        // 构建参数对象
        TransactionDto transactionDto = createTransactionDto(currentTime, peerPubKey.toByteArray(), newTxQueryDto.getSubLedgerTxDto().getOpType());

        // 序列化SubLedgerTxDto
        byte[] subLedgerTxDtoBytes = getSubLedgerTxDtoBytes(newTxQueryDto.getSubLedgerTxDto());

        // 序列化获取sysData
        byte[] sysData = getSubLedgerSysData(peerPubKey.toByteArray(), subLedgerTxDtoBytes, newTxQueryDto.getSubLedgerTxDto().getOpType());

        // 给transactionDto 赋值
        transactionDto.setData(sysData);

        // 给TransactionDto对象赋值
        setValueForTransactionDto(transactionDto);

        // 构建grpc参数
        MyPeer.PeerRequest request = getPeerRequest(transactionDto, peerPubKey);

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(newTxQueryDto.getAddr(), newTxQueryDto.getRpcPort());
        // 调用接口
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);
        log.info("peerResponse--->{}", peerResponse);

        return null;
    }

    /**
     * 序列化获取sysData
     *
     * @param peerPubKey
     * @param subLedgerTxDtoBytes
     * @param opType
     * @return
     */
    private byte[] getSubLedgerSysData(byte[] peerPubKey, byte[] subLedgerTxDtoBytes, Integer opType) {
        ByteBuf buf = Unpooled.buffer();

        buf.writeBytes(int2Bytes(peerPubKey.length));
        buf.writeBytes(peerPubKey);

        if (0 == opType) {
            buf.writeBytes(int2Bytes(4));
        } else if (1 == opType) {
            buf.writeBytes(int2Bytes(6));
        } else if (2 == opType) {
            buf.writeBytes(int2Bytes(5));
        } else if (3 == opType) {
            buf.writeBytes(int2Bytes(7));
        }

        buf.writeBytes(int2Bytes(subLedgerTxDtoBytes.length));
        buf.writeBytes(subLedgerTxDtoBytes);

        return convertBuf(buf);
    }

    /**
     * 序列化SubLedgerTxDto
     *
     * @param subLedgerTxDto
     * @return
     */
    private byte[] getSubLedgerTxDtoBytes(SubLedgerTxDto subLedgerTxDto) {
        ByteBuf buf = Unpooled.buffer();

        buf.writeBytes(int2Bytes(subLedgerTxDto.getOpType()));

        if (StringUtils.isNoneBlank(subLedgerTxDto.getName())) {
            buf.writeBytes(int2Bytes(subLedgerTxDto.getName().length()));
            buf.writeBytes(subLedgerTxDto.getName().getBytes());
        }

        if (subLedgerTxDto.getMembers() != null && subLedgerTxDto.getMembers().size() > 0) {
            buf.writeBytes(int2Bytes(subLedgerTxDto.getMembers().size()));
            for (String member : subLedgerTxDto.getMembers()) {
                buf.writeBytes(int2Bytes(member.length()));
                buf.writeBytes(member.getBytes());
            }
        } else {
            buf.writeInt(0);
        }


        if (StringUtils.isNoneBlank(subLedgerTxDto.getHashType())) {
            buf.writeBytes(int2Bytes(subLedgerTxDto.getHashType().length()));
            buf.writeBytes(subLedgerTxDto.getHashType().getBytes());
        }

        if (StringUtils.isNoneBlank(subLedgerTxDto.getWord())) {
            buf.writeBytes(int2Bytes(subLedgerTxDto.getWord().length()));
            buf.writeBytes(subLedgerTxDto.getWord().getBytes());
        }

        if (StringUtils.isNoneBlank(subLedgerTxDto.getCons())) {
            buf.writeBytes(int2Bytes(subLedgerTxDto.getCons().length()));
            buf.writeBytes(subLedgerTxDto.getCons().getBytes());
        }

        buf.writeBytes(int2Bytes(subLedgerTxDto.getPubKeyFromSend().length));
        buf.writeBytes(subLedgerTxDto.getPubKeyFromSend());

        buf.writeBytes(long2Bytes(subLedgerTxDto.getTimeStamp()));

        return convertBuf(buf);
    }

    /**
     * 构建参数对象
     *
     * @param currentTime
     * @param peerPubKey
     * @param opType
     * @return
     */
    private TransactionDto createTransactionDto(long currentTime, byte[] peerPubKey, Integer opType) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setPubKey(peerPubKey);

        TransactionHeaderDto transactionHeaderDto = new TransactionHeaderDto();
        transactionHeaderDto.setVersion(0);
        transactionHeaderDto.setType(4);
        transactionHeaderDto.setSubType(2);
        transactionHeaderDto.setTimestamp(currentTime);

        transactionDto.setTransactionHeader(transactionHeaderDto);

        if (0 == opType) {
            transactionHeaderDto.setSubType(4);
        } else if (1 == opType) {
            transactionHeaderDto.setSubType(5);
        } else if (2 == opType) {
            transactionHeaderDto.setSubType(6);
        } else if (3 == opType) {
            transactionHeaderDto.setSubType(7);
        }


        return transactionDto;
    }
}
