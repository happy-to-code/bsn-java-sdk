package chain.tj.model.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Describe:合约信息查询
 * @Author: zhangyifei
 * @Date: 2020/6/1 18:01
 */
@Data
public class QuerySmartContractReq {

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
     * 其他参数
     */
    private List<Object> args;

    /**
     * 是否唯一
     */
    private Boolean isUnique;

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

    /**
     * 私钥地址路径
     */
    private String priKeyPath;


}
