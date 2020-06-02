package chain.tj.controller;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 15:51
 */
// @RestController
// @RequestMapping("/")
// public class PeerController {
//     /**
//      * 创建交易
//      */
//     @RequestMapping("newTx")
//     public RestResponse newTx() {
//         SystemTx systemTx = new CreateSystemPeer();
//         NewTxQueryDto newTxQueryDto = new NewTxQueryDto();
//
//         PeerTxDto peerTxDto = new PeerTxDto();
//         peerTxDto.setPeerType(0);
//         peerTxDto.setOpType(0);
//         peerTxDto.setRpcPort(9008);
//         peerTxDto.setWlanAddrs(Arrays.asList("/ip4/10.1.13.150/tcp/60005"));
//         peerTxDto.setLanAddrs(Arrays.asList("/ip4/10.1.13.151/tcp/60005"));
//         peerTxDto.setId("QmXCme1rk8b3SG7w7JSN9JhBm1uLo8kfTXqbGcbmdc9LT3");
//         peerTxDto.setShownName("myName");
//
//         newTxQueryDto.setPeerTxDto(peerTxDto);
//         return SystemTxUtil.newSysTransaction(systemTx, newTxQueryDto);
//
//     }
// }
