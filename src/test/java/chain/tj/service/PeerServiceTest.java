package chain.tj.service;

import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.pojo.dto.PermissionTxDto;
import chain.tj.model.pojo.dto.SubLedgerTxDto;
import chain.tj.model.pojo.dto.SysContractStatusTxDto;
import chain.tj.model.pojo.query.NewTxQueryDto;
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
import java.util.Arrays;

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
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        newTxQueryDto.setAddr("10.1.3.150");
        newTxQueryDto.setRpcPort(9008);
        newTxQueryDto.setPubKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem");
        newTxQueryDto.setPriKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");

        PeerTxDto peerTxDto = new PeerTxDto();
        peerTxDto.setPeerType(0);
        peerTxDto.setOpType(0);
        peerTxDto.setRpcPort(9008);
        peerTxDto.setWlanAddrs(Arrays.asList("/ip4/10.1.13.150/tcp/60005"));
        peerTxDto.setLanAddrs(Arrays.asList("/ip4/10.1.13.151/tcp/60005"));
        peerTxDto.setId("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3");
        peerTxDto.setShownName("myName");

        newTxQueryDto.setPeerTxDto(peerTxDto);

        createSystemPeer.newTransaction(newTxQueryDto);
    }

    @Test
    public void testCreateSystemPM() {
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        newTxQueryDto.setAddr("10.1.3.150");
        newTxQueryDto.setRpcPort(9008);
        newTxQueryDto.setPubKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem");
        newTxQueryDto.setPriKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");


        PermissionTxDto permissionTxDto = new PermissionTxDto();
        permissionTxDto.setShownName("myname");
        permissionTxDto.setPm(Arrays.asList(0, 1, 2, 10));
        permissionTxDto.setPeerId("123");

        newTxQueryDto.setPermissionTxDto(permissionTxDto);

        // newTxQueryDto.setPermission(Arrays.asList(0, 1, 2, 10));
        // newTxQueryDto.setRpcAddr("10.1.3.150:9008");
        // newTxQueryDto.setShownName("myname");

        createSystemPM.newTransaction(newTxQueryDto);
    }

    @Test
    public void testCreateSystemSubLeadger() {
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        newTxQueryDto.setAddr("10.1.3.150");
        newTxQueryDto.setRpcPort(9008);
        newTxQueryDto.setPubKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem");
        newTxQueryDto.setPriKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");

        SubLedgerTxDto subLedgerTxDto = new SubLedgerTxDto();
        subLedgerTxDto.setOpType(0);
        subLedgerTxDto.setWord("genessisTX");
        subLedgerTxDto.setCons("raft");
        subLedgerTxDto.setMembers(Arrays.asList("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3"));


        newTxQueryDto.setSubLedgerTxDto(subLedgerTxDto);

        createSystemSubLeadger.newTransaction(newTxQueryDto);
    }

    @Test
    public void testCreateSystemFASC() {
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        newTxQueryDto.setAddr("10.1.3.150");
        newTxQueryDto.setRpcPort(9008);
        newTxQueryDto.setPubKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem");
        newTxQueryDto.setPriKeyPath("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");

        SysContractStatusTxDto statusTxDto = new SysContractStatusTxDto();
        statusTxDto.setName("aads");
        statusTxDto.setVersion("0");
        statusTxDto.setOp("F");


        newTxQueryDto.setSysContractStatusTxDto(statusTxDto);

        createSystemFASC.newTransaction(newTxQueryDto);
    }
}