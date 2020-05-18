package chain.tj.model.pojo.dto;

import lombok.Data;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 16:08
 */
@Data
public class TransactionDto {

    private TransactionHeaderDto transactionHeader;
    private byte[] data;
    private byte[] pubKey;
    private byte[] sign;
    private byte[] result;
    private byte[] extra;
}
