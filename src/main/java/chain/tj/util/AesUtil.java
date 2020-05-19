package chain.tj.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * @description AES加密解密类
 * create by hss on 2017/10/16
 */
public class AesUtil {
    private final int keySize = 32 * 8;
    private final int iterationCount = 2048;
    private final Cipher cipher;
    private static String status = null;
    private static String passphase = null;
    public static String salt = null;

    public AesUtil() {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            throw fail(e);
        }
    }

    public byte[] AESencrypt(byte[] salt, byte[] iv, String passphrase, byte[] plaintext) {
        try {
            SecretKey key = generateKey(salt, passphrase);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, plaintext);
            return encrypted;
        } catch (Exception e) {
            throw fail(e);
        }
    }

    public byte[] AESdecrypt(byte[] salt, byte[] iv, String passphrase, byte[] ciphertext) {
        try {
            SecretKey key = generateKey(salt, passphrase);
            System.out.println(key);
            byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, ciphertext);
            return decrypted;
        } catch (Exception e) {
            throw fail(e);
        }
    }

    private byte[] doFinal(int encryptMode, SecretKey key, byte[] iv, byte[] bytes) {
        try {
            cipher.init(encryptMode, key, new IvParameterSpec(iv));
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            throw fail(e);
        }
    }

    private SecretKey generateKey(byte[] salt, String passphrase) {
        try {

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt, iterationCount, keySize);
            SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return key;
        } catch (Exception e) {
            throw fail(e);
        }
    }

    public static byte[] random(int length) {
        byte[] salt = new byte[length];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private IllegalStateException fail(Exception e) {
        return new IllegalStateException(e);
    }

    public static void main(String[] args) throws Exception {
        // tyg
        byte[] IV = random(16);
        byte[] SALT = random(8);
        String PLAIN_TEXT = "你是谁？";
        //String PASSPHRASE = "<script>var tyg='zytang';var ganggang = 1; var zfq = '1234567890'</script>";
        String PASSPHRASE = "111";
        AesUtil util = new AesUtil();
        byte[] encrypt = util.AESencrypt(SALT, IV, PASSPHRASE, PLAIN_TEXT.getBytes("UTF-8"));
        byte[] decrypted = util.AESdecrypt(SALT, IV, PASSPHRASE, encrypt);
        System.out.println(new String(decrypted, "UTF-8"));
    }
}

