package chain.tj.util;

import com.tjfoc.gmsm.*;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

import static chain.tj.util.PeerUtil.toHexString;
import static chain.tj.util.TjParseEncryptionKey.readKeyFromPem;


public class GmUtils {
    public static byte[] priToPubKey(byte[] privateKey) {
        SM2 factory = SM2.Instance();
        BigInteger userD = new BigInteger(1, privateKey);
        ECPoint userKey = factory.ecc_point_g.multiply(userD);
        byte[] pubx = userKey.getXCoord().getEncoded();
        byte[] puby = userKey.getEncoded(false);
        byte[] pubkbytes = userKey.getEncoded(false);
        byte[] formatedPubKey;
        // 公钥里第一byte加了标志位04。需要去除。
        if (pubkbytes.length == 65) {
            // 添加一字节标识，用于ECPoint解析
            formatedPubKey = new byte[64];
            System.arraycopy(pubkbytes, 1, formatedPubKey, 0, pubkbytes.length - 1);
        } else {
            formatedPubKey = pubkbytes;
        }
        return formatedPubKey;
    }

    public static byte[] sm2Sign(byte[] privateKey, byte[] sm3bytes) throws Exception {
        SM2SignVO sign = SM2Utils.Sign(privateKey, sm3bytes);
        byte[] sig = Util.hexStringToBytes(sign.getSm2_sign());
        return sig;
    }

    public static boolean sm2Verify(byte[] publicKey, byte[] sourceData, byte[] sign) {
        SM2SignVO verify = SM2Utils.verifySign(publicKey, sourceData, sign);
        return verify.isVerify();
    }


    /**
     * 国密3
     *
     * @param sourceData
     * @return
     */
    public static byte[] sm3Hash(byte[] sourceData) {
        SM3CalculateHash sm3CalculateHash = new SM3CalculateHash();
        byte[] sm3bytes = sm3CalculateHash.calculateHash(sourceData);
        return sm3bytes;
    }

    public static byte[] sm2Encrypt(byte[] publicKey, byte[] data) {
        String encrypted = SM2Utils.encrypt(publicKey, data);
        byte[] ciphertext = Util.hexStringToBytes(encrypted);
        return ciphertext;
    }

    public static byte[] sm2Decrypt(byte[] privateKey, byte[] ciphertext) throws Exception {
        byte[] plainText = SM2Utils.decrypt(privateKey, ciphertext);
        return plainText;
    }

    public static byte[] sm4Decrypt(String kText, byte[] cipherBytes) {
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = kText;
        sm4.hexString = true;
        sm4.iv = "00000000000000000000000000000000";
        String cipherText = Util.byteToString(cipherBytes);
        String decryptData = sm4.decryptData_CBC(cipherText);
        byte[] decryptBytes = (decryptData).getBytes();
        return decryptBytes;
    }

    public static byte[] sm4Encrypt(String message, String kText) {
        // iv长度应为16字节
        final String ivText = "00000000000000000000000000000000";
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = kText;
        sm4.hexString = true;
        sm4.iv = ivText;
        String ciphertext = sm4.encryptData_CBC(message);
        byte[] cipherBytes = (ciphertext).getBytes();
        System.out.println(ciphertext);
        return cipherBytes;
    }

    public static void main(String[] args) throws Exception {
        byte[] bytes = new byte[2];
        bytes[0] = 1;
        bytes[1] = 2;
        // byte[] bytes1 = sm3Hash(bytes);
        // System.out.println(bytes1);
        String s = toHexString(bytes);
        System.out.println("s = " + s);

        byte[] prikey = readKeyFromPem("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");

        byte[] pubKey = priToPubKey(prikey);
        System.out.println("pubKey = " + pubKey);


        // public static byte[] sm2Encrypt(byte[] publicKey, byte[] data)
        byte[] enc = sm2Encrypt(pubKey, bytes);
        String encryptStr = toHexString(enc);
        System.out.println("encryptStr = " + encryptStr);

        // sm2Decrypt(byte[] privateKey, byte[] ciphertext)
        byte[] bytes1 = sm2Decrypt(prikey, enc);
        String decryptStr = toHexString(bytes1);
        System.out.println("decryptStr = " + decryptStr);
    }
}