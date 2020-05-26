package chain.tj.model.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/25 18:26
 */
@Data
public class SysContractStatusTxDto implements Serializable {
    private static final long serialVersionUID = -3275698575721010496L;

    /**
     * 合约名
     */
    private String name;

    /**
     * 合约版本
     */
    private String version;

    /**
     * 操作符——F:冻结,A:激活
     */
    private String op;


    private String rpcAddr;
}
