package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.service.memberinfo.MemberInfo;
import com.google.protobuf.InvalidProtocolBufferException;
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
public class MemberInfoTest extends TestCase {

    @Resource
    private MemberInfo memberInfo;

    @Test
    public void testMemberInfo() {

        RestResponse memberList = memberInfo.getMemberList();
        System.out.println("memberList = " + memberList);
    }


}