package chain.tj.util;

import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.dto.TransactionHeaderDto;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 16:50
 */
public class TransactionUtil {
    /**
     * 获取基础TransactionDto
     *
     * @param currentTime
     * @return
     */
    public static TransactionDto getTransactionDto(long currentTime) {
        // 创建返回对象
        TransactionDto transactionDto = new TransactionDto();
        // 创建头对象
        TransactionHeaderDto transactionHeaderDto = new TransactionHeaderDto();
        transactionHeaderDto.setTimestamp(currentTime);
        // 默认版本
        transactionHeaderDto.setVersion(0);

        transactionDto.setTransactionHeader(transactionHeaderDto);
        return transactionDto;
    }
}
