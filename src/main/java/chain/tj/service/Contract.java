package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.ContractReq;
import chain.tj.model.pojo.dto.InvokeSmartContractReq;
import chain.tj.model.pojo.dto.QuerySmartContractReq;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;

import java.util.List;

/**
 * @Describe:合约相关
 * @Author: zhangyifei
 * @Date: 2020/5/28 14:53
 */
public interface Contract {

    /**
     * 安装智能合约
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @param contractReq
     * @return
     */
    RestResponse installSmartContract(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, ContractReq contractReq);

    /**
     * 销毁合约
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @param contractReq
     * @return
     */
    RestResponse destorySmartContract(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, ContractReq contractReq);

    /**
     * 调用合约
     *
     * @param stubList               连接数组
     * @param txCommonDataVo         交易公共数据对象
     * @param invokeSmartContractReq
     * @return
     */
    RestResponse invokeSmartContract(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, InvokeSmartContractReq invokeSmartContractReq);


    /**
     * 查询合约信息
     *
     * @param stubList              连接数组
     * @param txCommonDataVo        交易公共数据对象
     * @param querySmartContractReq
     * @return
     */
    RestResponse querySmartContract(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, QuerySmartContractReq querySmartContractReq);

}
