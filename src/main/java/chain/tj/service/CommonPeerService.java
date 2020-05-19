package chain.tj.service;

import chain.tj.model.proto.PeerGrpc;


public interface CommonPeerService {
    /**
     * 根据ip和port获取PeerBlockingStub
     *
     * @param ip
     * @param port
     * @return
     */
    PeerGrpc.PeerBlockingStub getStubByIpAndPort(String ip, Integer port);
}
