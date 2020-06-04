package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.*;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.contract.SmartContract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static chain.tj.util.PeerBasicUtil.getCommonData;
import static chain.tj.util.PeerBasicUtil.getStubList;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/29 15:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractTest {


    @Resource
    private SmartContract smartContract;

    @Test
    public void testSmartContract() {
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

        ContractReq c = new ContractReq();
        c.setCategory("wvm");
        c.setName("abc");
        c.setVersion("2");

        RestResponse restResponse = smartContract.installSmartContract(stubList, commonData, c);
        System.out.println(restResponse);
    }

    @Test
    public void testDestorySmartContract() {
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

        ContractReq c = new ContractReq();
        // c.setCategory("wvm");
        c.setName("abc");

        RestResponse restResponse = smartContract.destorySmartContract(stubList, commonData, c);
        System.out.println(restResponse);
    }

    @Test
    public void testInvokeSmartContract() {
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

        InvokeSmartContractReq c = new InvokeSmartContractReq();
        List<Object> list = new ArrayList<>();
        list.add("bob");
        list.add("jim");
        list.add(10);

        c.setCategory("wvm");
        c.setVersion("2");
        c.setMethod("transfer");
        c.setArgs(list);
        c.setCaller("123");
        c.setName("3cbcb5691f082b0aa24baff0ddf0dc7cb3cc9731dccd71c46c61767052ee6eb3");

        RestResponse restResponse = smartContract.invokeSmartContract(stubList, commonData, c);
        System.out.println(restResponse);
    }

    @Test
    public void testQuerySmartContract() {
        List<PeerConnectionDto> connectionDtoList = new ArrayList<>(10);
        connectionDtoList.add(new PeerConnectionDto("10.1.3.150", 9008));
        connectionDtoList.add(new PeerConnectionDto("10.1.3.151", 9008));
        connectionDtoList.add(new PeerConnectionDto("10.1.3.152", 9008));
        List<PeerGrpc.PeerBlockingStub> stubList = getStubList(connectionDtoList);


        PeerCommonDataDto dataDto = new PeerCommonDataDto();
        dataDto.setPriKeyFilePath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        dataDto.setPubKeyFilePath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem");
        dataDto.setWvmFilePath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\transfer.wlang");
        TxCommonDataVo commonData = getCommonData(dataDto);


        QuerySmartContractReq c = new QuerySmartContractReq();
        List<Object> list = new ArrayList<>();
        list.add("bob");
        c.setCategory("wvm");
        c.setArgs(list);
        c.setCaller("123");
        c.setVersion("2");
        c.setMethod("getBalance");
        c.setName("3cbcb5691f082b0aa24baff0ddf0dc7cb3cc9731dccd71c46c61767052ee6eb3");


        RestResponse restResponse = smartContract.querySmartContract(stubList, commonData, c);
        System.out.println(restResponse);
        // 9940
        // 9930
    }
}
