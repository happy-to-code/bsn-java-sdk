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
     * @return
     */
    RestResponse blockHeight();

    /**
     * 根据区块高度区块信息
     *
     * @param height
     * @return
     */
    RestResponse getBlockByHeight(Integer height);

    /**
     * 根据hash值查询区块信息
     *
     * @param hash
     * @return
     */
    RestResponse getBlockByHash(String hash);

}
