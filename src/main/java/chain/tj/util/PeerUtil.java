package chain.tj.util;

import chain.tj.model.pojo.dto.PeerTxDto;
import chain.tj.model.proto.PeerGrpc;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class PeerUtil {


    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2), 16);
            j++;
        }
        return result;
    }


    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex, int radix) {
        return (byte) Integer.parseInt(inHex, radix);
    }

    /**
     * 将10进制转换成16进制
     *
     * @param byt
     * @return
     */
    public static String covent10To16Str(byte[] byt) {
        StringBuilder sb = new StringBuilder();
        if (byt.length > 0) {
            for (byte b : byt) {
                String s = Integer.toHexString(b);
                sb.append(s);
            }
        }
        return sb.toString();
    }

    // 16进制字符
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 方法一：将byte类型数组转化成16进制字符串
     *
     * @param bytes
     * @return
     * @explain 字符串拼接
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int num;
        for (byte b : bytes) {
            num = b < 0 ? 256 + b : b;
            sb.append(HEX_CHAR[num / 16]).append(HEX_CHAR[num % 16]);
        }
        return sb.toString();
    }

    /**
     * 将pubKey转换成ByteString
     *
     * @param str
     * @return
     */
    public static ByteString convertPubKeyToByteString(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        byte[] bytes = hexToByteArray(str);
        return ByteString.copyFrom(bytes);
    }

    /**
     * 通过ip和端口获取PeerBlockingStub
     *
     * @param
     * @param
     * @return
     */
    public static PeerGrpc.PeerBlockingStub getStubByIpAndPort(String ip, Integer port) {
        ManagedChannel channel = NettyChannelBuilder.forAddress("10.1.3.150", 9008)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();

        return PeerGrpc.newBlockingStub(channel);
    }


    public static String bytesToString(ByteString src, String charSet) {
        if (StringUtils.isEmpty(charSet)) {
            charSet = "GB2312";
        }
        return bytesToString(src.toByteArray(), charSet);
    }

    public static String bytesToString(byte[] input, String charSet) {
        if (ArrayUtils.isEmpty(input)) {
            return StringUtils.EMPTY;
        }

        ByteBuffer buffer = ByteBuffer.allocate(input.length);
        buffer.put(input);
        buffer.flip();

        Charset charset;
        CharsetDecoder decoder;
        CharBuffer charBuffer;

        try {
            charset = Charset.forName(charSet);
            decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());

            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte) ((value >> 8 * i));
        }
        return b;
    }

    public static byte[] int2Bytes(int integer) {
        byte[] bytes = new byte[4];
        bytes[3] = (byte) (integer >> 24);
        bytes[2] = (byte) (integer >> 16);
        bytes[1] = (byte) (integer >> 8);
        bytes[0] = (byte) integer;

        return bytes;
    }

    public static byte[] long2Bytes(long data) {
        byte[] bytes = new byte[8];

        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        bytes[4] = (byte) ((data >> 32) & 0xff);
        bytes[5] = (byte) ((data >> 40) & 0xff);
        bytes[6] = (byte) ((data >> 48) & 0xff);
        bytes[7] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }

    private static ByteBuffer buffer = ByteBuffer.allocate(8);


    /**
     * byte 数组与 long 的相互转换
     *
     * @param x
     * @return
     */
    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static int bytes2Int2(byte[] bytes) {
        int int1 = bytes[0] & 0xff;
        int int2 = (bytes[1] & 0xff) << 8;
        int int3 = (bytes[2] & 0xff) << 16;
        int int4 = (bytes[3] & 0xff) << 24;

        return int1 | int2 | int3 | int4;
    }

    /**
     * 利用Apache的工具类实现SHA-256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str) {
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

    public static byte[] getRandomByte(int len) {
        if (len <= 0) {
            return null;
        }
        byte[] bytes = new byte[len];
        Random random = new Random();
        random.nextBytes(bytes);
        return bytes;
    }

    public static String getRandomByteStr(int len) {
        if (len <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("[");
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append((byte) random.nextInt()).append(",");
        }
        String substring = sb.toString().substring(0, sb.toString().lastIndexOf(","));


        return substring + "]";
    }


    /**
     * 获取TransactionHash
     *
     * @param peerTxDto
     * @return
     */
    public static byte[] getPeerTxDtoBytes(PeerTxDto peerTxDto) {
        ByteBuf buf = Unpooled.buffer();

        if (null != peerTxDto.getOpType()) {
            buf.writeBytes(int2Bytes(peerTxDto.getOpType()));
        }
        if (null != peerTxDto.getPeerType()) {
            buf.writeBytes(int2Bytes(peerTxDto.getPeerType()));
        }

        if (null != peerTxDto.getId()) {
            buf.writeBytes(int2Bytes(peerTxDto.getId().getBytes().length));
            buf.writeBytes(peerTxDto.getId().getBytes());
        }

        if (null != peerTxDto.getShownName()) {
            buf.writeBytes(int2Bytes(peerTxDto.getShownName().length()));
            buf.writeBytes(peerTxDto.getShownName().getBytes());
        }

        buf.writeBytes(int2Bytes(peerTxDto.getLanAddrs().size()));
        if (peerTxDto.getLanAddrs().size() > 0) {
            for (String lanAddr : peerTxDto.getLanAddrs()) {
                buf.writeBytes(int2Bytes(lanAddr.getBytes().length));
                buf.writeBytes(lanAddr.getBytes());
            }
        }

        buf.writeBytes(int2Bytes(peerTxDto.getWlanAddrs().size()));
        if (peerTxDto.getWlanAddrs().size() > 0) {
            for (String wlanAddr : peerTxDto.getWlanAddrs()) {
                buf.writeBytes(int2Bytes(wlanAddr.getBytes().length));
                buf.writeBytes(wlanAddr.getBytes());
            }
        }

        buf.writeBytes(int2Bytes(peerTxDto.getRpcPort()));

        byte[] byteReturn = new byte[buf.writerIndex()];

        byte[] array = buf.array();
        for (int i = 0; i < byteReturn.length; i++) {
            byteReturn[i] = array[i];
        }
        return byteReturn;
    }

    /**
     * 数据的序列化
     *
     * @param dataBytes   数据
     * @param pubKeyBytes 公钥
     * @param o           数据类型
     * @return
     */
    public static byte[] dataSerializable(byte[] dataBytes, byte[] pubKeyBytes, Object o) {
        ByteBuf buf = Unpooled.buffer();
        // 写入公钥
        buf.writeBytes(int2Bytes(pubKeyBytes.length));
        buf.writeBytes(pubKeyBytes);

        if (o instanceof PeerTxDto) {
            PeerTxDto peerTxDto = (PeerTxDto) o;
            Integer opType = peerTxDto.getOpType();
            buf.writeBytes(int2Bytes(opType));
        }

        // 写入数据
        buf.writeBytes(int2Bytes(dataBytes.length));
        buf.writeBytes(dataBytes);

        // 封装返回数据
        byte[] byteReturn = new byte[buf.writerIndex()];

        byte[] array = buf.array();
        for (int i = 0; i < byteReturn.length; i++) {
            byteReturn[i] = array[i];
        }
        return byteReturn;
    }

    /**
     * 对象转数组
     *
     * @param obj
     * @return
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 数组转对象
     *
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static void main(String[] args) {
        PeerTxDto peerTxDto = new PeerTxDto();
        peerTxDto.setOpType(0);
        peerTxDto.setPeerType(0);
        peerTxDto.setId("abc");
        peerTxDto.setShownName("newName");
        peerTxDto.setLanAddrs(Arrays.asList("10.1.3.150"));
        peerTxDto.setWlanAddrs(Arrays.asList("10.1.3.150"));
        peerTxDto.setRpcPort(9999);

        System.out.println("peerTxDto = " + peerTxDto);

        byte[] bytes = toByteArray(peerTxDto);
        for (byte aByte : bytes) {
            System.out.print(aByte);
        }
        System.out.println("----->" + toHexString(bytes));

        byte[] peerTxDtoBytes = getPeerTxDtoBytes(peerTxDto);

        for (byte peerTxDtoByte : peerTxDtoBytes) {
            System.out.print(peerTxDtoByte);
        }
        System.out.println("peerTxDtoBytes = " + peerTxDtoBytes);
        System.out.println("peerTxDtoBytes十六进制：" + toHexString(peerTxDtoBytes));

        // 将byte数组序列化成对象
        // Object o = toObject(toByteArray(peerTxDto));
        // System.out.println("o = " + (PeerTxDto) o);

        // System.out.println(getRandomByteStr(3));
        //
        //
        // // byte[] random = getRandomByte(16);
        // // for (byte b : random) {
        // //     System.out.print(b + " ");
        // // }
        //
        //
        // String sha256Str = getSHA256Str("123");
        // System.out.println("sha256Str = " + sha256Str);
        // byte[] bytes = int2Bytes(200, 4);
        // for (byte aByte : bytes) {
        //     System.out.print(aByte + " ");
        // }
        // System.out.println("----------");
        // byte[] bytes1 = int2Bytes(200);
        // for (byte b : bytes1) {
        //     System.out.print(b + " ");
        // }
        // System.out.println("----------");
        // byte[] bytes2 = longToBytes(200L);
        // for (byte b : bytes2) {
        //     System.out.print(b + " ");
        // }
        // String s = "2c7f6f353d828e99692bb8bf960186f218674581495b399db753c00dd636c4f0583f7a833ce67d352e7d32be5d6e3fc899d7004efe1f450fc1a078ee856a8b75";
        // byte[] bytes = hexToByteArray(s, 16);
        // for (byte aByte : bytes) {
        //
        //     System.out.println("bytes = " + aByte);
        // }
    }
}
