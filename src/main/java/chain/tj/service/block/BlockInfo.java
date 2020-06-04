package chain.tj.service.block;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.vo.BlockInfoVo;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.Msg;
import chain.tj.model.proto.MyBlock;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Block;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static chain.tj.util.PeerBasicUtil.checkingParam;
import static chain.tj.util.PeerUtil.arr2HexStr;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/26 17:14
 */
@Service
@Slf4j
public class BlockInfo implements Block {

    /**
     * 获取区块高度
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @return
     */
    @Override
    public RestResponse blockHeight(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo) {
        // 验证参数
        checkingParam(stubList, txCommonDataVo);

        // 封装请求对象
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder().setPubkey(ByteString.copyFrom(txCommonDataVo.getPubKeyByte())).build();

        Msg.BlockchainNumber blockChainNumber = null;
        for (PeerGrpc.PeerBlockingStub stub : stubList) {
            // 调用接口
            MyPeer.PeerResponse peerResponse;
            try {
                // 获取高度
                peerResponse = stub.blockchainGetHeight(request);
                // 成功
                if (null != peerResponse && peerResponse.getOk()) {
                    try {
                        blockChainNumber = Msg.BlockchainNumber.parseFrom(peerResponse.getPayload());
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                        continue;
                    }
                    break;
                } else {
                    // 如果调用一个节点失败  尝试另外的节点
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (null == blockChainNumber) {
            return RestResponse.failure("查询数据失败", StatusCode.SERVER_500000.value());
        }

        return RestResponse.success().setData(blockChainNumber);
    }

    /**
     * 根据区块高度区块信息
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @param height
     * @return
     */
    @Override
    public RestResponse getBlockByHeight(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, Integer height) {
        // 验证参数
        checkingParam(stubList, txCommonDataVo);
        if (null == height || height <= 0) {
            height = 0;
        }

        // 封装请求对象
        Msg.BlockchainNumber blockChainNumber = Msg.BlockchainNumber.newBuilder().setNumber(height).build();
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(txCommonDataVo.getPubKeyByte()))
                .setPayload(blockChainNumber.toByteString())
                .build();

        MyBlock.Block block = null;
        for (PeerGrpc.PeerBlockingStub stub : stubList) {
            // 调用接口
            MyPeer.PeerResponse peerResponse;
            try {
                // 获取高度
                peerResponse = stub.blockchainGetBlockByHeight(request);
                // 成功
                if (null != peerResponse && peerResponse.getOk()) {
                    try {
                        block = MyBlock.Block.parseFrom(peerResponse.getPayload());
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                        continue;
                    }
                    break;
                } else {
                    // 如果调用一个节点失败  尝试另外的节点
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (null == block) {
            return RestResponse.failure("查询数据失败", StatusCode.SERVER_500000.value());
        }

        // 转换区块头信息 并放入返回体
        return RestResponse.success().setData(convertBlockHead(block));

    }

    /**
     * 根据hash值查询区块信息
     *
     * @param stubList       连接数组
     * @param txCommonDataVo 交易公共数据对象
     * @param hash
     * @return
     */
    @Override
    public RestResponse getBlockByHash(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo, String hash) {
        // 验证参数
        checkingParam(stubList, txCommonDataVo);

        Msg.BlockchainHash blockChainHash = Msg.BlockchainHash.newBuilder().setHashData(ByteString.copyFromUtf8(hash)).build();
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(txCommonDataVo.getPubKeyByte()))
                .setPayload(blockChainHash.toByteString())
                .build();

        MyBlock.Block block = null;
        for (PeerGrpc.PeerBlockingStub stub : stubList) {
            // 调用接口
            MyPeer.PeerResponse peerResponse;
            try {
                // 获取高度
                peerResponse = stub.blockchainGetBlockByHash(request);
                // 成功
                if (null != peerResponse && peerResponse.getOk()) {
                    try {
                        block = MyBlock.Block.parseFrom(peerResponse.getPayload());
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                        continue;
                    }
                    break;
                } else {
                    // 如果调用一个节点失败  尝试另外的节点
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (null == block) {
            return RestResponse.failure("查询数据失败", StatusCode.SERVER_500000.value());
        }

        // 转换区块头信息 并放入返回体
        return RestResponse.success().setData(convertBlockHead(block));
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
