package chain.tj.model.pojo.dto;

import lombok.Data;

/**
 * @Describe: 公用数据转换对象
 * @Author: zhangyifei
 * @Date: 2020/6/3 17:53
 */
@Data
public class PeerCommonDataDto {
    /**
     * 公钥文件路径
     */
    private String pubKeyFilePath;

    /**
     * 私钥文件路径
     */
    private String priKeyFilePath;

    /**
     * 合约文件路径
     */
    private String wvmFilePath;
}
