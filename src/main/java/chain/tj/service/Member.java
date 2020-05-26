package chain.tj.service;

import chain.tj.common.response.RestResponse;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/26 15:02
 */
public interface Member {

    /**
     * 获取节点信息
     *
     * @return
     */
    RestResponse getMemberList() throws InvalidProtocolBufferException;
}
