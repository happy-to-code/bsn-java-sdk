package chain.tj.service.systemtx;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.PermissionTxDto;
import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.dto.TransactionHeaderDto;
import chain.tj.model.pojo.query.BasicTxObj;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.proto.MyPeer;
import chain.tj.service.SystemTx;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static chain.tj.util.PeerUtil.int2Bytes;
import static chain.tj.util.PeerUtil.toHexString;
import static chain.tj.util.TransactionUtil.*;

/**
 * @Describe:权限变更
 * @Author: zhangyifei
 * @Date: 2020/5/25 9:56
 */
@Service
@Slf4j
public class CreateSystemPM implements SystemTx {

    /**
     * 权限变更
     *
     * @param newTxQueryDto
     * @return
     */
    @Override
    public RestResponse newTransaction(NewTxQueryDto newTxQueryDto) {
        // 获取交易基本对象
        BasicTxObj basicTxObj = getBasicTxObj(newTxQueryDto);
        ByteString peerPubKey = basicTxObj.getPubKey();
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // 创建交易参数对象
        TransactionDto transactionDto = createTransactionDto(basicTxObj.getCurrentTime());
        transactionDto.setPubKey(peerPubKey.toByteArray());

        // 构建 PermissionTxDto 对象
        PermissionTxDto permissionTxDto = getPermissionDto(newTxQueryDto, peerPubKey);

        // 权限交易 序列化
        byte[] permissionTxDtoBytes = serialPermissionTxDto(permissionTxDto);
        log.info("permissionTxDtoBytes的十六进制：{}", toHexString(permissionTxDtoBytes));

        // 获取sysData
        byte[] sysData = systxEncode(peerPubKey, permissionTxDtoBytes);
        transactionDto.setData(sysData);
        log.info("sysData的十六进制：{}", toHexString(sysData));

        // 给TransactionDto对象赋值
        setValueForTransactionDto(transactionDto, newTxQueryDto.getPriKeyPath());

        MyPeer.PeerRequest request = getPeerRequest(transactionDto, peerPubKey);

        // 调用接口
        MyPeer.PeerResponse peerResponse = basicTxObj.getStub().newTransaction(request);
        log.info("peerResponse--->{}", peerResponse);

        if (peerResponse.getOk()) {
            return RestResponse.success();
        }

        return RestResponse.failure("权限变更失败", StatusCode.SERVER_500000.value());
    }


    /**
     * 获取sysData
     *
     * @param peerPubKey
     * @param permissionTxDtoBytes
     * @return
     */
    private byte[] systxEncode(ByteString peerPubKey, byte[] permissionTxDtoBytes) {
        ByteBuf buf = Unpooled.buffer();

        buf.writeBytes(int2Bytes(peerPubKey.toByteArray().length));
        buf.writeBytes(peerPubKey.toByteArray());

        buf.writeBytes(int2Bytes(3));

        // 写入数据
        buf.writeBytes(int2Bytes(permissionTxDtoBytes.length));
        buf.writeBytes(permissionTxDtoBytes);

        return convertBuf(buf);
    }

    /**
     * 权限交易 序列化
     *
     * @param permissionTxDto
     * @return
     */
    private byte[] serialPermissionTxDto(PermissionTxDto permissionTxDto) {
        ByteBuf buf = Unpooled.buffer();

        buf.writeBytes(int2Bytes(permissionTxDto.getPeerId().getBytes().length));
        buf.writeBytes(permissionTxDto.getPeerId().getBytes());

        if (null != permissionTxDto.getPm() && permissionTxDto.getPm().size() > 0) {
            buf.writeBytes(int2Bytes(permissionTxDto.getPm().size()));
            if (permissionTxDto.getPm().size() > 0) {
                for (Integer pm : permissionTxDto.getPm()) {
                    buf.writeBytes(int2Bytes(pm));
                }
            }
        } else {
            buf.writeInt(0);
        }

        if (null != permissionTxDto.getShownName()) {
            buf.writeBytes(int2Bytes(permissionTxDto.getShownName().length()));
            buf.writeBytes(permissionTxDto.getShownName().getBytes());
        }

        return convertBuf(buf);
    }

    /**
     * 构建 PermissionTxDto 对象
     *
     * @param newTxQueryDto
     * @param peerPubKey
     * @return
     */
    private PermissionTxDto getPermissionDto(NewTxQueryDto newTxQueryDto, ByteString peerPubKey) {
        PermissionTxDto permissionTxDto = new PermissionTxDto();

        permissionTxDto.setPeerId(String.valueOf(peerPubKey));
        if (null != newTxQueryDto.getPermissionTxDto() &&
                null != newTxQueryDto.getPermissionTxDto().getPm() && newTxQueryDto.getPermissionTxDto().getPm().size() > 0) {
            permissionTxDto.setPm(newTxQueryDto.getPermissionTxDto().getPm());
        }
        if (StringUtils.isNoneBlank(newTxQueryDto.getPermissionTxDto().getShownName())) {
            permissionTxDto.setShownName(newTxQueryDto.getPermissionTxDto().getShownName());
        }

        return permissionTxDto;
    }

    /**
     * 创建交易参数对象
     *
     * @param currentTime
     * @return
     */
    private TransactionDto createTransactionDto(long currentTime) {
        TransactionDto transactionDto = new TransactionDto();

        TransactionHeaderDto transactionHeader = new TransactionHeaderDto();
        // 默认版本
        transactionHeader.setVersion(0);
        transactionHeader.setType(4);
        transactionHeader.setSubType(3);
        transactionHeader.setTimestamp(currentTime);

        // transactionDto 设置头对象
        transactionDto.setTransactionHeader(transactionHeader);

        return transactionDto;
    }
}
