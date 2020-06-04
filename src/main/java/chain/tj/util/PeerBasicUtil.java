package chain.tj.util;

import chain.tj.common.exception.ServiceException;
import chain.tj.model.pojo.dto.PeerCommonDataDto;
import chain.tj.model.pojo.dto.PeerConnectionDto;
import chain.tj.model.pojo.vo.TxCommonDataVo;
import chain.tj.model.proto.PeerGrpc;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chain.tj.util.GmUtils.priToPubKey;
import static chain.tj.util.GmUtils.sm2Sign;
import static chain.tj.util.PeerUtil.*;
import static chain.tj.util.TjParseEncryptionKey.readKeyFromPem;
import static chain.tj.util.TransactionUtil.convertBuf;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/6/3 17:07
 */
public class PeerBasicUtil {
    public static void main(String[] args) {
        String s1 = "1.1.1.1";
        String s = "255.255.255.255";
        System.out.println("s1.length() = " + s1.length());
        System.out.println("s.length() = " + s.length());
    }

    /**
     * 获取连接
     *
     * @param connectionDtoList
     * @return
     */
    public static List<PeerGrpc.PeerBlockingStub> getStubList(List<PeerConnectionDto> connectionDtoList) {
        if (null == connectionDtoList || connectionDtoList.size() <= 0) {
            throw new ServiceException("参数对象不可以为空！");
        }

        List<PeerGrpc.PeerBlockingStub> stubs = new ArrayList<>(connectionDtoList.size());
        for (PeerConnectionDto dto : connectionDtoList) {
            String ip = dto.getIp().trim();
            Integer port = dto.getPort();
            if (StringUtils.isNotBlank(ip) && ip.length() >= 7 && ip.length() <= 15 && port != null && port > 0) {
                stubs.add(getStubByIpAndPort(ip, port));
            }
        }

        return stubs;
    }


    /**
     * 获取和链交互的共有对象
     *
     * @param dataDto
     * @return
     */
    public static TxCommonDataVo getCommonData(PeerCommonDataDto dataDto) {
        if (null == dataDto) {
            return null;
        }

        // 返回体
        TxCommonDataVo commonDataVo = new TxCommonDataVo();

        // 处理PubKey
        if (StringUtils.isNoneBlank(dataDto.getPubKeyFilePath())) {
            // 获取私钥字节码
            String pubKeyStr = readFile(dataDto.getPubKeyFilePath());
            // 将字符串的pubKey转换成ByteString
            ByteString pubKey = convertPubKeyToByteString(pubKeyStr);
            if (null != pubKey) {
                commonDataVo.setPubKeyByte(pubKey.toByteArray());
            }
        }

        // 处理wvmFile
        if (StringUtils.isNotBlank(dataDto.getWvmFilePath())) {
            // 获取合约文件
            String contractFile = readFile(dataDto.getWvmFilePath());
            if (StringUtils.isNotBlank(contractFile)) {
                ByteBuf buf = Unpooled.buffer();
                buf.writeBytes(contractFile.getBytes());
                byte[] content = convertBuf(buf);

                commonDataVo.setWvmFileBytes(content);
            }
        }

        // 处理私钥对和签名
        if (StringUtils.isNotBlank(dataDto.getPriKeyFilePath())) {
            Map<String, byte[]> keyPairAndSign = new HashMap<>(2);

            // 获取私钥
            byte[] priKeyBytes = new byte[0];
            try {
                priKeyBytes = readKeyFromPem(dataDto.getPriKeyFilePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 用私钥获取公钥
            byte[] pubKey = priToPubKey(priKeyBytes);

            // 获取自己的签名
            byte[] signBytes = new byte[0];
            try {
                signBytes = sm2Sign(priKeyBytes, "ownersign".getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 将key和sign放入map
            keyPairAndSign.put("priKey", priKeyBytes);
            keyPairAndSign.put("pubKey", pubKey);
            keyPairAndSign.put("sign", signBytes);

            commonDataVo.setKeyPairAndSign(keyPairAndSign);
        }

        return commonDataVo;
    }


    /**
     * 校验参数
     *
     * @param stubList
     * @param txCommonDataVo
     */
    public static void checkingParam(List<PeerGrpc.PeerBlockingStub> stubList, TxCommonDataVo txCommonDataVo) {
        if (stubList == null || stubList.size() <= 0) {
            throw new ServiceException("连接对象不可以为空");
        }
        if (null == txCommonDataVo || null == txCommonDataVo.getPubKeyByte()) {
            throw new ServiceException("获取公钥数据失败");
        }
    }
}
