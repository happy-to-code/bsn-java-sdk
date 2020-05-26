package chain.tj.model.pojo.query;

import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.pojo.dto.SubLedgerTxDto;
import chain.tj.model.pojo.dto.SysContractStatusTxDto;
import lombok.Data;

import java.util.List;

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
     * memberID
     */
    private String memberId;

    /**
     * 操作类型，0-修改/新增；1-删除
     */
    private Integer opType;

    /**
     * rpc地址
     */
    private String peer;


    /**
     * 链的类型  类型(0-共识;1-非共识)
     */
    private Integer peerType;

    /**
     * 端口
     */
    private Integer rpcPort;

    //========================================
    /**
     * 类型
     */
    private Integer type;

    /**
     * 子类型
     */
    private Integer subType;

    /**
     * 展示的名称
     */
    private String shownName;

    //==========================================
    /**
     * 子链名称
     */
    private String subPeerName;

    /**
     * rpc地址 (default "10.1.3.157:9000")
     */
    private String rpcAddr;

    /**
     * 修改权限
     */
    private List<Integer> permission;

    //==========================================
    private PeerTxDto peerTxDto;

    private SubLedgerTxDto subLedgerTxDto;

    private SysContractStatusTxDto sysContractStatusTxDto;


    public NewTxQueryDto() {
        // 默认是新增或者修改
        this.opType = 0;
    }

}
