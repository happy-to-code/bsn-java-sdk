package chain.tj.service;

import chain.tj.common.response.RestResponse;

/**
 * @Describe:权限相关
 * @Author: zhangyifei
 * @Date: 2020/6/1 14:42
 */
public interface Permission {
    /**
     * 获取节点信息
     *
     * @param addr       ip地址
     * @param rpcPort    端口
     * @param pubKeyPath 公钥文件路径地址
     * @return
     */
    RestResponse getPeerPermissions(String addr, Integer rpcPort, String pubKeyPath);
}
