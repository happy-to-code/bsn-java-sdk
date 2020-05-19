
package chain.tj.util;


import com.tjfoc.gmsm.ParseEncryptionKey;
import com.tjfoc.gmsm.Util;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TjParseEncryptionKey {
    public static void main(String[] args) throws IOException{
        byte[] bytes = readKeyFromPem("D:\\work_project\\tj-java-sdk\\src\\main\\java\\chain\\tj\\file\\key.pem");
        System.out.println("bytes = " + bytes);
        for (byte aByte : bytes) {
            System.out.print(aByte);
        }
    }

    /**
     * 读取私钥文件中的 der 编码
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] readKeyFromPem(String filePath) throws IOException {
        ParseEncryptionKey parseEncryptionKey = new ParseEncryptionKey();
        return ParseEncryptionKey.readKeyFromPem(filePath);
//		Reader reader = new BufferedReader(new FileReader(filePath));
//		PemReader pemReader = new PemReader(reader);
//		PemObject obj = pemReader.readPemObject();
//		pemReader.close();
//		byte[] der = obj.getContent();
//		return der;
    }

    public byte[] readPrivateKey(byte[] prik) throws Exception {
        try {
            ParseEncryptionKey parseEncryptionKey = new ParseEncryptionKey();
            return parseEncryptionKey.readPrivateKey(prik);
        } catch (Exception e) {
            System.out.print("私钥读取失败");
            throw (e);
        }

//		// ASN1Object是DERObject的子类，ASN1Sequence是ASN1Object的子类
//		// 1、遇到要把解析的证书内容转换成InputStream
//		// 2、然后再传递给ASN1InputStream（FileInputStream的子类）
//		// 3、再利用ASN1InputStream里面的readObjct函数获得需要的ASN1Sequence
//		// ASN1InputStream bIn = new ASN1InputStream(new ByteArrayInputStream(prik));
//		// ASN1Primitive obj = bIn.readObject();
//		// System.out.println(ASN1Dump.dumpAsString(obj));
//		ASN1InputStream asn1Stream = new ASN1InputStream(new ByteArrayInputStream(prik));
//		ASN1Sequence asn1Sequence = (ASN1Sequence) asn1Stream.readObject();
//		asn1Stream.close();
//		InputStream stream = ((DEROctetString) asn1Sequence.getObjectAt(2)).getOctetStream();
//		ASN1InputStream privStream = new ASN1InputStream(stream);
//		ASN1Sequence privSequence = (ASN1Sequence) privStream.readObject();
//		privStream.close();
//		byte[] payload = ((DEROctetString) privSequence.getObjectAt(1)).getOctets();
//	 System.out.println("privateKey: " + Util.byteToHex(payload));
//		return payload;
    }

    public byte[] readPrivateKey(byte[] prik, String pwd) throws Exception {
        try {
            //打印加密过的证书内容格式
            // ASN1InputStream bIn = new ASN1InputStream(new ByteArrayInputStream(prik));
            // ASN1Primitive obj = bIn.readObject();
            // System.out.println(ASN1Dump.dumpAsString(obj));

            ASN1InputStream asn1Stream = new ASN1InputStream(new ByteArrayInputStream(prik));
            ASN1Sequence asn1Sequence = (ASN1Sequence) asn1Stream.readObject();
            asn1Stream.close();

            // Sequence
            // Sequence                                                           a
            // 	ObjectIdentifier(1.2.840.113549.1.5.13)     id-PBES2(PBES2)
            // 	Sequence                                                          b
            // 		Sequence                                                      c
            // 			ObjectIdentifier(1.2.840.113549.1.5.12)   id-PBKDF2
            // 			Sequence                                                  d
            // 				DER Octet String[8]     salt
            // 				Integer(2048)           iter
            // 				Sequence
            // 					ObjectIdentifier(1.2.840.113549.2.7)  oidKEYSHA1
            // 					NULL
            // 		Sequence                                                      e
            // 			ObjectIdentifier(2.16.840.1.101.3.4.1.42)   oidAES256CBC
            // 			DER Octet String[16]    IV
            // DER Octet String[160]     encryptedKey
            ASN1Sequence a = ((ASN1Sequence) asn1Sequence.getObjectAt(0));
            ASN1Sequence b = ((ASN1Sequence) a.getObjectAt(1));
            ASN1Sequence c = ((ASN1Sequence) b.getObjectAt(0));
            ASN1Sequence d = ((ASN1Sequence) c.getObjectAt(1));
            ASN1Sequence e = ((ASN1Sequence) b.getObjectAt(1));
            ASN1Integer iter = (ASN1Integer) d.getObjectAt(1);
            byte[] IV = ((DEROctetString) e.getObjectAt(1)).getOctets();
            byte[] salt = ((DEROctetString) d.getObjectAt(0)).getOctets();
            byte[] encryptedKey = ((DEROctetString) asn1Sequence.getObjectAt(1)).getOctets();
            AesUtil aesUtil = new AesUtil();
            byte[] decryptedkey = aesUtil.AESdecrypt(salt, IV, pwd, encryptedKey);
            byte[] payload = readPrivateKey(decryptedkey);
            return payload;
        } catch (Exception e) {
            System.out.print("私钥与密钥不匹配");
            throw (e);
        }

    }

    public byte[] pbkdf(String password, byte[] salt, byte[] iter, int KeyLen) {
        byte[] payload = {0x22};
        return payload;
    }

    public byte[] readPublicKeyFromCert(byte[] pub) throws Exception {
        // ASN1InputStream asn1Stream = new ASN1InputStream(der);
        ASN1InputStream asn1Stream = new ASN1InputStream(new ByteArrayInputStream(pub));
        ASN1Sequence asn1Sequence = (ASN1Sequence) asn1Stream.readObject();
        asn1Stream.close();
        X509CertificateStructure cert = new X509CertificateStructure(asn1Sequence);
        SubjectPublicKeyInfo subjectPublicKeyInfo = cert.getSubjectPublicKeyInfo();
        // sm2算法公钥数据格式的ASN.1定义为 SM2PublicKey :: = BIT STRING
        DERBitString publicKeyData = subjectPublicKeyInfo.getPublicKeyData();
        byte[] publicKey = publicKeyData.getEncoded();
        byte[] encodedPublicKey = publicKey;
        System.out.println("################################################################");
        String s = new String(Hex.encode(encodedPublicKey));
        System.out.println("full publicKey:" + s);
        byte[] eP = new byte[65];
        System.arraycopy(encodedPublicKey, 3, eP, 0, eP.length);
        return eP;
    }

    public byte[] readPublicKey(byte[] pubk) throws Exception {
        // ASN1Object是DERObject的子类，ASN1Sequence是ASN1Object的子类
        // 1、遇到要把解析的证书内容转换成InputStream
        // 2、然后再传递给ASN1InputStream（FileInputStream的子类）
        // 3、再利用ASN1InputStream里面的readObjct函数获得需要的ASN1Sequence
        ASN1InputStream asn1Stream = new ASN1InputStream(new ByteArrayInputStream(pubk));
        ASN1Sequence asn1Sequence = (ASN1Sequence) asn1Stream.readObject();
        asn1Stream.close();
        System.out.println(asn1Sequence.getObjectAt(0).getClass());
        System.out.println(asn1Sequence.getObjectAt(1).getClass());
        // priv

        // ASN1InputStream privStream = new ASN1InputStream(stream);
        // ASN1Sequence privSequence = (ASN1Sequence) privStream.readObject();
        // privStream.close();
        // System.out.println(privSequence.getObjectAt(0).getClass());
        System.out.println("------------------------pem公钥-------------------------");
        // byte[] payload = ((DEROctetString) privSequence.getObjectAt(1)).getOctets();
        byte[] payload = ((DERBitString) asn1Sequence.getObjectAt(1)).getBytes();

        System.out.println("original Pub :" + Util.byteToHex(payload));
        // 同济
        /*
         * byte[] bufferx=new byte[1]; int i; i=payload[2]&0xFF; System.out.format(
         * "asn1 auto fill:"+"%x", i ); System.out.println("\n"); if(0x80<=i&&i<=0xff) {
         * byte[] pbhex=new byte[payload.length-1]; pbhex[0]=0x4;
         * System.arraycopy(payload, 2, pbhex, 1, pbhex.length-1);
         * System.out.println("privateKey: " + Util.byteToHex(pbhex)); return pbhex; }
         * else { System.out.println("privateKey: " + Util.byteToHex(payload)); return
         * payload; }
         */

        return payload;

    }
}
