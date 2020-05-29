package chain.tj.service.contract;

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
        String fileBase64 = "Ly/lkIjnuqbnpLrkvosKY29udHJhY3QgVHJhbnNmZXIgewoJLy/liJ3lp4vljJbkuIDkuKrotKbmiLcKCXB1YmxpYyBzdHJpbmcgaW5pdCgpewoJCWRiX3NldCgiYm9iIiwxMDAwMCkKICAgICAgICBkYl9zZXQoImppbSIsMTAwMDApCiAgICAgICAgcmV0dXJuICJzdWNjZXNzIgoJfQoJCgkvL+i9rOi0puaTjeS9nAoJcHVibGljIHN0cmluZyB0cmFuc2ZlcihzdHJpbmcgZnJvbSwgc3RyaW5nIHRvLCBpbnQgYW1vdW50KSB7CgkJaW50IGJhbEEgPSBkYl9nZXQ8aW50Pihmcm9tKQoJCWludCBiYWxCID0gZGJfZ2V0PGludD4odG8pCgkJYmFsQSA9IGJhbEEtYW1vdW50CgkJaWYgKGJhbEE+MCl7CgkJCWJhbEIgPSBiYWxCK2Ftb3VudAoJCQlkYl9zZXQoZnJvbSwgYmFsQSkKCQkJZGJfc2V0KHRvLCBiYWxCKQoJCX1lbHNlewogICAgICAgICAgICByZXR1cm4gImluc3VmZmljaWVudCBiYWxhbmNlIgogICAgICAgIH0KCQlyZXR1cm4gInN1Y2Nlc3MiCgl9CgoJLy/mn6Xor6LotKbmiLfkvZnpop0KCXB1YmxpYyBpbnQgZ2V0QmFsYW5jZShzdHJpbmcgYWNjb3VudCl7CgkJcmV0dXJuIGRiX2dldDxpbnQ+KGFjY291bnQpCgl9Cn0=";
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

        // 用私钥获取公钥
        byte[] pubKey = priToPubKey(priKeyBytes);

        byte[] b = new byte[1];
        b[0] = 1;
        byte[] signBytes = new byte[0];
        try {
            signBytes = sm2Sign(priKeyBytes, b);
        } catch (Exception e) {
            e.printStackTrace();
        }


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

        invokeTransaction(contractReq.getLedger(), wvmContractTxBytes, pubKey, priKeyBytes);


        return null;
    }

    private void invokeTransaction(String ledger, byte[] wvmContractTxBytes, byte[] pubKey, byte[] priKeyBytes) {

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
            }

            if (null != arg.getGas()) {
                buf.writeBytes(int2Bytes(arg.getGas()));
            }

            if (null != arg.getCaller()) {
                buf.writeBytes(int2Bytes(arg.getCaller().length));
                buf.writeBytes(arg.getCaller());
            }

            if (null != arg.getVersion()) {
                buf.writeBytes(int2Bytes(arg.getVersion().getBytes().length));
                buf.writeBytes(arg.getVersion().getBytes());
            }
        }
        // 将arg.getArgs() 转换成json串
        String jsonString = JSONObject.toJSONString(arg.getArgs());
        buf.writeBytes(int2Bytes(jsonString.length()));
        buf.writeBytes(jsonString.getBytes());

        if (null != wvmContractTx.getName()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getName().getBytes().length));
            buf.writeBytes(wvmContractTx.getName().getBytes());
        }

        if (null != wvmContractTx.getOwner()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getOwner().length));
            buf.writeBytes(wvmContractTx.getOwner());
        }

        if (null != wvmContractTx.getRandom()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getRandom().length));
            buf.writeBytes(wvmContractTx.getRandom());
        }

        if (null != wvmContractTx.getSrc()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getSrc().length));
            buf.writeBytes(wvmContractTx.getSrc());
        }

        if (null != wvmContractTx.getSign()) {
            buf.writeBytes(int2Bytes(wvmContractTx.getSign().length));
            buf.writeBytes(wvmContractTx.getSign());
        }

        return convertBuf(buf);
    }
}
