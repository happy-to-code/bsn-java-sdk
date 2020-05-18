package chain.tj.service;

import chain.tj.model.pojo.query.NewTxQueryDto;
import org.springframework.stereotype.Service;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 16:25
 */
@Service
public interface PeerService {
    /**
     * 创建交易
     *
     * @param newTxQueryDto
     */
    void newTransaction(NewTxQueryDto newTxQueryDto);
}
