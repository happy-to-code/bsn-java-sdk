package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.ContractReq;
import chain.tj.model.pojo.dto.InvokeSmartContractReq;
import chain.tj.service.contract.SmartContract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        c.setName("abc");

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

    @Test
    public void testInvokeSmartContract() {
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
        c.setName("b686eaef00b39b3344d8b6b9e4bda257a99f67f4787a8fadedb23eb843ab7d72");

        RestResponse restResponse = smartContract.invokeSmartContract(c);
        System.out.println(restResponse);
    }
}
