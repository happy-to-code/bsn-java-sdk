package chain.tj.model.pojo.dto;

import lombok.Data;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/28 15:03
 */
@Data
public class ContractReq {

    private String name;
    private String version;
    private String priKey;
    private String category;
    private String file;

    private String ledger;

}
