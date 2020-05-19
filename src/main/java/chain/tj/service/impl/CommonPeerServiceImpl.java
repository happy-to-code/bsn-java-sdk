package chain.tj.service.impl;

import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.CommonPeerService;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/19 17:28
 */
@Service
public class CommonPeerServiceImpl implements CommonPeerService {

    /**
     * 根据ip和port获取PeerBlockingStub
     *
     * @param ip
     * @param port
     * @return
     */
    @Override
    public PeerGrpc.PeerBlockingStub getStubByIpAndPort(String ip, Integer port) {
        ManagedChannel channel = NettyChannelBuilder.forAddress(ip, port)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();

        return PeerGrpc.newBlockingStub(channel);
    }
}
