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
     * 私钥路径
     */
    private String priKeyPath;

    /**
     * 类别
     */
    private String category;

    /**
     * 合约文件路径
     */
    private String filePath;

    /**
     * 账簿
     */
    private String ledger;

    /**
     * ip地址
     */
    private String addr;

    /**
     * 端口
     */
    private Integer port;

}
