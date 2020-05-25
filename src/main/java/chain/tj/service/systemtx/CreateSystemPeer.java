package chain.tj.service.systemtx;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.MyTransaction;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.SystemTx;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

import static chain.tj.util.GmUtils.sm2Sign;
import static chain.tj.util.GmUtils.sm3Hash;
import static chain.tj.util.PeerUtil.*;
import static chain.tj.util.TjParseEncryptionKey.readKeyFromPem;
import static chain.tj.util.TransactionUtil.getTransactionDto;

/**
 * @Describe:节点变更
 * @Author: zhangyifei
 * @Date: 2020/5/22 11:31
 */
@Slf4j
@Service
public class CreateSystemPeer implements SystemTx {

    @Value("${peer.pubKey}")
    private String pubKey = "2c7f6f353d828e99692bb8bf960186f218674581495b399db753c00dd636c4f0583f7a833ce67d352e7d32be5d6e3fc899d7004efe1f450fc1a078ee856a8b75";

    @Override
    public RestResponse newTransaction(NewTxQueryDto newTxQueryDto) {
        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // 获取当前时间戳
        long currentTime = System.currentTimeMillis() / 1000;
        // long currentTime = 1590050482;

        // 根据subType获取TransactionDto
        TransactionDto transactionDto = getTransactionDtoBySubType(currentTime, newTxQueryDto);

        // 序列化PeerDto,并且给transactionDto赋值
        serialPeerTxDto(newTxQueryDto, peerPubKey, transactionDto);

        // 序列化transactionDto
        byte[] transactionDtoBytes = serialTransactionDto(transactionDto);
        // sm3加密
        byte[] hashVal = sm3Hash(transactionDtoBytes);

        log.info("hashVal的十六进制：{}", toHexString(hashVal));

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

        // PeerRequest: Transaction, PeerResponse:PeerResponse
        // 封装请求对象
        MyPeer.PeerRequest request = getPeerRequest(transactionDto, peerPubKey);

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(newTxQueryDto.getAddr(), newTxQueryDto.getRpcPort());
        // 调用接口
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);
        log.info("peerResponse--->{}", peerResponse);

        if (peerResponse.getOk()) {
            return RestResponse.success();
        }

        return RestResponse.failure("节点变更失败", StatusCode.SERVER_500000.value());
    }

    /**
     * 封装请求对象
     *
     * @param transactionDto
     * @param peerPubKey     链上的公钥
     * @return MyPeer.PeerRequest
     */
    private MyPeer.PeerRequest getPeerRequest(TransactionDto transactionDto, ByteString peerPubKey) {
        MyTransaction.TransactionHeader transactionHeader = MyTransaction.TransactionHeader.newBuilder()
                .setVersion(transactionDto.getTransactionHeader().getVersion())
                .setType(transactionDto.getTransactionHeader().getType())
                .setSubType(transactionDto.getTransactionHeader().getSubType())
                .setTimestamp(transactionDto.getTransactionHeader().getTimestamp())
                .setTransactionHash(ByteString.copyFrom(transactionDto.getTransactionHeader().getTransactionHash()))
                .build();

        MyTransaction.Transaction transaction = MyTransaction.Transaction.newBuilder()
                .setHeader(transactionHeader)
                .setPubkey(peerPubKey)
                .setData(ByteString.copyFrom(transactionDto.getData()))
                .setSign(ByteString.copyFrom(transactionDto.getSign()))
                .build();

        return MyPeer.PeerRequest.newBuilder()
                .setPubkey(peerPubKey)
                .setPayload(transaction.toByteString())
                .build();
    }

    /**
     * 序列化 TransactionDto
     *
     * @param transactionDto
     * @return
     */
    private byte[] serialTransactionDto(TransactionDto transactionDto) {
        System.out.println("transactionDto = " + transactionDto);
        ByteBuf buf = Unpooled.buffer();

        if (null != transactionDto.getTransactionHeader()) {
            if (null != transactionDto.getTransactionHeader().getVersion()) {
                buf.writeBytes(int2Bytes(transactionDto.getTransactionHeader().getVersion()));
            }
            if (null != transactionDto.getTransactionHeader().getType()) {
                buf.writeBytes(int2Bytes(transactionDto.getTransactionHeader().getType()));
            }
            if (null != transactionDto.getTransactionHeader().getSubType()) {
                buf.writeBytes(int2Bytes(transactionDto.getTransactionHeader().getSubType()));
            }
            if (null != transactionDto.getTransactionHeader().getTimestamp()) {
                buf.writeBytes(long2Bytes(transactionDto.getTransactionHeader().getTimestamp()));
            }
        }

        if (null != transactionDto.getData()) {
            buf.writeBytes(int2Bytes(transactionDto.getData().length));
            buf.writeBytes(transactionDto.getData());
        } else {
            buf.writeInt(0);
        }

        if (null != transactionDto.getExtra()) {
            buf.writeBytes(int2Bytes(transactionDto.getExtra().length));
            buf.writeBytes(transactionDto.getExtra());
        } else {
            buf.writeInt(0);
        }

        if (null != transactionDto.getPubKey()) {
            buf.writeBytes(int2Bytes(transactionDto.getPubKey().length));
            buf.writeBytes(transactionDto.getPubKey());
        } else {
            buf.writeInt(0);
        }
        buf.writeInt(0);

        byte[] bytes1 = new byte[buf.writerIndex()];

        byte[] array = buf.array();
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i] = array[i];
        }
        return bytes1;
    }

    /**
     * 序列化PeerDto,并且给transactionDto赋值
     *
     * @param newTxQueryDto
     * @param peerPubKey
     * @param transactionDto
     */
    private void serialPeerTxDto(NewTxQueryDto newTxQueryDto, ByteString peerPubKey, TransactionDto transactionDto) {
        PeerTxDto peerTxDto = new PeerTxDto();

        peerTxDto.setOpType(newTxQueryDto.getOpType());
        peerTxDto.setPeerType(newTxQueryDto.getPeerType());
        peerTxDto.setId(newTxQueryDto.getMemberId());
        peerTxDto.setShownName(newTxQueryDto.getShownName());
        peerTxDto.setLanAddrs(Arrays.asList(newTxQueryDto.getAddr()));
        peerTxDto.setWlanAddrs(Arrays.asList(newTxQueryDto.getAddr()));
        peerTxDto.setRpcPort(newTxQueryDto.getRpcPort());

        // 获取peerTxDtoBytes
        byte[] peerTxDtoBytes = getPeerTxDtoBytes(peerTxDto);

        // 序列化
        byte[] sysData = dataSerializable(peerTxDtoBytes, peerPubKey.toByteArray(), peerTxDto);
        log.info("sysData的十六进制：{}", toHexString(sysData));

        transactionDto.setData(sysData);
        transactionDto.setPubKey(peerPubKey.toByteArray());
    }


    /**
     * 根据subType获取TransactionDto
     *
     * @param currentTime
     * @param newTxQueryDto
     * @return
     */
    private TransactionDto getTransactionDtoBySubType(long currentTime, NewTxQueryDto newTxQueryDto) {
        TransactionDto transactionDto = getTransactionDto(currentTime);

        Integer opType = newTxQueryDto.getOpType();
        if (opType == 0) {
            transactionDto.getTransactionHeader().setSubType(0);
        } else {
            transactionDto.getTransactionHeader().setSubType(1);
        }

        return transactionDto;
    }

}
