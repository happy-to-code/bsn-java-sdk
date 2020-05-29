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
        RestResponse height = blockInfo.blockHeight("10.1.3.150", 9008, "D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem");
        System.out.println("height = " + height);
    }

    @Test
    public void testGetBlockByHeight() {
        RestResponse block = blockInfo.getBlockByHeight("10.1.3.150", 9008, "D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem", 17);
        System.out.println("block = " + block);
    }

    @Test
    public void testGetBlockByHash() {
        RestResponse block = blockInfo.getBlockByHash("10.1.3.150", 9008, "D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem", "0");
        System.out.println("block = " + block);
    }


}