package chain.tj.service;

import chain.tj.common.response.RestResponse;

/**
 * @Describe:区块相关
 * @Author: zhangyifei
 * @Date: 2020/5/26 17:12
 */
public interface Block {

    /**
     * 获取区块高度
     *
     * @param addr       ip地址
     * @param rpcPort    端口
     * @param pubKeyPath 公钥文件路径地址
     * @return
     */
    RestResponse blockHeight(String addr, Integer rpcPort, String pubKeyPath);

    /**
     * 根据区块高度区块信息
     *
     * @param addr       ip地址
     * @param rpcPort    端口
     * @param pubKeyPath 公钥文件路径地址
     * @param height
     * @return
     */
    RestResponse getBlockByHeight(String addr, Integer rpcPort, String pubKeyPath, Integer height);

    /**
     * 根据hash值查询区块信息
     *
     * @param addr       ip地址
     * @param rpcPort    端口
     * @param pubKeyPath 公钥文件路径地址
     * @param hash
     * @return
     */
    RestResponse getBlockByHash(String addr, Integer rpcPort, String pubKeyPath, String hash);

}
