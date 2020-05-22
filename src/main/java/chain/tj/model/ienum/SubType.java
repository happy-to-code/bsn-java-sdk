package chain.tj.model.ienum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Describe:系统子交易类型定义
 * @Author: zhangyifei
 * @Date: 2020/5/18 14:43
 */
@Getter
@AllArgsConstructor
public enum SubType {

    M_Peer(0, "修改节点"),
    D_Peer(1, "删除节点"),
    // D_Peer(2,"删除节点),
    Permission(3, "权限交易"),
    C_SubLedger(4, "创建子链"),
    F_SubLedger(5, "冻结子链"),
    UF_SubLedger(6, "解冻子链"),
    D_SubLedger(7, "销毁子链");

    /**
     * 值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;
}
