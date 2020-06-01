package chain.tj.service.contract;

import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import chain.tj.model.pojo.dto.ContractReq;
import chain.tj.model.pojo.dto.contract.ContractCallArg;
import chain.tj.model.pojo.dto.contract.WVMContractTx;
import chain.tj.model.proto.MyPeer;
import chain.tj.model.proto.MyTransaction;
import chain.tj.model.proto.PeerGrpc;
import chain.tj.service.Contract;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static chain.tj.util.GmUtils.*;
import static chain.tj.util.PeerUtil.*;
import static chain.tj.util.PeerUtil.int2Bytes;
import static chain.tj.util.TjParseEncryptionKey.readKeyFromPem;
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
        String fileBase64 = "//合约示例\n" +
                "contract Transfer {\n" +
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

        // 获取私钥
        byte[] priKeyBytes = new byte[0];
        try {
            priKeyBytes = readKeyFromPem("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("私钥16进制形式{}", toHexString(priKeyBytes));

        // 用私钥获取公钥
        byte[] pubKey = priToPubKey(priKeyBytes);
        log.info("公钥16进制形式{}", toHexString(pubKey));


        byte[] signBytes = new byte[0];
        try {
            signBytes = sm2Sign(priKeyBytes, "ownersign".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("签名16进制形式{}", toHexString(signBytes));


        ContractCallArg contractCallArg = new ContractCallArg();
        contractCallArg.setVersion("2");
        contractCallArg.setGas(10000000);

        WVMContractTx wvmContractTx = new WVMContractTx();
        wvmContractTx.setArg(contractCallArg);
        wvmContractTx.setOwner(pubKey);
        wvmContractTx.setRandom(randomStr.getBytes());
        wvmContractTx.setSrc(content);
        wvmContractTx.setSign(signBytes);

        // 序列化 wvmContractTx
        byte[] wvmContractTxBytes = serialWvmContractTx(wvmContractTx);

        return invokeTransaction(contractReq.getLedger(), wvmContractTxBytes, pubKey, priKeyBytes);


    }

    /**
     * 销毁合约
     *
     * @param contractReq
     * @return
     */
    @Override
    public RestResponse destorySmartContract(ContractReq contractReq) {


        return null;
    }

    private RestResponse invokeTransaction(String ledger, byte[] wvmContractTxBytes, byte[] pubKey, byte[] priKeyBytes) {

        long currentTime = System.currentTimeMillis() / 1000;

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(int2Bytes(2));
        buf.writeBytes(int2Bytes(3));
        buf.writeBytes(int2Bytes(40));
        buf.writeBytes(long2Bytes(currentTime));

        buf.writeBytes(int2Bytes(wvmContractTxBytes.length));
        buf.writeBytes(wvmContractTxBytes);

        buf.writeInt(0);

        buf.writeBytes(int2Bytes(pubKey.length));
        buf.writeBytes(pubKey);

        buf.writeInt(0);

        byte[] bytes = convertBuf(buf);

        byte[] hashData = sm3Hash(bytes);
        log.info("hashData 16进制形式{}", toHexString(hashData));

        // 获取签名
        byte[] signBytes = new byte[0];
        try {
            signBytes = sm2Sign(priKeyBytes, hashData);
        } catch (Exception e) {
            e.printStackTrace();
        }


        MyTransaction.TransactionHeader transactionHeader = MyTransaction.TransactionHeader.newBuilder()
                .setVersion(2)
                .setType(3)
                .setSubType(40)
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

        MyPeer.PeerRequest request = MyPeer.PeerRequest.newBuilder()
                .setPubkey(ByteString.copyFrom(pubKey))
                .setPayload(transaction.toByteString())
                .build();

        PeerGrpc.PeerBlockingStub stub = getStubByIpAndPort("10.1.3.150", 9008);
        MyPeer.PeerResponse peerResponse = stub.newTransaction(request);
        System.out.println("peerResponse = " + peerResponse);

        if (peerResponse.getOk()) {
            return RestResponse.success();
        }

        return RestResponse.failure("创建合约失败！", StatusCode.SERVER_500000.value());
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

        if (arg.getArgs() != null && arg.getArgs().size() > 0) {
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
