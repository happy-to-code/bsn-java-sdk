package chain.tj.service.systemtx;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.SystemTx;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static chain.tj.util.PeerUtil.*;
import static chain.tj.util.TransactionUtil.*;

/**
 * @Describe:节点变更
 * @Author: zhangyifei
 * @Date: 2020/5/22 11:31
 */
@Slf4j
@Service
public class CreateSystemPeer implements SystemTx {

    @Value("${peer.pubKey}")
    private String pubKey = "2c7f6f353d828e99692bb8bf960186f218674581495b399db753c00dd636c4f0583f7a833ce67d352e7d32be5d6e3fc899d7004efe1f450fc1a078ee856a8b75";

    @Override
    public RestResponse newTransaction(NewTxQueryDto newTxQueryDto) {
        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // 获取当前时间戳
        long currentTime = System.currentTimeMillis() / 1000;

        // 根据subType获取TransactionDto
        TransactionDto transactionDto = getTransactionDtoBySubType(currentTime, newTxQueryDto);

        // 序列化PeerDto,并且给transactionDto赋值
        serialPeerTxDto(newTxQueryDto, peerPubKey, transactionDto);

        // 给TransactionDto对象赋值
        setValueForTransactionDto(transactionDto);

        // PeerRequest: Transaction, PeerResponse:PeerResponse
        // 封装请求对象
        MyPeer.PeerRequest request = getPeerRequest(transactionDto, peerPubKey);

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(newTxQueryDto.getAddr(), newTxQueryDto.getPeerTxDto().getRpcPort());
        // 调用接口
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);
        log.info("peerResponse--->{}", peerResponse);

        if (peerResponse.getOk()) {
            return RestResponse.success();
        }

        return RestResponse.failure("节点变更失败", StatusCode.SERVER_500000.value());
    }


    /**
     * 序列化PeerDto,并且给transactionDto赋值
     *
     * @param newTxQueryDto
     * @param peerPubKey
     * @param transactionDto
     */
    private void serialPeerTxDto(NewTxQueryDto newTxQueryDto, ByteString peerPubKey, TransactionDto transactionDto) {
        PeerTxDto peerTxDto = new PeerTxDto();

        peerTxDto.setOpType(newTxQueryDto.getPeerTxDto().getOpType());
        peerTxDto.setPeerType(newTxQueryDto.getPeerTxDto().getPeerType());
        peerTxDto.setId(newTxQueryDto.getPeerTxDto().getId());
        peerTxDto.setShownName(newTxQueryDto.getPeerTxDto().getShownName());
        peerTxDto.setLanAddrs(newTxQueryDto.getPeerTxDto().getLanAddrs());
        peerTxDto.setWlanAddrs(newTxQueryDto.getPeerTxDto().getWlanAddrs());
        peerTxDto.setRpcPort(newTxQueryDto.getPeerTxDto().getRpcPort());

        // 获取peerTxDtoBytes
        byte[] peerTxDtoBytes = getPeerTxDtoBytes(peerTxDto);

        // 序列化
        byte[] sysData = dataSerializable(peerTxDtoBytes, peerPubKey.toByteArray(), peerTxDto);
        log.info("sysData的十六进制：{}", toHexString(sysData));

        transactionDto.setData(sysData);
        transactionDto.setPubKey(peerPubKey.toByteArray());
    }


    /**
     * 根据subType获取TransactionDto
     *
     * @param currentTime
     * @param newTxQueryDto
     * @return
     */
    private TransactionDto getTransactionDtoBySubType(long currentTime, NewTxQueryDto newTxQueryDto) {
        TransactionDto transactionDto = getTransactionDto(currentTime);

        Integer opType = newTxQueryDto.getPeerTxDto().getOpType();
        if (opType == 0) {
            transactionDto.getTransactionHeader().setSubType(0);
        } else {
            transactionDto.getTransactionHeader().setSubType(1);
        }

        return transactionDto;
    }

}
