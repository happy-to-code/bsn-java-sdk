package chain.tj.util;

import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.service.SystemTx;
import chain.tj.service.systemtx.CreateSystemPeer;

import java.util.Arrays;

/**
 * @Describe: 系统消息创建工具类
 * @Author: zhangyifei
 * @Date: 2020/5/22 15:06
 */
public class SystemTxUtil {
    public static void main(String[] args) {
        SystemTx systemTx = new CreateSystemPeer();
        NewTxQueryDto newTxQueryDto = new NewTxQueryDto();

        PeerTxDto peerTxDto = new PeerTxDto();
        peerTxDto.setPeerType(0);
        peerTxDto.setOpType(0);
        peerTxDto.setRpcPort(9008);
        peerTxDto.setWlanAddrs(Arrays.asList("/ip4/10.1.13.150/tcp/60005"));
        peerTxDto.setLanAddrs(Arrays.asList("/ip4/10.1.13.151/tcp/60005"));
        peerTxDto.setId("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3");
        peerTxDto.setShownName("myName");

        newTxQueryDto.setPeerTxDto(peerTxDto);
        RestResponse restResponse = newSysTransaction(systemTx, newTxQueryDto);
        System.out.println("restResponse = " + restResponse);
    }

    public static RestResponse newSysTransaction(SystemTx systemTx, NewTxQueryDto newTxQueryDto) {
        return systemTx.newTransaction(newTxQueryDto);
    }
}
