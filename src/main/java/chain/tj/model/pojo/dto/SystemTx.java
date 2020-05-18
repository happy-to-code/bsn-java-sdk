package chain.tj.model.pojo.dto;

import lombok.Data;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 18:36
 */
@Data
public class SystemTx {
    private byte[] pubKey;
    private byte[] systemtx;
}
