package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/26 15:02
 */
public interface Member {

    /**
     * 获取节点信息
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @return
     * @throws InvalidProtocolBufferException
     */
    RestResponse getMemberList(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo) throws InvalidProtocolBufferException;
}
