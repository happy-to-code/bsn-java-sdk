package chain.tj.service.systemtx;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.SysContractStatusTxDto;
import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.dto.TransactionHeaderDto;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.SystemTx;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static chain.tj.util.PeerBasicUtil.checkingData;
import static chain.tj.util.PeerUtil.int2Bytes;
import static chain.tj.util.TransactionUtil.*;

/**
 * @Describe: 冻结或者激活合约
 * @Author: zhangyifei
 * @Date: 2020/5/25 18:20
 */
@Service
@Slf4j
public class CreateSystemFASC implements SystemTx {

    /**
     * 冻结或者激活合约
     *
     * @param newTxQueryDto
     * @return
     */
    @Override
    public RestResponse newTransaction(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, NewTxQueryDto newTxQueryDto) {
        // 验证参数
        checkingData(stubList, txCommonDataVo);

        // 获取密钥对和签名
        Map<String, byte[]> keyPairAndSign = txCommonDataVo.getKeyPairAndSign();

        // 获取pubKey
        ByteString peerPubKey = ByteString.copyFrom(keyPairAndSign.get("pubKey"));

        // 当前时间
        long currentTime = System.currentTimeMillis() / 1000;

        // 构建TransactionDto对象
        TransactionDto transactionDto = createTransactionDto(peerPubKey, currentTime, newTxQueryDto.getSysContractStatusTxDto());

        // 获取SysContractStatusTxDto对象的字节对象
        byte[] sysContractStatusTxDtoBytes = getSysContractStatusTxDtoBytes(newTxQueryDto.getSysContractStatusTxDto());

        // 获取sysData字节数组
        byte[] sysData = getSysData(peerPubKey, sysContractStatusTxDtoBytes, newTxQueryDto.getSysContractStatusTxDto().getOp());

        // sysData字节数组set进transactionDto对象
        transactionDto.setData(sysData);

        // 给TransactionDto对象赋值
        setValueForTransactionDto(transactionDto, keyPairAndSign);

        MyPeer.PeerRequest request = getPeerRequest(transactionDto, peerPubKey);

        // 重复调用接口
        if (repeatCall(stubList, request)) {
            return RestResponse.success();
        }

        return RestResponse.failure("冻结或者激活合约失败", StatusCode.SERVER_500000.value());
    }


    /**
     * 获取sysData字节数组
     *
     * @param peerPubKey
     * @param sysContractStatusTxDtoBytes
     * @param op
     * @return
     */
    private byte[] getSysData(ByteString peerPubKey, byte[] sysContractStatusTxDtoBytes, String op) {
        ByteBuf buf = Unpooled.buffer();

        buf.writeBytes(int2Bytes(peerPubKey.toByteArray().length));
        buf.writeBytes(peerPubKey.toByteArray());

        if (StringUtils.equals("F", op)) {
            buf.writeBytes(int2Bytes(9));
        } else if (StringUtils.equals("A", op)) {
            buf.writeBytes(int2Bytes(10));
        }

        // 写入数据
        buf.writeBytes(int2Bytes(sysContractStatusTxDtoBytes.length));
        buf.writeBytes(sysContractStatusTxDtoBytes);

        return convertBuf(buf);
    }


    /**
     * 获取SysContractStatusTxDto对象的字节对象
     *
     * @param sysContractStatusTxDto
     * @return
     */
    private byte[] getSysContractStatusTxDtoBytes(SysContractStatusTxDto sysContractStatusTxDto) {
        ByteBuf buf = Unpooled.buffer();

        if (StringUtils.isNoneBlank(sysContractStatusTxDto.getName())) {
            buf.writeBytes(int2Bytes(sysContractStatusTxDto.getName().length()));
            buf.writeBytes(sysContractStatusTxDto.getName().getBytes());
        }

        if (StringUtils.isNoneBlank(sysContractStatusTxDto.getVersion())) {
            buf.writeBytes(int2Bytes(sysContractStatusTxDto.getVersion().length()));
            buf.writeBytes(sysContractStatusTxDto.getVersion().getBytes());
        }

        if (StringUtils.isNoneBlank(sysContractStatusTxDto.getOp())) {
            buf.writeBytes(int2Bytes(sysContractStatusTxDto.getOp().length()));
            buf.writeBytes(sysContractStatusTxDto.getOp().getBytes());
        }

        return convertBuf(buf);

    }

    /**
     * 构建TransactionDto对象
     *
     * @param peerPubKey
     * @param currentTime
     * @param sysContractStatusTxDto
     * @return
     */
    private TransactionDto createTransactionDto(ByteString peerPubKey, long currentTime, SysContractStatusTxDto sysContractStatusTxDto) {
        TransactionDto transactionDto = new TransactionDto();

        TransactionHeaderDto transactionHeaderDto = new TransactionHeaderDto();
        transactionHeaderDto.setVersion(0);
        transactionHeaderDto.setType(4);
        transactionHeaderDto.setSubType(0);
        transactionHeaderDto.setTimestamp(currentTime);

        if (StringUtils.equals("F", sysContractStatusTxDto.getOp())) {
            transactionHeaderDto.setSubType(9);
        } else if (StringUtils.equals("A", sysContractStatusTxDto.getOp())) {
            transactionHeaderDto.setSubType(10);
        }


        transactionDto.setTransactionHeader(transactionHeaderDto);
        transactionDto.setPubKey(peerPubKey.toByteArray());

        return transactionDto;

    }
}
