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
     * @param addr       ip地址
     * @param rpcPort    端口
     * @param pubKeyPath 公钥文件路径地址
     * @return
     */
    RestResponse getMemberList(String addr, Integer rpcPort, String pubKeyPath) throws InvalidProtocolBufferException;
}
