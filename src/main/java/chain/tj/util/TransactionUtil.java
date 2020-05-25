package chain.tj.util;

import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.dto.TransactionHeaderDto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static chain.tj.util.PeerUtil.int2Bytes;
import static chain.tj.util.PeerUtil.long2Bytes;

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
}
