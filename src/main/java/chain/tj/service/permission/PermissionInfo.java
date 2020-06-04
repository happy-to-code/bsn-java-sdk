package chain.tj.service.permission;

import chain.tj.common.StatusCode;
import chain.tj.common.exception.ServiceException;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.Msg;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Permission;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static chain.tj.util.PeerBasicUtil.checkingParam;
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
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @return
     */
    @Override
    public RestResponse getPeerPermissions(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo) {
        // 验证参数
        checkingParam(stubList, txCommonDataVo);

        // 封装请求对象
        MyPeer.PeerRequest peerRequest = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(txCommonDataVo.getPubKeyByte()))
                .build();

        Msg.PeerPermission peerPermissions = null;
        for (PeerGrpc.PeerBlockingStub stub : stubList) {
            // 调用接口
            MyPeer.PeerResponse peerResponse;
            try {
                peerResponse = stub.getPeerPermissions(peerRequest);
                // 成功
                if (null != peerResponse && peerResponse.getOk()) {
                    try {
                        peerPermissions = Msg.PeerPermission.parseFrom(peerResponse.getPayload());
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                        continue;
                    }
                    break;
                } else {
                    // 如果调用一个节点失败，尝试另外的节点
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (null == peerPermissions) {
            return RestResponse.failure("查询权限数据失败", StatusCode.SERVER_500000.value());
        }

        return RestResponse.success().setData(peerPermissions);
    }


}
