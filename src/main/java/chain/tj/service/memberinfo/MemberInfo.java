package chain.tj.service.memberinfo;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.Msg;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Member;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static chain.tj.util.PeerBasicUtil.checkingParam;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/26 15:04
 */
@Slf4j
@Service
public class MemberInfo implements Member {

    /**
     * 获取节点信息
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @return
     */
    @Override
    public RestResponse getMemberList(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo) {
        // 验证参数
        checkingParam(stubList, txCommonDataVo);

        // 封装请求对象
        MyPeer.PeerRequest peerRequest = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(txCommonDataVo.getPubKeyByte()))
                .build();

        Msg.MemberListInfo memberListInfo = null;
        for (PeerGrpc.PeerBlockingStub stub : stubList) {
            // 调用接口
            MyPeer.PeerResponse peerResponse;
            try {
                peerResponse = stub.getMemberList(peerRequest);
                // 成功
                if (null != peerResponse && peerResponse.getOk()) {
                    try {
                        memberListInfo = Msg.MemberListInfo.parseFrom(peerResponse.getPayload());
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                        continue;
                    }
                    break;
                } else {
                    // 如果调用一个节点失败  尝试另外的节点
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (null == memberListInfo) {
            return RestResponse.failure("查询数据失败", StatusCode.SERVER_500000.value());
        }

        return RestResponse.success().setData(memberListInfo);
    }
}
