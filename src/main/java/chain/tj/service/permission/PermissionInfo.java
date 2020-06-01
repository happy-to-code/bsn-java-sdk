package chain.tj.service.permission;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.proto.Msg;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Permission;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static chain.tj.util.PeerUtil.*;
import static chain.tj.util.PeerUtil.getStubByIpAndPort;
import static chain.tj.util.TransactionUtil.checkParam;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/6/1 15:05
 */
@Service
@Slf4j
public class PermissionInfo implements Permission {

    /**
     * 获取权限
     *
     * @param addr       ip地址
     * @param rpcPort    端口
     * @param pubKeyPath 公钥文件路径地址
     * @return
     */
    @Override
    public RestResponse getPeerPermissions(String addr, Integer rpcPort, String pubKeyPath) {
        // 验证参数
        checkParam(addr, rpcPort, pubKeyPath);

        // 读取PubKey
        String pubKey = readFile(pubKeyPath);
        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // 封装请求对象
        MyPeer.PeerRequest peerRequest = MyPeer.PeerRequest.newBuilder()
                .setPubkey(peerPubKey)
                .build();

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(addr, rpcPort);

        // 调用接口
        MyPeer.PeerResponse peerResponse = stub.getPeerPermissions(peerRequest);
        log.info("peerResponse--->{}", peerResponse);

        Msg.PeerPermission peerPermissions;
        try {
            peerPermissions = Msg.PeerPermission.parseFrom(peerResponse.getPayload());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return RestResponse.failure("获取节点信息失败：" + e.getMessage(), StatusCode.SERVER_500000.value());
        }

        if (peerResponse.getOk()) {
            return RestResponse.success().setData(peerPermissions);
        }

        return RestResponse.failure("获取节点信息失败!", StatusCode.SERVER_500000.value());
    }
}
