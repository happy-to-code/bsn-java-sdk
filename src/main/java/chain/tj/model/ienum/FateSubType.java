package chain.tj.model.ienum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Describe:40-49 Fate智能合约交易子类
 * @Author: zhangyifei
 * @Date: 2020/6/1 13:16
 */
@Getter
@AllArgsConstructor
public enum FateSubType {

    WVMSCInstall(40, "Install"),
    WVMSCDest(41, "Dest"),
    WVMSCInvoke(42, "Invoke");


    /**
     * 值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;
}
