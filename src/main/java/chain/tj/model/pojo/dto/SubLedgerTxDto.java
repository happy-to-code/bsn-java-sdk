package chain.tj.model.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/25 16:40
 */
@Data
public class SubLedgerTxDto implements Serializable {

    private static final long serialVersionUID = 7097079678551212572L;

    /**
     * 操作，0-修改/新增；1-删除
     */
    private Integer opType;

    /**
     * 子链名称
     */
    private String name;

    /**
     * 包含的节点ID，如非新建操作，参数可省略
     */
    private List<String> members;

    /**
     * hash算法（默认是sm3）
     */
    private String hashType;

    /**
     * 世区块备注内容,如非新建操作，参数可省略
     */
    private String word;

    /**
     * 共识算法,如非新建操作，参数可省略
     */
    private String cons;

    /**
     * 发送方公钥
     */
    private byte[] pubKeyFromSend;

    /**
     * 时间戳
     */
    private Long timeStamp;


}
