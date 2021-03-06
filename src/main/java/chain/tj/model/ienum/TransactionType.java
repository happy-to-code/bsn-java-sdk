package chain.tj.model.ienum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Describe:版本信息
 * @Author: zhangyifei
 * @Date: 2020/6/1 11:24
 */
@Getter
@AllArgsConstructor
public enum TransactionType {

    Store(0, " 存证交易"),
    UTXO(1, " UTXO交易"),
    SCDocker(2, " 智能合约Docker交易"),
    SCWVM(3, " WVM智能合约交易"),
    System(4, "系统交易"),
    Admin(20, " 链管理类交易"),
    Store_9(30, "未知类型");

    /**
     * 值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;
}
