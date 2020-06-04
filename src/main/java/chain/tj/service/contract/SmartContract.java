package chain.tj.service.contract;

import chain.tj.common.StatusCode;
import chain.tj.common.exception.ServiceException;
import chain.tj.common.response.RestResponse;
import chain.tj.model.ienum.FateSubType;
import chain.tj.model.ienum.TransactionType;
import chain.tj.model.ienum.Version;
import chain.tj.model.pojo.dto.ContractReq;
import chain.tj.model.pojo.dto.InvokeSmartContractReq;
import chain.tj.model.pojo.dto.QuerySmartContractReq;
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

import java.util.ArrayList;
import java.util.List;
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
        // 验证数据
        checkData(contractReq);

        // 获取密钥对和签名
        Map<String, byte[]> keyPair = getKeyPairAndSign(contractReq.getPriKeyPath());
        if (keyPair.isEmpty()) {
            return RestResponse.failure("获取秘钥失败！", StatusCode.CLIENT_410021.value());
        }
        // 获取合约文件
        String contractFile = readFile(contractReq.getFilePath());
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(contractFile.getBytes());
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
        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(contractReq.getAddr(), contractReq.getPort());
        // 发送请求
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);

        if (peerResponse.getOk()) {
            // 封装返回体
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
        // 验证数据
        checkBasicParam(contractReq.getPriKeyPath(), contractReq.getAddr(), contractReq.getPort());
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
        Map<String, byte[]> keyPairAndSign = getKeyPairAndSign(contractReq.getPriKeyPath());
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
        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(contractReq.getAddr(), contractReq.getPort());
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
        // 校验合约名称
        String name = invokeSmartContractReq.getName();
        if (StringUtils.isBlank(name)) {
            return RestResponse.failure("合约名称不可以为空", StatusCode.CLIENT_410001.value());
        }
        // 校验数据
        checkInvokeSmartContractParam(invokeSmartContractReq);

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
        byte[] bodyByte = getSmartContractBodyByte(invokeSmartContractReq.getName(), invokeSmartContractReq.getMethod(),
                invokeSmartContractReq.getVersion(), invokeSmartContractReq.getCaller(), invokeSmartContractReq.getArgs());

        // 获取请求体
        MyTransaction.Transaction transaction = getTransaction(invokeSmartContractReq.getLedger(), Version.VersionTwo.getValue(),
                TransactionType.SCWVM.getValue(), FateSubType.WVMSCInvoke.getValue(), bodyByte, keyPairAndSign.get("pubKey"), keyPairAndSign.get("priKey"));

        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(keyPairAndSign.get("pubKey")))
                .setPayload(transaction.toByteString())
                .build();
        // 获取stub
        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(invokeSmartContractReq.getAddr(), invokeSmartContractReq.getPort());
        // 发送请求
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);
        if (peerResponse.getOk()) {
            return RestResponse.success();
        }

        return RestResponse.failure("调用合约失败！", StatusCode.SERVER_500000.value());
    }


    /**
     * 查询合约信息
     *
     * @param querySmartContractReq
     * @return
     */
    @Override
    public RestResponse querySmartContract(QuerySmartContractReq querySmartContractReq) {
        // 获取密钥对和签名
        Map<String, byte[]> keyPairAndSign = getKeyPairAndSign("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        if (keyPairAndSign.isEmpty()) {
            return RestResponse.failure("获取秘钥失败", StatusCode.CLIENT_410021.value());
        }
        // 校验数据
        ckeckQuerySmartContractParam(querySmartContractReq);


        // 获取QuerySmartContract bodyByte
        byte[] bodyByte = getSmartContractBodyByte(querySmartContractReq.getName(), querySmartContractReq.getMethod(),
                querySmartContractReq.getVersion(), querySmartContractReq.getCaller(), querySmartContractReq.getArgs());

        // 获取请求体
        MyTransaction.Transaction transaction = getTransaction(querySmartContractReq.getLedger(), Version.VersionTwo.getValue(),
                TransactionType.SCWVM.getValue(), FateSubType.WVMSCInvoke.getValue(), bodyByte, keyPairAndSign.get("pubKey"), keyPairAndSign.get("priKey"));
        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(keyPairAndSign.get("pubKey")))
                .setPayload(transaction.toByteString())
                .build();
        // 获取stub
        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort(querySmartContractReq.getAddr(), querySmartContractReq.getPort());
        // 发送请求
        MyPeer.PeerResponse peerResponse = stub.newQueryTransaction(request);

        if (peerResponse.getOk()) {
            return RestResponse.success().setData(peerResponse.getPayload().toStringUtf8());
        }

        return RestResponse.failure("查询合约信息失败！", StatusCode.SERVER_500000.value());
    }


    /**
     * 校验查询合约信息参数
     *
     * @param querySmartContractReq
     */
    private void ckeckQuerySmartContractParam(QuerySmartContractReq querySmartContractReq) {
        checkBasicParam(querySmartContractReq.getPriKeyPath(), querySmartContractReq.getAddr(), querySmartContractReq.getPort());

        if (StringUtils.isBlank(querySmartContractReq.getName())) {
            throw new ServiceException("参数：name不可以为空");
        }

        if (StringUtils.isBlank(querySmartContractReq.getCategory())) {
            throw new ServiceException("参数：category不可以为空!");
        }

        if (StringUtils.isBlank(querySmartContractReq.getVersion())) {
            throw new ServiceException("参数：version不可以为空!");
        }

        if (StringUtils.isBlank(querySmartContractReq.getMethod())) {
            throw new ServiceException("参数：method不可以为空");
        }

        if (StringUtils.isBlank(querySmartContractReq.getCaller())) {
            throw new ServiceException("参数：caller不可以为空");
        }

        if (null == querySmartContractReq.getArgs() || querySmartContractReq.getArgs().size() <= 0) {
            throw new ServiceException("参数：args不可以为空!");
        }
    }

    /**
     * 获取SmartContract bodyByte
     *
     * @param name
     * @param method
     * @param version
     * @param caller
     * @param args
     * @return
     */
    private byte[] getSmartContractBodyByte(String name, String method, String version, String caller, List<Object> args) {
        // 组装数据
        ContractCallArg contractCallArg = new ContractCallArg();
        contractCallArg.setGas(100000000);
        contractCallArg.setMethod(method);
        if (StringUtils.isNoneBlank(caller)) {
            contractCallArg.setCaller(caller.getBytes());
        }
        contractCallArg.setVersion(version);
        contractCallArg.setArgs(args);

        WVMContractTx wvmContractTx = new WVMContractTx();
        wvmContractTx.setArg(contractCallArg);
        wvmContractTx.setName(name);

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
     * @param version     版本
     * @param txType      交易类型
     * @param txSubType   交易子类型
     * @param bodyBytes   交易数据
     * @param pubKey      公钥
     * @param priKeyBytes 私钥
     * @return
     */
    private MyTransaction.Transaction getTransaction(String ledger, Integer version, Integer txType, Integer txSubType, byte[] bodyBytes, byte[] pubKey, byte[] priKeyBytes) {

        long currentTime = System.currentTimeMillis() / 1000;

        // 获取dataBytes
        byte[] bytes = getDataBytes(version, txType, txSubType, bodyBytes, pubKey, currentTime);

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
                .setData(ByteString.copyFrom(bodyBytes))
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


    /**
     * 验证数据
     *
     * @param contractReq
     */
    private void checkData(ContractReq contractReq) {
        if (StringUtils.isBlank(contractReq.getFilePath())) {
            throw new ServiceException("合约文件路径不可以为空！");
        }

        checkBasicParam(contractReq.getPriKeyPath(), contractReq.getAddr(), contractReq.getPort());
    }

    /**
     * 验证基础信息
     *
     * @param priKeyPath
     * @param addr
     * @param port
     */
    private void checkBasicParam(String priKeyPath, String addr, Integer port) {
        if (StringUtils.isBlank(priKeyPath)) {
            throw new ServiceException("私钥文件路径不可以为空！");
        }

        if (StringUtils.isBlank(addr)) {
            throw new ServiceException("ip地址不可以为空！");
        }

        if (null == port || port <= 0) {
            throw new ServiceException("端口不合法！");
        }
    }

    /**
     * 校验调用合约参数
     *
     * @param invokeSmartContractReq
     */
    private void checkInvokeSmartContractParam(InvokeSmartContractReq invokeSmartContractReq) {
        checkBasicParam(invokeSmartContractReq.getPriKeyPath(), invokeSmartContractReq.getAddr(), invokeSmartContractReq.getPort());

        if (StringUtils.isBlank(invokeSmartContractReq.getCategory())) {
            throw new ServiceException("参数：category不可以为空");
        }

        if (StringUtils.isBlank(invokeSmartContractReq.getVersion())) {
            throw new ServiceException("参数：version不可以为空");
        }

        if (StringUtils.isBlank(invokeSmartContractReq.getMethod())) {
            throw new ServiceException("参数：method不可以为空");
        }

        if (StringUtils.isBlank(invokeSmartContractReq.getCaller())) {
            throw new ServiceException("参数：caller不可以为空");
        }

        if (null == invokeSmartContractReq.getArgs() || invokeSmartContractReq.getArgs().size() <= 0) {
            throw new ServiceException("参数：args不可以为空");
        }

    }
}
