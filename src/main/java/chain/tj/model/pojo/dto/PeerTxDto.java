package chain.tj.model.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 16:58
 */
@Data
public class PeerTxDto implements Serializable {

    private static final long serialVersionUID = 4908943075304554001L;
    /**
     * 操作类型，0-修改/新增；1-删除
     */
    private Integer opType;

    /**
     * 节点类型，0-共识节点；1-非共识节点
     */
    private Integer peerType;

    /**
     * peer ID
     */
    private String id;

    /**
     * 显示名
     */
    private String shownName;

    /**
     * 节点的Lan地址， 对应入站IP地址[inAddr]
     */
    private List<String> lanAddrs;

    /**
     * 节点的Wlan地址，对应出站IP地址[outAddr]
     */
    private List<String> wlanAddrs;

    /**
     * rpcPort
     */
    private Integer rpcPort;

}
