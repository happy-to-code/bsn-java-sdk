package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.service.block.BlockInfo;
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
public class BlockInfoTest extends TestCase {

    @Resource
    private BlockInfo blockInfo;

    @Test
    public void testBlockHeight() {

        RestResponse height = blockInfo.blockHeight();
        System.out.println("height = " + height);
    }

    @Test
    public void testGetBlockByHeight() {
        RestResponse block = blockInfo.getBlockByHeight(14);

        System.out.println("block = " + block);
    }


}