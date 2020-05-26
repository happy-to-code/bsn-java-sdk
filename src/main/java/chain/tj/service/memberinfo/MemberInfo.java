package chain.tj.service.memberinfo;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.proto.Msg;
import chain.tj.model.proto.MyBlock;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Member;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static chain.tj.util.PeerUtil.*;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/26 15:04
 */
@Slf4j
@Service
public class MemberInfo implements Member {

    @Value("${peer.pubKey}")
    private String pubKey = "2c7f6f353d828e99692bb8bf960186f218674581495b399db753c00dd636c4f0583f7a833ce67d352e7d32be5d6e3fc899d7004efe1f450fc1a078ee856a8b75";


    /**
     * 获取节点信息
     *
     * @return
     */
    @Override
    public RestResponse getMemberList() {
        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // PeerRequest: PeerRequest, PeerResponse:MemberList

        // 封装请求对象
        MyPeer.PeerRequest peerRequest = MyPeer.PeerRequest.newBuilder()
                .setPubkey(peerPubKey)
                .build();

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort("", 1);
        // 调用接口
        MyPeer.PeerResponse peerResponse = stub.getMemberList(peerRequest);
        log.info("peerResponse--->{}", peerResponse);

        // PeerRequest: BlockchainNumber, PeerResponse:Block
        Msg.MemberListInfo memberListInfos = null;
        try {
            memberListInfos = Msg.MemberListInfo.parseFrom(peerResponse.getPayload());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return RestResponse.failure("获取节点信息失败：" + e.getMessage(), StatusCode.SERVER_500000.value());
        }

        if (peerResponse.getOk()) {
            return RestResponse.success().setData(memberListInfos);
        }

        return RestResponse.failure("获取节点信息失败!", StatusCode.SERVER_500000.value());
    }
}
