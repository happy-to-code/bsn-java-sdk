package chain.tj.model.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/25 10:38
 */
@Data
public class PermissionTxDto implements Serializable {
    private static final long serialVersionUID = 5487075255184746293L;
    /**
     * peer ID
     */
    private String peerId;

    /**
     * 权限
     */
    private List<Integer> pm;

    /**
     * 显示名
     */
    private String shownName;


}
