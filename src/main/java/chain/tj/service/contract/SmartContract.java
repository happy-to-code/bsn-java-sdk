package chain.tj.service.contract;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.ienum.FateSubType;
import chain.tj.model.ienum.TransactionType;
import chain.tj.model.ienum.Version;
import chain.tj.model.pojo.dto.ContractReq;
import chain.tj.model.pojo.dto.InvokeSmartContractReq;
import chain.tj.model.pojo.dto.contract.ContractCallArg;
import chain.tj.model.pojo.dto.contract.WVMContractTx;
import chain.tj.model.pojo.vo.InstallContractVo;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.MyTransaction;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Contract;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Pattern;

import static chain.tj.util.GmUtils.sm2Sign;
import static chain.tj.util.GmUtils.sm3Hash;
import static chain.tj.util.PeerUtil.*;
import static chain.tj.util.PeerUtil.arr2HexStr;
import static chain.tj.util.TransactionUtil.convertBuf;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/28 15:12
 */
@Service
@Slf4j
public class SmartContract implements Contract {


    /**
     * 安装智能合约
     *
     * @param contractReq
     * @return
     */
    @Override
    public RestResponse installSmartContract(ContractReq contractReq) {
        // 获取密钥对和签名
        Map<String, byte[]> keyPair = getKeyPairAndSign("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        if (keyPair.isEmpty()) {
            return RestResponse.failure("获取秘钥失败！", StatusCode.CLIENT_410021.value());
        }

        String fileBase64 = "//合约示例\n" +
                "contract Transfer12345 {\n" +
                "\t//初始化一个账户\n" +
                "\tpublic string init(){\n" +
                "\t\tdb_set(\"bob\",10000)\n" +
                "        db_set(\"jim\",10000)\n" +
                "        return \"success\"\n" +
                "\t}\n" +
                "\t\n" +
                "\t//转账操作\n" +
                "\tpublic string transfer(string from, string to, int amount) {\n" +
                "\t\tint balA = db_get<int>(from)\n" +
                "\t\tint balB = db_get<int>(to)\n" +
                "\t\tbalA = balA-amount\n" +
                "\t\tif (balA>0){\n" +
                "\t\t\tbalB = balB+amount\n" +
                "\t\t\tdb_set(from, balA)\n" +
                "\t\t\tdb_set(to, balB)\n" +
                "\t\t}else{\n" +
                "            return \"insufficient balance\"\n" +
                "        }\n" +
                "\t\treturn \"success\"\n" +
                "\t}\n" +
                "\n" +
                "\t//查询账户余额\n" +
                "\tpublic int getBalance(string account){\n" +
                "\t\treturn db_get<int>(account)\n" +
                "\t}\n" +
                "}";
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(fileBase64.getBytes());
        byte[] content = convertBuf(buf);

        // 获取随机字符串
        String randomStr = getRandomStr();

        ContractCallArg contractCallArg = new ContractCallArg();
        contractCallArg.setVersion("2");
        contractCallArg.setGas(10000000);

        WVMContractTx wvmContractTx = new WVMContractTx();
        wvmContractTx.setArg(contractCallArg);
        wvmContractTx.setOwner(keyPair.get("pubKey"));
        wvmContractTx.setRandom(randomStr.getBytes());
        wvmContractTx.setSrc(content);
        wvmContractTx.setSign(keyPair.get("sign"));

        // 序列化 wvmContractTx
        byte[] wvmContractTxBytes = serialWvmContractTx(wvmContractTx);

        // 获取请求体MyTransaction.Transaction getTransaction
        MyTransaction.Transaction transaction = getTransaction(contractReq.getLedger(), Version.VersionTwo.getValue(), TransactionType.SCWVM.getValue(), FateSubType.WVMSCInstall.getValue(), wvmContractTxBytes, keyPair.get("pubKey"), keyPair.get("priKey"));
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(keyPair.get("pubKey")))
                .setPayload(transaction.toByteString())
                .build();
        // 获取stub
        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort("10.1.3.150", 9008);
        // 发送请求
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);

        if (peerResponse.getOk()) {
            InstallContractVo installContractVo = new InstallContractVo();
            installContractVo.setName(arr2HexStr(peerResponse.getPayload().toByteArray()));
            installContractVo.setHashData(arr2HexStr(transaction.getHeader().getTransactionHash().toByteArray()));

            return RestResponse.success().setData(installContractVo);
        }

        return RestResponse.failure("创建合约失败！", StatusCode.SERVER_500000.value());
    }

    /**
     * 销毁合约
     *
     * @param contractReq
     * @return
     */
    @Override
    public RestResponse destorySmartContract(ContractReq contractReq) {
        if (StringUtils.isBlank(contractReq.getName())) {
            return RestResponse.failure("合约名称不可以为空！", StatusCode.CLIENT_410001.value());
        }

        // 使用正则验证名称
        String name = contractReq.getName();
        String pattern = "^[a-z0-9]+(?:[._-][a-z0-9]+)*$";

        boolean isMatch = Pattern.matches(pattern, name);
        if (!isMatch) {
            return RestResponse.failure("合约名称不合法！", StatusCode.CLIENT_410002.value());
        }

        // 获取密钥对和签名
        Map<String, byte[]> keyPairAndSign = getKeyPairAndSign("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        if (keyPairAndSign.isEmpty()) {
            return RestResponse.failure("获取秘钥失败！", StatusCode.CLIENT_410021.value());
        }

        // 获取数据字节码
        byte[] bodyByte = getBodyByte(name);

        // 获取请求体
        MyTransaction.Transaction transaction = getTransaction(contractReq.getLedger(), Version.VersionTwo.getValue(), TransactionType.SCWVM.getValue(), FateSubType.WVMSCDest.getValue(), bodyByte, keyPairAndSign.get("pubKey"), keyPairAndSign.get("priKey"));
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(keyPairAndSign.get("pubKey")))
                .setPayload(transaction.toByteString())
                .build();
        // 获取stub
        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort("10.1.3.150", 9008);
        // 发送请求
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);
        if (peerResponse.getOk()) {
            return RestResponse.success();
        }

        return RestResponse.failure("删除合约失败！", StatusCode.SERVER_500000.value());
    }

    /**
     * 调用合约
     *
     * @param invokeSmartContractReq
     * @return
     */
    @Override
    public RestResponse invokeSmartContract(InvokeSmartContractReq invokeSmartContractReq) {
        String name = invokeSmartContractReq.getName();
        if (StringUtils.isBlank(name)) {
            return RestResponse.failure("合约名称不可以为空", StatusCode.CLIENT_410001.value());
        }

        if (!StringUtils.equals("Sys_StoreEncrypted", name)) {
            // 使用正则验证名称
            String pattern = "^[a-z0-9]+(?:[._-][a-z0-9]+)*$";
            boolean isMatch = Pattern.matches(pattern, name);
            if (!isMatch) {
                return RestResponse.failure("合约名称不合法！", StatusCode.CLIENT_410002.value());
            }
        }
        // 获取密钥对和签名
        Map<String, byte[]> keyPairAndSign = getKeyPairAndSign("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        if (keyPairAndSign.isEmpty()) {
            return RestResponse.failure("获取秘钥失败！", StatusCode.CLIENT_410021.value());
        }

        // 获取InvokeSmartContract bodyByte
        byte[] bodyByte = getInvokeSmartContractBodyByte(invokeSmartContractReq);

        // 获取请求体
        MyTransaction.Transaction transaction = getTransaction(invokeSmartContractReq.getLedger(), Version.VersionTwo.getValue(), TransactionType.SCWVM.getValue(), FateSubType.WVMSCInvoke.getValue(), bodyByte, keyPairAndSign.get("pubKey"), keyPairAndSign.get("priKey"));
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(keyPairAndSign.get("pubKey")))
                .setPayload(transaction.toByteString())
                .build();
        // 获取stub
        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort("10.1.3.150", 9008);
        // 发送请求
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);
        if (peerResponse.getOk()) {


            return RestResponse.success();
        }

        return RestResponse.failure("调用合约失败！", StatusCode.SERVER_500000.value());
    }

    /**
     * 获取InvokeSmartContract bodyByte
     *
     * @param invokeSmartContractReq
     * @return
     */
    private byte[] getInvokeSmartContractBodyByte(InvokeSmartContractReq invokeSmartContractReq) {
        // 组装数据
        ContractCallArg contractCallArg = new ContractCallArg();
        contractCallArg.setGas(100000000);
        contractCallArg.setMethod(invokeSmartContractReq.getMethod());
        if (null != invokeSmartContractReq.getCaller()) {
            contractCallArg.setCaller(invokeSmartContractReq.getCaller().getBytes());
        }
        contractCallArg.setVersion(invokeSmartContractReq.getVersion());
        contractCallArg.setArgs(invokeSmartContractReq.getArgs());

        WVMContractTx wvmContractTx = new WVMContractTx();
        wvmContractTx.setArg(contractCallArg);
        wvmContractTx.setName(invokeSmartContractReq.getName());

        System.out.println(invokeSmartContractReq.getName());

        // 序列化
        byte[] wvmContractTxBytes = serialWvmContractTx(wvmContractTx);

        return wvmContractTxBytes;
    }

    /**
     * 获取数据字节码
     * contractReq.getName()
     *
     * @param name
     * @return
     */
    private byte[] getBodyByte(String name) {
        WVMContractTx wvmContractTx = new WVMContractTx();
        wvmContractTx.setName(name);

        return serialWvmContractTx(wvmContractTx);
    }

    /**
     * 获取Transaction
     *
     * @param ledger
     * @param version            版本
     * @param txType             交易类型
     * @param txSubType          交易子类型
     * @param wvmContractTxBytes 交易数据
     * @param pubKey             公钥
     * @param priKeyBytes        私钥
     * @return
     */
    private MyTransaction.Transaction getTransaction(String ledger, Integer version, Integer txType, Integer txSubType, byte[] wvmContractTxBytes, byte[] pubKey, byte[] priKeyBytes) {

        long currentTime = System.currentTimeMillis() / 1000;

        // 获取dataBytes
        byte[] bytes = getDataBytes(version, txType, txSubType, wvmContractTxBytes, pubKey, currentTime);

        byte[] hashData = sm3Hash(bytes);
        log.info("hashData 16进制形式{}", toHexString(hashData));

        // 获取数据的签名
        byte[] signBytes = new byte[0];
        try {
            signBytes = sm2Sign(priKeyBytes, hashData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MyTransaction.TransactionHeader transactionHeader = MyTransaction.TransactionHeader.newBuilder()
                .setVersion(version)
                .setType(txType)
                .setSubType(txSubType)
                .setTimestamp(currentTime)
                .setTransactionHash(ByteString.copyFrom(hashData))
                .build();

        MyTransaction.Transaction transaction = MyTransaction.Transaction.newBuilder()
                .setHeader(transactionHeader)
                .setData(ByteString.copyFrom(wvmContractTxBytes))
                .setPubkey(ByteString.copyFrom(pubKey))
                .setSign(ByteString.copyFrom(signBytes))
                .setResult(ByteString.copyFrom(new byte[0]))
                .setExtra(ByteString.copyFrom(new byte[0]))
                .build();

        return transaction;
    }

    /**
     * 获取Data字节数据
     *
     * @param version            版本
     * @param txType             交易类型
     * @param txSubType          交易子类型
     * @param wvmContractTxBytes 交易数据
     * @param pubKey             公钥
     * @param currentTime        当前时间
     * @return
     */
    private byte[] getDataBytes(Integer version, Integer txType, Integer txSubType, byte[] wvmContractTxBytes, byte[] pubKey, long currentTime) {
        ByteBuf buf = getByteBuf(version, txType, txSubType, currentTime);

        buf.writeBytes(int2Bytes(wvmContractTxBytes.length));
        buf.writeBytes(wvmContractTxBytes);

        buf.writeInt(0);

        buf.writeBytes(int2Bytes(pubKey.length));
        buf.writeBytes(pubKey);

        buf.writeInt(0);

        return convertBuf(buf);
    }

    /**
     * 组装buf
     *
     * @param version
     * @param txType
     * @param txSubType
     * @param currentTime
     * @return
     */
    private ByteBuf getByteBuf(Integer version, Integer txType, Integer txSubType, long currentTime) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(int2Bytes(version));
        buf.writeBytes(int2Bytes(txType));
        buf.writeBytes(int2Bytes(txSubType));
        buf.writeBytes(long2Bytes(currentTime));
        return buf;
    }

    /**
     * 序列化 wvmContractTx
     *
     * @param wvmContractTx
     * @return
     */
    private byte[] serialWvmContractTx(WVMContractTx wvmContractTx) {
        ByteBuf buf = Unpooled.buffer();

        ContractCallArg arg = wvmContractTx.getArg();
        if (null != arg) {
            if (null != arg.getMethod()) {
                buf.writeBytes(int2Bytes(arg.getMethod().getBytes().length));
                buf.writeBytes(arg.getMethod().getBytes());
            } else {
                buf.writeInt(0);
            }

            if (null != arg.getGas()) {
                buf.writeBytes(int2Bytes(arg.getGas()));
            } else {
                buf.writeInt(0);
            }

            if (null != arg.getCaller()) {
                buf.writeBytes(int2Bytes(arg.getCaller().length));
                buf.writeBytes(arg.getCaller());
            } else {
                buf.writeInt(0);
            }

            if (null != arg.getVersion()) {
                buf.writeBytes(int2Bytes(arg.getVersion().getBytes().length));
                buf.writeBytes(arg.getVersion().getBytes());
            } else {
                buf.writeInt(0);
            }
        }

        if (null != arg && arg.getArgs() != null && arg.getArgs().size() > 0) {
            // 将arg.getArgs() 转换成json串
            String jsonString = JSONObject.toJSONString(arg.getArgs());
            buf.writeBytes(int2Bytes(jsonString.length()));
            buf.writeBytes(jsonString.getBytes());
        } else {
            buf.writeInt(0);
        }

        if (null != wvmContractTx.getName()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getName().getBytes().length));
            buf.writeBytes(wvmContractTx.getName().getBytes());
        } else {
            buf.writeInt(0);
        }

        if (null != wvmContractTx.getOwner()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getOwner().length));
            buf.writeBytes(wvmContractTx.getOwner());
        } else {
            buf.writeInt(0);
        }

        if (null != wvmContractTx.getRandom()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getRandom().length));
            buf.writeBytes(wvmContractTx.getRandom());
        } else {
            buf.writeInt(0);
        }

        if (null != wvmContractTx.getSrc()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getSrc().length));
            buf.writeBytes(wvmContractTx.getSrc());
        } else {
            buf.writeInt(0);
        }

        if (null != wvmContractTx.getSign()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getSign().length));
            buf.writeBytes(wvmContractTx.getSign());
        } else {
            buf.writeInt(0);
        }

        return convertBuf(buf);
    }
}
