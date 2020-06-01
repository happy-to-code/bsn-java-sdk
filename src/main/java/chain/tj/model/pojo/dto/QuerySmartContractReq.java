package chain.tj.model.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/6/1 18:01
 */
@Data
public class QuerySmartContractReq {
    private String name;
    private String version;
    private String method;
    private String caller;
    private String category;
    private List<Object> args;
    private Boolean isUnique;

    private String ledger;

}
