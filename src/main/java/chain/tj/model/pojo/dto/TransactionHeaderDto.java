package chain.tj.model.pojo.dto;

import lombok.Data;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 15:57
 */
@Data
public class TransactionHeaderDto {
    private Integer version;
    private Integer type;
    private Integer subType;
    private Long timestamp;
    private byte[] transactionHash;

}
