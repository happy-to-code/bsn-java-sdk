package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;

import java.util.List;

/**
 * @Describe:区块相关
 * @Author: zhangyifei
 * @Date: 2020/5/26 17:12
 */
public interface Block {

    /**
     * 获取区块高度
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @return
     */
    RestResponse blockHeight(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo);

    /**
     * 根据区块高度区块信息
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @param height
     * @return
     */
    RestResponse getBlockByHeight(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, Integer height);

    /**
     * 根据hash值查询区块信息
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @param hash
     * @return
     */
    RestResponse getBlockByHash(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, String hash);

}
