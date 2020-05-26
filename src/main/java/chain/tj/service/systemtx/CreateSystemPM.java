package chain.tj.service.systemtx;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.PermissionTxDto;
import chain.tj.model.pojo.dto.TransactionDto;
import chain.tj.model.pojo.dto.TransactionHeaderDto;
import chain.tj.model.pojo.query.NewTxQueryDto;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.SystemTx;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static chain.tj.util.PeerUtil.*;
import static chain.tj.util.TransactionUtil.getPeerRequest;
import static chain.tj.util.TransactionUtil.setValueForTransactionDto;

/**
 * @Describe:权限变更
 * @Author: zhangyifei
 * @Date: 2020/5/25 9:56
 */
@Service
@Slf4j
public class CreateSystemPM implements SystemTx {

    @Value("${peer.pubKey}")
    // private String pubKey = "7408b0d5577d5be240afbf0712397e5b19df9213f4d7c2cfa98d0d545fa9fc013a2a8fc4046c62e4b8fc00c524a92cd66d54cd2fc8c88c834d06ba7a951eea73";
    private String pubKey = "2c7f6f353d828e99692bb8bf960186f218674581495b399db753c00dd636c4f0583f7a833ce67d352e7d32be5d6e3fc899d7004efe1f450fc1a078ee856a8b75";


    /**
     * 权限变更
     *
     * @param newTxQueryDto
     * @return
     */
    @Override
    public RestResponse newTransaction(NewTxQueryDto newTxQueryDto) {
        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // 获取当前时间戳
        long currentTime = System.currentTimeMillis() / 1000;
        // long currentTime = 1590390219;

        // 创建交易参数对象
        TransactionDto transactionDto = createTransactionDto(currentTime);
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
        setValueForTransactionDto(transactionDto);

        MyPeer.PeerRequest request = getPeerRequest(transactionDto, peerPubKey);

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(newTxQueryDto.getAddr(), newTxQueryDto.getRpcPort());
        // 调用接口
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);
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

        byte[] bytes1 = new byte[buf.writerIndex()];

        byte[] array = buf.array();
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i] = array[i];
        }
        return bytes1;
    }

    /**
     * 权限交易 序列化
     *
     * @param permissionTxDto
     * @return
     */
    private byte[] serialPermissionTxDto(PermissionTxDto permissionTxDto) {
        ByteBuf buf = Unpooled.buffer();

        buf.writeBytes(int2Bytes(permissionTxDto.getPeerId().length));
        buf.writeBytes(permissionTxDto.getPeerId());

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

        byte[] bytes1 = new byte[buf.writerIndex()];

        byte[] array = buf.array();
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i] = array[i];
        }
        return bytes1;
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

        permissionTxDto.setPeerId(peerPubKey.toByteArray());
        if (null != newTxQueryDto.getPermission() && newTxQueryDto.getPermission().size() > 0) {
            permissionTxDto.setPm(newTxQueryDto.getPermission());
        }

        if (StringUtils.isNoneBlank(newTxQueryDto.getShownName())) {
            permissionTxDto.setShownName(newTxQueryDto.getShownName());
        }

        if (StringUtils.isNoneBlank(newTxQueryDto.getSubPeerName())) {
            permissionTxDto.setSubPeerName(newTxQueryDto.getSubPeerName());
        }

        if (StringUtils.isNoneBlank(newTxQueryDto.getRpcAddr())) {
            permissionTxDto.setRpcAddr(newTxQueryDto.getRpcAddr());
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