package chain.tj.model.pojo.dto;

import lombok.Data;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/28 15:03
 */
@Data
public class ContractReq {

    /**
     * 名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;


    /**
     * 类别
     */
    private String category;

    /**
     * 账簿
     */
    private String ledger;


}
