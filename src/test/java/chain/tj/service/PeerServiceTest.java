package chain.tj.service;

import chain.tj.model.pojo.dto.*;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.systemtx.CreateSystemFASC;
import chain.tj.service.systemtx.CreateSystemPM;
import chain.tj.service.systemtx.CreateSystemPeer;
import chain.tj.service.systemtx.CreateSystemSubLeadger;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chain.tj.util.PeerBasicUtil.getCommonData;
import static chain.tj.util.PeerBasicUtil.getStubList;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/19 17:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PeerServiceTest extends TestCase {

    @Resource
    private CreateSystemPeer createSystemPeer;

    @Resource
    private CreateSystemPM createSystemPM;

    @Resource
    private CreateSystemSubLeadger createSystemSubLeadger;

    @Resource
    private CreateSystemFASC createSystemFASC;

    @Test
    public void testNewTransaction() {
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

        createSystemPeer.newTransaction(stubList, commonData, newTxQueryDto);
    }

    @Test
    public void testCreateSystemPM() {
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
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();

        PermissionTxDto permissionTxDto = new PermissionTxDto();
        permissionTxDto.setShownName("myname");
        permissionTxDto.setPm(Arrays.asList(0, 1, 2, 10));
        permissionTxDto.setPeerId("123");

        newTxQueryDto.setPermissionTxDto(permissionTxDto);

        // newTxQueryDto.setPermission(Arrays.asList(0, 1, 2, 10));
        // newTxQueryDto.setRpcAddr("10.1.3.150:9008");
        // newTxQueryDto.setShownName("myname");

        createSystemPM.newTransaction(stubList, commonData, newTxQueryDto);
    }

    @Test
    public void testCreateSystemSubLeadger() {
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
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();

        SubLedgerTxDto subLedgerTxDto = new SubLedgerTxDto();
        subLedgerTxDto.setOpType(0);
        subLedgerTxDto.setWord("genessisTX");
        subLedgerTxDto.setCons("raft");
        subLedgerTxDto.setMembers(Arrays.asList("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3"));


        newTxQueryDto.setSubLedgerTxDto(subLedgerTxDto);

        createSystemSubLeadger.newTransaction(stubList, commonData, newTxQueryDto);
    }

    @Test
    public void testCreateSystemFASC() {
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
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();

        SysContractStatusTxDto statusTxDto = new SysContractStatusTxDto();
        statusTxDto.setName("aads");
        statusTxDto.setVersion("0");
        statusTxDto.setOp("F");


        newTxQueryDto.setSysContractStatusTxDto(statusTxDto);

        createSystemFASC.newTransaction(stubList, commonData, newTxQueryDto);
    }
}