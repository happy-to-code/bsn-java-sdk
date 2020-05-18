package chain.tj.service.impl;

import chain.tj.model.ienum.SubType;
import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.pojo.dto.SystemTx;
import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.service.PeerService;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;

import static chain.tj.util.PeerUtil.convertPubKeyToByteString;
import static chain.tj.util.PeerUtil.getPeerTxDtoBytes;
import static chain.tj.util.TransactionUtil.getTransactionDto;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 16:26
 */
public class PeerServiceImpl implements PeerService {

    @Value("${peer.pubKey}")
    private String pubKey;

    /**
     * 创建交易
     *
     * @param newTxQueryDto
     */
    public void newTransaction(NewTxQueryDto newTxQueryDto) {
        // 验证数据 TODO

        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);

        // 获取当前时间戳
        long currentTime = System.currentTimeMillis() / 1000;

        // 根据subType获取TransactionDto
        TransactionDto transactionDto = getTransactionDtoBySubType(newTxQueryDto, currentTime);

        PeerTxDto peerTxDto = new PeerTxDto();
        peerTxDto.setOpType(newTxQueryDto.getOpType());
        peerTxDto.setPeerType(newTxQueryDto.getPeerType());
        peerTxDto.setId(newTxQueryDto.getMemberId());
        peerTxDto.setShownName("newName");
        peerTxDto.setLanAddrs(Arrays.asList(newTxQueryDto.getAddr()));
        peerTxDto.setWlanAddrs(Arrays.asList(newTxQueryDto.getAddr()));
        peerTxDto.setRpcPort(newTxQueryDto.getRpcPort());

        // 获取TransactionHash
        byte[] peerTxDtoBytes = getPeerTxDtoBytes(peerTxDto);

        SystemTx systemTx = new SystemTx();
        systemTx.setPubKey(peerPubKey.toByteArray());

    }


    /**
     * 根据subType获取TransactionDto
     *
     * @param subType
     * @param currentTime
     * @return
     */
    private TransactionDto getTransactionDtoBySubType(NewTxQueryDto subType, long currentTime) {
        TransactionDto transactionDto = getTransactionDto(currentTime);

        if (SubType.M_Peer.getValue().equals(subType.getSubType())) {
            createSystemPeer(transactionDto, subType);
        } else if (SubType.D_Peer.getValue().equals(subType.getSubType())) {
            createSystemPM(transactionDto);
        } else if (SubType.D_Peer.getValue().equals(subType.getSubType())) {
            createSystemSubLeadger(transactionDto);
        } else if (SubType.Permission.getValue().equals(subType.getSubType())) {
            createSystemFASC(transactionDto);
        }

        return transactionDto;
    }


    private void createSystemSubLeadger(TransactionDto transactionDto) {

    }

    private void createSystemFASC(TransactionDto transactionDto) {

    }

    private void createSystemPM(TransactionDto transactionDto) {

    }

    private void createSystemPeer(TransactionDto transactionDto, NewTxQueryDto subType) {
        // 设置交易类型为：系统交易
        transactionDto.getTransactionHeader().setType(4);
        // 设置subType类型
        transactionDto.getTransactionHeader().setSubType(0);

        if (subType.getOpType() == 1) {
            transactionDto.getTransactionHeader().setSubType(1);
        }
    }
}
