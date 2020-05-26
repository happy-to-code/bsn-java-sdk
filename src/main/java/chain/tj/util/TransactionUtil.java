package chain.tj.util;

import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.dto.TransactionHeaderDto;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.MyTransaction;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

import static chain.tj.util.GmUtils.sm2Sign;
import static chain.tj.util.GmUtils.sm3Hash;
import static chain.tj.util.PeerUtil.int2Bytes;
import static chain.tj.util.PeerUtil.long2Bytes;
import static chain.tj.util.TjParseEncryptionKey.readKeyFromPem;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 16:50
 */
public class TransactionUtil {
    /**
     * 获取基础TransactionDto
     *
     * @param currentTime
     * @return
     */
    public static TransactionDto getTransactionDto(long currentTime) {
        // 创建返回对象
        TransactionDto transactionDto = new TransactionDto();
        // 创建头对象
        TransactionHeaderDto transactionHeaderDto = new TransactionHeaderDto();
        transactionHeaderDto.setTimestamp(currentTime);
        // 默认版本
        transactionHeaderDto.setVersion(0);
        transactionHeaderDto.setType(4);
        transactionHeaderDto.setSubType(0);

        transactionDto.setTransactionHeader(transactionHeaderDto);
        return transactionDto;
    }

    /**
     * 转换buf
     *
     * @param buf
     * @return
     */
    public static byte[] convertBuf(ByteBuf buf) {
        byte[] bytes1 = new byte[buf.writerIndex()];

        byte[] array = buf.array();
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i] = array[i];
        }
        return bytes1;
    }

    /**
     * 序列化transactionDto
     *
     * @param transactionDto
     * @return
     */
    public static byte[] serialTransactionDto(TransactionDto transactionDto) {
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

        byte[] bytesReturn = new byte[buf.writerIndex()];

        byte[] array = buf.array();
        for (int i = 0; i < bytesReturn.length; i++) {
            bytesReturn[i] = array[i];
        }
        return bytesReturn;
    }

    /**
     * 封装请求对象
     *
     * @param transactionDto
     * @param peerPubKey     链上的公钥
     * @return MyPeer.PeerRequest
     */
    public static MyPeer.PeerRequest getPeerRequest(TransactionDto transactionDto, ByteString peerPubKey) {
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
     * 给TransactionDto对象赋值
     *
     * @param transactionDto
     */
    public static void setValueForTransactionDto(TransactionDto transactionDto) {
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
    }


}
