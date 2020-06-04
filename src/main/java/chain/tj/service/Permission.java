package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;

import java.util.List;

/**
 * @Describe:权限相关
 * @Author: zhangyifei
 * @Date: 2020/6/1 14:42
 */
public interface Permission {
    /**
     * 获取节点信息
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @return
     */
    RestResponse getPeerPermissions(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo);
}
