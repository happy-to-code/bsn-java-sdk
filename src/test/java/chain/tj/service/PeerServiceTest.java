package chain.tj.service;

import chain.tj.model.pojo.dto.SubLedgerTxDto;
import chain.tj.model.pojo.query.NewTxQueryDto;
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

    @Test
    public void testNewTransaction() {
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        newTxQueryDto.setPeerType(0);
        newTxQueryDto.setSubType(0);
        newTxQueryDto.setOpType(0);
        newTxQueryDto.setAddr("/ip4/10.1.13.150/tcp/60005");
        newTxQueryDto.setRpcPort(9008);
        newTxQueryDto.setMemberId("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3");
        newTxQueryDto.setShownName("newname");

        createSystemPeer.newTransaction(newTxQueryDto);
    }

    @Test
    public void testCreateSystemPM() {
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        newTxQueryDto.setPermission(Arrays.asList(0, 1, 2, 10));
        newTxQueryDto.setRpcAddr("10.1.3.150:9008");
        newTxQueryDto.setShownName("myname");

        createSystemPM.newTransaction(newTxQueryDto);
    }

    @Test
    public void testCreateSystemSubLeadger() {
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        SubLedgerTxDto subLedgerTxDto = new SubLedgerTxDto();
        subLedgerTxDto.setOpType(0);
        subLedgerTxDto.setWord("genessisTX");
        subLedgerTxDto.setCons("raft");
        subLedgerTxDto.setMembers(Arrays.asList("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3"));


        newTxQueryDto.setSubLedgerTxDto(subLedgerTxDto);

        createSystemSubLeadger.newTransaction(newTxQueryDto);
    }
}