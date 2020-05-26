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
}