package chain.tj.model.pojo.dto.contract;

import lombok.Data;

import java.util.List;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/27 18:36
 */
@Data
public class ContractCallArg {

    /**
     * 合约方法
     */
    private String method;

    /**
     * 合约调用执行费用——目前默认为1000000，目的是为了防止合约代码中出现死循环和保证不同机器合约执行结果一致
     */
    private Integer gas;

    /**
     * 合约调用人
     */
    private byte[] caller;

    /**
     * 合约版本号
     */
    private String version;

    /**
     * 传入合约方法的参数
     */
    private List<Object> args;

}
