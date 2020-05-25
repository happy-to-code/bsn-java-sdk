package chain.tj.controller;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.service.systemtx.SystemTx;
import chain.tj.service.systemtx.impl.CreateSystemPeer;
import chain.tj.util.SystemTxUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 15:51
 */
@RestController
@RequestMapping("/")
public class PeerController {


    @Resource
    private SystemTxUtil systemTxUtil;

    /**
     * 创建交易
     */
    @RequestMapping("newTx")
    public RestResponse newTx() {
        SystemTx systemTx = new CreateSystemPeer();
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
        newTxQueryDto.setPeerType(0);
        newTxQueryDto.setSubType(0);
        newTxQueryDto.setOpType(0);
        newTxQueryDto.setAddr("/ip4/10.1.13.150/tcp/60005");
        newTxQueryDto.setRpcPort(9008);
        newTxQueryDto.setMemberId("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3");
        newTxQueryDto.setShownName("newname");
        return systemTxUtil.newSysTransaction(systemTx, newTxQueryDto);

    }
}
