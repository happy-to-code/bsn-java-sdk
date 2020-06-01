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
    private String name;
    private String version;
    private String method;
    private String caller;
    private String category;
    private String ledgerName;
    private List<Object> args;



    private Boolean isUnique;

    private List<String> scheduleTime;

    private Long number;

    private String ledger;


}
