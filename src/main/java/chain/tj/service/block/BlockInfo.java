package chain.tj.service.block;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.vo.BlockInfoVo;
import chain.tj.model.proto.Msg;
import chain.tj.model.proto.MyBlock;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Block;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static chain.tj.util.PeerUtil.*;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/26 17:14
 */
@Service
@Slf4j
public class BlockInfo implements Block {

    @Value("${peer.pubKey}")
    private String pubKey = "2c7f6f353d828e99692bb8bf960186f218674581495b399db753c00dd636c4f0583f7a833ce67d352e7d32be5d6e3fc899d7004efe1f450fc1a078ee856a8b75";

    /**
     * 获取区块高度
     *
     * @return
     */
    @Override
    public RestResponse blockHeight() {
        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);
        log.info("peerPubKey的十六进制：{}", toHexString(peerPubKey.toByteArray()));

        // 封装请求对象
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder().setPubkey(peerPubKey).build();

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort("", 1);

        // 获取高度
        MyPeer.PeerResponse response = stub.blockchainGetHeight(request);

        if (!response.getOk()) {
            return RestResponse.failure("请求出错！", StatusCode.SERVER_500000.value());
        }

        Msg.BlockchainNumber blockchainNumber;
        try {
            blockchainNumber = Msg.BlockchainNumber.parseFrom(response.getPayload());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return RestResponse.failure("请求出错:" + e.getMessage(), StatusCode.SERVER_500000.value());
        }

        return RestResponse.success().setData(blockchainNumber.getNumber());
    }

    /**
     * 根据区块高度区块信息
     *
     * @param height
     * @return
     */
    @Override
    public RestResponse getBlockByHeight(Integer height) {
        if (null == height || height <= 0) {
            height = 0;
        }

        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);

        // 封装请求对象
        Msg.BlockchainNumber blockchainNumber = Msg.BlockchainNumber.newBuilder().setNumber(height).build();
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(peerPubKey)
                .setPayload(blockchainNumber.toByteString())
                .build();

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort("", 1);
        MyPeer.PeerResponse peerResponse = stub.blockchainGetBlockByHeight(request);

        MyBlock.Block block;
        try {
            block = MyBlock.Block.parseFrom(peerResponse.getPayload());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return RestResponse.failure("解析区块出错:" + e.getMessage(), StatusCode.SERVER_500000.value());
        }

        // 转换区块头信息
        BlockInfoVo blockInfoVo = convertBlockHead(block);

        return RestResponse.success().setData(blockInfoVo);
    }

    /**
     * 根据hash值查询区块信息
     *
     * @param hash
     * @return
     */
    @Override
    public RestResponse getBlockByHash(String hash) {
        // 将16进制的pubKey转换成ByteString
        ByteString peerPubKey = convertPubKeyToByteString(pubKey);

        //PeerRequest: BlockchainHash, PeerResponse:Block
        // rpc BlockchainGetBlockByHash(PeerRequest) returns(PeerResponse){};

        Msg.BlockchainHash blockchainHash = Msg.BlockchainHash.newBuilder().setHashData(ByteString.copyFromUtf8(hash)).build();
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(peerPubKey)
                .setPayload(blockchainHash.toByteString())
                .build();

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort("", 1);
        MyPeer.PeerResponse peerResponse = stub.blockchainGetBlockByHeight(request);

        System.out.println("peerResponse = " + peerResponse);

        MyBlock.Block block;
        try {
            block = MyBlock.Block.parseFrom(peerResponse.getPayload());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return RestResponse.failure("解析区块信息出错:" + e.getMessage(), StatusCode.SERVER_500000.value());
        }

        // 转换区块头信息
        BlockInfoVo blockInfoVo = convertBlockHead(block);

        return RestResponse.success().setData(blockInfoVo);
    }

    /**
     * 转换区块头信息
     *
     * @param block
     * @return
     */
    private BlockInfoVo convertBlockHead(MyBlock.Block block) {
        BlockInfoVo blockInfoVo = new BlockInfoVo();

        if (null != block.getHeader()) {
            blockInfoVo.setHeight(block.getHeader().getHeight());
            blockInfoVo.setVersion(block.getHeader().getVersion());
            blockInfoVo.setTimestamp(block.getHeader().getTimestamp());
            blockInfoVo.setBlockHash(arr2HexStr(block.getHeader().getBlockHash().toByteArray()));
            blockInfoVo.setPreviousHash(arr2HexStr(block.getHeader().getPreviousHash().toByteArray()));
            blockInfoVo.setWorldStateRoot(arr2HexStr(block.getHeader().getWorldStateRoot().toByteArray()));
            blockInfoVo.setTransactionRoot(arr2HexStr(block.getHeader().getTransactionRoot().toByteArray()));
        }

        if (null != block.getExtra() && block.getExtra().toByteArray() != null) {
            blockInfoVo.setExtra(arr2HexStr(block.getExtra().toByteArray()));
        }

        if (null != block.getTxsList() && block.getTxsList().size() > 0) {
            List<String> txList = new ArrayList<>(block.getTxsList().size());
            for (ByteString bytes : block.getTxsList()) {
                txList.add(arr2HexStr(bytes.toByteArray()));
            }
            blockInfoVo.setTxs(txList);
        }

        return blockInfoVo;
    }
}
