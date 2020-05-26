package chain.tj.model.pojo.query;

import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.pojo.dto.PermissionTxDto;
import chain.tj.model.pojo.dto.SubLedgerTxDto;
import chain.tj.model.pojo.dto.SysContractStatusTxDto;
import lombok.Data;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 17:11
 */
@Data
public class NewTxQueryDto {

    /**
     * ip地址
     */
    private String addr;

    /**
     * 端口
     */
    private Integer rpcPort;

    /**
     * 节点变更参数对象
     */
    private PeerTxDto peerTxDto;

    /**
     * 权限变更参数对象
     */
    private PermissionTxDto permissionTxDto;

    /**
     * 子链变更参数对象
     */
    private SubLedgerTxDto subLedgerTxDto;

    /**
     * 冻结或者激活合约参数对象
     */
    private SysContractStatusTxDto sysContractStatusTxDto;

}
