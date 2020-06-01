package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.service.memberinfo.MemberInfo;
import chain.tj.service.permission.PermissionInfo;
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
public class PermissionInfoTest extends TestCase {

    @Resource
    private PermissionInfo permissionInfo;

    @Test
    public void testMemberInfo() {

        RestResponse peerPermissions = permissionInfo.getPeerPermissions("10.1.3.150", 9008, "D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\pubKey.pem");
        System.out.println("peerPermissions = " + peerPermissions);
    }


}