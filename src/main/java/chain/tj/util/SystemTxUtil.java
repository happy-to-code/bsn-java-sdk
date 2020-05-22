package chain.tj.util;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.service.systemtx.SystemTx;
import org.springframework.stereotype.Component;

/**
 * @Describe: 系统消息创建工具类
 * @Author: zhangyifei
 * @Date: 2020/5/22 15:06
 */
@Component
public class SystemTxUtil {

    public RestResponse newSysTransaction(SystemTx systemTx, NewTxQueryDto newTxQueryDto) {
        return systemTx.newTransaction(newTxQueryDto);
    }
}
