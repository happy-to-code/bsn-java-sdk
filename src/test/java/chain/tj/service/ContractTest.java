package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.ContractReq;
import chain.tj.service.contract.SmartContract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
        ContractReq c = new ContractReq();
        c.setCategory("wvm");

        RestResponse restResponse = smartContract.installSmartContract(c);
        System.out.println(restResponse);
    }

    @Test
    public void testDestorySmartContract() {
        ContractReq c = new ContractReq();
        // c.setCategory("wvm");
        c.setName("abc");

        RestResponse restResponse = smartContract.destorySmartContract(c);
        System.out.println(restResponse);
    }
}
