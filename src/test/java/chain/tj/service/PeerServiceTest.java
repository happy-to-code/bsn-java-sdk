package chain.tj.service;

import chain.tj.model.pojo.query.NewTxQueryDto;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/19 17:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PeerServiceTest extends TestCase {
    @Resource
    private PeerService peerService;

    @Test
    public void testNewTransaction() {
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        newTxQueryDto.setPeerType(0);
        newTxQueryDto.setSubType(0);
        newTxQueryDto.setType(4);
        newTxQueryDto.setOpType(0);
        newTxQueryDto.setAddr("10.1.3.150");
        newTxQueryDto.setRpcPort(9008);
        newTxQueryDto.setMemberId("abc");

        peerService.newTransaction(newTxQueryDto);
    }
}