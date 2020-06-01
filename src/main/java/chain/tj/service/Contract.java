package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.ContractReq;
import chain.tj.model.pojo.dto.InvokeSmartContractReq;

/**
 * @Describe:合约相关
 * @Author: zhangyifei
 * @Date: 2020/5/28 14:53
 */
public interface Contract {

    /**
     * 安装智能合约
     *
     * @param contractReq
     * @return
     */
    RestResponse installSmartContract(ContractReq contractReq);


    /**
     * 销毁合约
     *
     * @param contractReq
     * @return
     */
    RestResponse destorySmartContract(ContractReq contractReq);

    /**
     * 调用合约
     *
     * @param invokeSmartContractReq
     * @return
     */
    RestResponse invokeSmartContract(InvokeSmartContractReq invokeSmartContractReq);

}
