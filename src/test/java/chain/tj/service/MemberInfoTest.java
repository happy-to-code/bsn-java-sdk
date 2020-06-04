package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.PeerCommonDataDto;
import chain.tj.model.pojo.dto.PeerConnectionDto;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.memberinfo.MemberInfo;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class MemberInfoTest extends TestCase {

    @Resource
    private MemberInfo memberInfo;

    @Test
    public void testMemberInfo() {
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


        RestResponse memberList = memberInfo.getMemberList(stubList, commonData);
        System.out.println("memberList = " + memberList);
    }


}