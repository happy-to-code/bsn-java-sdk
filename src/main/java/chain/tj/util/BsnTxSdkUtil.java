package chain.tj.util;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.ContractReq;
import chain.tj.model.pojo.dto.InvokeSmartContractReq;
import chain.tj.model.pojo.dto.QuerySmartContractReq;
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
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe: 系统消息创建工具类
 * @Author: zhangyifei
 * @Date: 2020/5/22 15:06
 */
public class BsnTxSdkUtil {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        // SystemTx systemTx = new CreateSystemPeer();
        // NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        //
        // PeerTxDto peerTxDto = new PeerTxDto();
        // peerTxDto.setPeerType(0);
        // peerTxDto.setOpType(0);
        // peerTxDto.setRpcPort(9008);
        // peerTxDto.setWlanAddrs(Arrays.asList("/ip4/10.1.13.150/tcp/60005"));
        // peerTxDto.setLanAddrs(Arrays.asList("/ip4/10.1.13.151/tcp/60005"));
        // peerTxDto.setId("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3");
        // peerTxDto.setShownName("myName");
        //
        // newTxQueryDto.setPeerTxDto(peerTxDto);
        // RestResponse restResponse = newSysTransaction(systemTx, newTxQueryDto);
        // System.out.println("restResponse = " + restResponse);
        //    ==========================================================
        // Contract contract = new SmartContract();
        // ContractReq c = new ContractReq();
        // c.setCategory("wvm");
        // c.setName("abc");
        // c.setFilePath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\transfer.wlang");
        // c.setPriKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        // c.setAddr("10.1.3.150");
        // c.setPort(9008);
        //
        // RestResponse restResponse = installSmartContract(contract, c);
        // System.out.println(restResponse);

        //    =======================================
        // Contract contract = new SmartContract();
        // ContractReq c = new ContractReq();
        // c.setName("abc");
        // c.setPriKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        // c.setAddr("10.1.3.150");
        // c.setPort(9008);
        //
        // RestResponse restResponse = destorySmartContract(contract, c);
        // System.out.println(restResponse);
        //    ---------------------------------------
        // Contract contract = new SmartContract();
        // InvokeSmartContractReq c = new InvokeSmartContractReq();
        // List<Object> list = new ArrayList<>();
        // list.add("bob");
        // list.add("jim");
        // list.add(10);
        //
        // c.setCategory("wvm");
        // c.setVersion("2");
        // c.setMethod("transfer");
        // c.setArgs(list);
        // c.setCaller("123");
        // c.setName("15d2b6f52de83395734c4d36999bf5ef883058b95d1aedfca3e7ea67ca0b1919");
        // c.setPriKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        // c.setAddr("10.1.3.150");
        // c.setPort(9008);
        //
        // RestResponse restResponse = invokeSmartContract(contract, c);
        // System.out.println(restResponse);

        //    --------------------------------------------------
        // Contract contract = new SmartContract();
        // QuerySmartContractReq c = new QuerySmartContractReq();
        // List<Object> list = new ArrayList<>();
        // list.add("bob");
        // c.setCategory("wvm");
        // c.setArgs(list);
        // c.setCaller("123");
        // c.setVersion("2");
        // c.setMethod("getBalance");
        // c.setName("15d2b6f52de83395734c4d36999bf5ef883058b95d1aedfca3e7ea67ca0b1919");
        //
        // c.setPriKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        // c.setAddr("10.1.3.150");
        // c.setPort(9008);
        //
        // RestResponse restResponse = querySmartContract(c);
        // System.out.println(restResponse);

        //    ===============================================================================

        //    =============================================
        // RestResponse height = blockHeight("10.1.3.150", 9008, "D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem");
        // System.out.println("height = " + height);
    }

    /**
     * 创建交易
     *
     * @param systemTx
     * @param newTxQueryDto
     * @return
     */
    public static RestResponse newSysTransaction(SystemTx systemTx, NewTxQueryDto newTxQueryDto) {
        return systemTx.newTransaction(newTxQueryDto);
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
