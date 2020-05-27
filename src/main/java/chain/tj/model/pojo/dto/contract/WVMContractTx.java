package chain.tj.model.pojo.dto.contract;

import lombok.Data;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/27 18:43
 */
@Data
public class WVMContractTx {

    /**
     * 合约调用相关参数
     */
    private ContractCallArg arg;

    /**
     * 合约名
     */
    private String name;

    /**
     * 合约持有人
     */
    private byte[] owner;

    /**
     * 随机数
     */
    private byte[] random;

    /**
     * 合约源码
     */
    private byte[] src;

    /**
     * 合约持有人私钥签名
     */
    private byte[] sign;

}
