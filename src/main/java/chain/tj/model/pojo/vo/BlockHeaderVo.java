package chain.tj.model.pojo.vo;

import lombok.Data;

/**
 * 区块头部信息
 *
 * @author zhangyifei
 */
@Data
public class BlockHeaderVo {
    private Integer version;
    private Long height;
    private Long timestamp;
    private String blockHash;
    private String previousHash;
    private String worldStateRoot;
    private String transactionRoot;
}