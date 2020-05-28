package chain.tj.service.systemtx;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.query.BasicTxObj;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.proto.MyPeer;
import chain.tj.service.SystemTx;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public RestResponse newTransaction(NewTxQueryDto newTxQueryDto) {
        // 获取交易基本对象
        BasicTxObj basicTxObj = getBasicTxObj(newTxQueryDto);
        ByteString peerPubKey = basicTxObj.getPubKey();
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // 根据subType获取TransactionDto
        TransactionDto transactionDto = getTransactionDtoBySubType(basicTxObj.getCurrentTime(), newTxQueryDto);

        // 序列化PeerDto,并且给transactionDto赋值
        serialPeerTxDto(newTxQueryDto, peerPubKey, transactionDto);

        // 给TransactionDto对象赋值
        setValueForTransactionDto(transactionDto, newTxQueryDto.getPriKeyPath());

        // PeerRequest: Transaction, PeerResponse:PeerResponse
        // 封装请求对象
        MyPeer.PeerRequest request = getPeerRequest(transactionDto, peerPubKey);

        // 调用接口
        MyPeer.PeerResponse peerResponse = basicTxObj.getStub().newTransaction(request);
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
