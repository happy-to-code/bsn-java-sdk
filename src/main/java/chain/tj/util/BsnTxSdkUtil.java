package chain.tj.util;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.*;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Block;
import chain.tj.service.Contract;
import chain.tj.service.Member;
import chain.tj.service.SystemTx;
import chain.tj.service.block.BlockInfo;
import chain.tj.service.contract.SmartContract;
import chain.tj.service.memberinfo.MemberInfo;
import chain.tj.service.systemtx.CreateSystemPeer;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chain.tj.util.PeerBasicUtil.getCommonData;
import static chain.tj.util.PeerBasicUtil.getStubList;

/**
 * @Describe: 系统消息创建工具类
 * @Author: zhangyifei
 * @Date: 2020/5/22 15:06
 */
public class BsnTxSdkUtil {
    public static void main(String[] args) {
        // 这边创建时是多态
        SystemTx systemTx = new CreateSystemPeer();
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();

        PeerTxDto peerTxDto = new PeerTxDto();
        peerTxDto.setPeerType(0);
        peerTxDto.setOpType(0);
        peerTxDto.setRpcPort(9008);
        peerTxDto.setWlanAddrs(Arrays.asList("/ip4/10.1.13.150/tcp/60005"));
        peerTxDto.setLanAddrs(Arrays.asList("/ip4/10.1.13.151/tcp/60005"));
        peerTxDto.setId("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3");
        peerTxDto.setShownName("myName");

        newTxQueryDto.setPeerTxDto(peerTxDto);


        List<PeerConnectionDto> connectionDtoList = new ArrayList<>(10);
        connectionDtoList.add(new PeerConnectionDto("10.1.3.151", 9008));
        connectionDtoList.add(new PeerConnectionDto("10.1.3.150", 9008));
        connectionDtoList.add(new PeerConnectionDto("10.1.3.152", 9008));
        List<PeerGrpc.PeerBlockingStub> stubList = getStubList(connectionDtoList);


        PeerCommonDataDto dataDto = new PeerCommonDataDto();
        dataDto.setPriKeyFilePath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        dataDto.setPubKeyFilePath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem");
        dataDto.setWvmFilePath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\transfer.wlang");
        TxCommonDataVo commonData = getCommonData(dataDto);
        RestResponse restResponse = newSysTransaction(systemTx, stubList, commonData, newTxQueryDto);
        System.out.println("restResponse = " + restResponse);
        //    ==========================================================
    }

    /**
     * 创建交易
     *
     * @param systemTx
     * @param newTxQueryDto
     * @return
     */
    public static RestResponse newSysTransaction(SystemTx systemTx, List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, NewTxQueryDto newTxQueryDto) {
        return systemTx.newTransaction(stubList, txCommonDataVo, newTxQueryDto);
    }

    /**
     * 安装智能合约
     *
     * @param stubList
     * @param txCommonDataVo
     * @param contractReq
     * @return
     */
    public static RestResponse installSmartContract(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, ContractReq contractReq) {
        Contract contract = new SmartContract();
        return contract.installSmartContract(stubList, txCommonDataVo, contractReq);
    }

    /**
     * 安装智能合约
     *
     * @param contractReq
     * @return
     */
    public static RestResponse destorySmartContract(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, ContractReq contractReq) {
        Contract contract = new SmartContract();
        return contract.destorySmartContract(stubList, txCommonDataVo, contractReq);
    }

    /**
     * 调用智能合约
     *
     * @param invokeSmartContractReq
     * @return
     */
    public static RestResponse invokeSmartContract(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, InvokeSmartContractReq invokeSmartContractReq) {
        Contract contract = new SmartContract();
        return contract.invokeSmartContract(stubList, txCommonDataVo, invokeSmartContractReq);
    }

    /**
     * 查询智能合约信息
     *
     * @param querySmartContractReq
     * @return
     */
    public static RestResponse querySmartContract(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, QuerySmartContractReq querySmartContractReq) {
        Contract contract = new SmartContract();
        return contract.querySmartContract(stubList, txCommonDataVo, querySmartContractReq);
    }

    /**
     * 获取节点状态数据
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @return
     * @throws InvalidProtocolBufferException
     */
    public static RestResponse getMemberList(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo) throws InvalidProtocolBufferException {
        Member member = new MemberInfo();
        return member.getMemberList(stubList, txCommonDataVo);
    }

    /**
     * 获取区块高度
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @return
     */
    public static RestResponse blockHeight(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo) {
        Block block = new BlockInfo();
        return block.blockHeight(stubList, txCommonDataVo);
    }

    /**
     * 根据区块高度区块信息
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @param height
     * @return
     */
    public static RestResponse getBlockByHeight(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, Integer height) {
        Block block = new BlockInfo();
        return block.getBlockByHeight(stubList, txCommonDataVo, height);
    }

    /**
     * 根据hash值查询区块信息
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @param hash
     * @return
     */
    public static RestResponse getBlockByHash(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, String hash) {
        Block block = new BlockInfo();
        return block.getBlockByHash(stubList, txCommonDataVo, hash);
    }

}
