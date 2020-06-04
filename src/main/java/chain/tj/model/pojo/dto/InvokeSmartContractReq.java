package chain.tj.model.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/6/1 15:26
 */
@Data
public class InvokeSmartContractReq {

    /**
     * 名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 方法
     */
    private String method;

    /**
     * 调用人
     */
    private String caller;

    /**
     * 目录
     */
    private String category;

    /**
     * 账簿名称
     */
    private String ledgerName;

    /**
     * 其他参数
     */
    private List<Object> args;


    private Boolean isUnique;

    private List<String> scheduleTime;

    private Long number;

    private String ledger;


}
