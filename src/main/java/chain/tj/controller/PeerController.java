package chain.tj.controller;

import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.service.PeerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 15:51
 */
@RestController
public class PeerController {

    @Resource
    private PeerService peerService;

    /**
     * 创建交易
     *
     * @param newTxQueryDto
     */
    @RequestMapping("newTx")
    public void newTx(NewTxQueryDto newTxQueryDto) {
        peerService.newTransaction(newTxQueryDto);
    }
}
