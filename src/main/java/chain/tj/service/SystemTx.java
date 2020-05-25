package chain.tj.service;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.query.NewTxQueryDto;

/**
 * @Describe:系统类型交易接口
 * @Author: zhangyifei
 * @Date: 2020/5/22 11:08
 */
public interface SystemTx {

    /**
     * 创建交易
     *
     * @param newTxQueryDto
     * @return
     */
    RestResponse newTransaction(NewTxQueryDto newTxQueryDto);
}
