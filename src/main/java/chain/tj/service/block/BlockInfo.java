package chain.tj.service.block;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.proto.Msg;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Block;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static chain.tj.util.PeerUtil.*;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/26 17:14
 */
@Service
@Slf4j
public class BlockInfo implements Block {

    @Value("${peer.pubKey}")
    private String pubKey = "2c7f6f353d828e99692bb8bf960186f218674581495b399db753c00dd636c4f0583f7a833ce67d352e7d32be5d6e3fc899d7004efe1f450fc1a078ee856a8b75";

    /**
     * 获取区块高度
     *
     * @return
     */
    @Override
    public RestResponse blockHeight() {
        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // 封装请求对象
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder().setPubkey(peerPubKey).build();

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort("", 1);

        // 获取高度
        MyPeer.PeerResponse response = stub.blockchainGetHeight(request);

        if (!response.getOk()) {
            return RestResponse.failure("请求出错！", StatusCode.SERVER_500000.value());
        }

        Msg.BlockchainNumber blockchainNumber;
        try {
            blockchainNumber = Msg.BlockchainNumber.parseFrom(response.getPayload());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return RestResponse.failure("请求出错:" + e.getMessage(), StatusCode.SERVER_500000.value());
        }

        return RestResponse.success().setData(blockchainNumber.getNumber());
    }
}
