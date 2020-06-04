package chain.tj.model.pojo.vo;

import lombok.Data;

import java.util.Map;

/**
 * @Describe:交易公共数据对象
 * @Author: zhangyifei
 * @Date: 2020/6/3 17:59
 */
@Data
public class TxCommonDataVo {
    /**
     * 公钥字节数据
     */
    private byte[] pubKeyByte;

    /**
     * 公私钥对和签名字节数据（默认键值是：priKey、pubKey和sign）
     * 此处的公钥是通过私钥推到出来的
     */
    private Map<String, byte[]> keyPairAndSign;

    /**
     * wvm合约文件字节数据
     */
    private byte[] wvmFileBytes;
}
