package chain.tj.util;

import chain.tj.model.proto.PeerGrpc;

import java.util.ArrayList;
import java.util.List;

import static chain.tj.util.PeerUtil.getStubByIpAndPort;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/20 11:23
 */
public class SelfTest {
    public static void main(String[] args) {
        List<PeerGrpc.PeerBlockingStub> stubList = new ArrayList<>(10);
        PeerGrpc.PeerBlockingStub stub0 = getStubByIpAndPort("10.1.3.150", 9008);
        PeerGrpc.PeerBlockingStub stub1 = getStubByIpAndPort("10.1.3.151", 9008);
        PeerGrpc.PeerBlockingStub stub2 = getStubByIpAndPort("10.1.3.152", 9008);

        stubList.add(stub0);
        stubList.add(stub1);
        stubList.add(stub2);

        for (PeerGrpc.PeerBlockingStub peerBlockingStub : stubList) {
            System.out.println("peerBlockingStub = " + peerBlockingStub);
        }
    }
}
