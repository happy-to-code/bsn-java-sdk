package chain.tj.model.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 区块头部信息
 *
 * @author zhangyifei
 */
@Data
public class BlockInfoVo {
    private Integer version;
    private Long height;
    private Long timestamp;
    private String blockHash;
    private String previousHash;
    private String worldStateRoot;
    private String transactionRoot;

    private List<String> txs;
    private String extra;
}