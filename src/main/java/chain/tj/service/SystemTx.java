package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;

import java.util.List;

/**
 * @Describe:系统类型交易接口
 * @Author: zhangyifei
 * @Date: 2020/5/22 11:08
 */
public interface SystemTx {

    /**
     * 创建相关
     *
     * @param newTxQueryDto
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @return
     */
    RestResponse newTransaction(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, NewTxQueryDto newTxQueryDto);
}
