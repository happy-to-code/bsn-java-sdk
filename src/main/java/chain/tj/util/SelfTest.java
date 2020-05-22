package chain.tj.util;

import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.proto.MyPeer;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static chain.tj.util.PeerUtil.getPeerTxDtoBytes;
import static chain.tj.util.PeerUtil.toHexString;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/20 11:23
 */
public class SelfTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        String property = System.getProperty("user.dir");
        System.out.println("property = " + property);


        System.out.println("-----------------------------------------------------------------");
        PeerTxDto peerTxDto = new PeerTxDto();
        peerTxDto.setOpType(0);
        peerTxDto.setPeerType(0);
        peerTxDto.setId("abc");
        // peerTxDto.setShownName("newName");
        peerTxDto.setLanAddrs(Arrays.asList("10.1.3.150"));
        peerTxDto.setWlanAddrs(Arrays.asList("10.1.3.150"));
        peerTxDto.setRpcPort(9008);

        byte[] peerTxDtoBytes = getPeerTxDtoBytes(peerTxDto);
        String s = toHexString(peerTxDtoBytes);
        System.out.println("s = " + s);
        // 0000000000000000030000006162630a0000000a00000031302e312e332e3135300a0000000a00000031302e312e332e31353030230000
        // 000000000000000003000000616263010000000a00000031302e312e332e313530010000000a00000031302e312e332e31353030230000


        // List<String> strings = Arrays.asList("10.1.3.150");
        // ByteBuf buf = Unpooled.buffer();
        // for (String lanAddr : strings) {
        //     buf.writeBytes(int2Bytes(lanAddr.getBytes().length));
        //     buf.writeBytes(lanAddr.getBytes());
        // }
        //
        // byte[] byteReturn = new byte[buf.writerIndex()];
        // byte[] array = buf.array();
        // for (int i = 0; i < byteReturn.length; i++) {
        //     byteReturn[i] = array[i];
        // }
        // String s = toHexString(byteReturn);
        // System.out.println("s = " + s);

        // 0000000000000000030000006162630a0000000a00000031302e312e332e3135300a0000000a00000031302e312e332e31353030230000
        // 000000000000000003000000616263010000000a00000031302e312e332e313530010000000a00000031302e312e332e31353030230000

        System.out.println("=================================");
        MyPeer.BalanceAddress.Builder builder = MyPeer.BalanceAddress.newBuilder();
        builder.setAddr("abc");


        MyPeer.BalanceAddress balanceAddress = builder.build();
        System.out.println("balanceAddress = " + balanceAddress);
        System.out.println("builder = " + builder);
        System.out.println("balanceAddress.toByteArray() = " + balanceAddress.toByteArray());
        System.out.println("balanceAddress.toByteArray()十六进制： = " + toHexString(balanceAddress.toByteArray()));


        //反序列化
        MyPeer.BalanceAddress balanceAddress1 = MyPeer.BalanceAddress.parseFrom(balanceAddress.toByteArray());
        System.out.println("after :" + balanceAddress1.toString());


        // 03000000 616263 03000000 646566
        // 0a03     616263 1203     646566
    }
}
