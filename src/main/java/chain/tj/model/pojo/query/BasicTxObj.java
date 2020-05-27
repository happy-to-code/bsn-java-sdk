package chain.tj.model.pojo.query;

import chain.tj.model.proto.PeerGrpc;
import com.google.protobuf.ByteString;
import lombok.Data;

/**
 * @Describe:交易基本对象
 * @Author: zhangyifei
 * @Date: 2020/5/27 16:38
 */
@Data
public class BasicTxObj {

    /**
     * 序列化后的公钥
     */
    private ByteString pubKey;

    /**
     * 获取当前时间戳
     */
    private long currentTime;

    /**
     * grpc连接对象
     */
    private PeerGrpc.PeerBlockingStub stub;


}
