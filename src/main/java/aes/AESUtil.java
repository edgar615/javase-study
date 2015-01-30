package aes;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * AES 算法 对称加密，密码学中的高级加密标准 2005年成为有效标准
 */
public class AESUtil {

    static final String KEY_ALGORITHM = "AES";
    static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
    /*
     *
     */
    static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    /*
     * AES/CBC/NoPadding 要求 密钥必须是16位的；Initialization vector (IV) 必须是16位
     * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
     * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16
     * bytes
     *
     * 由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
     *
     * 可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
     * 其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
     * 除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
     */
    static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";

    static final String UTF8 = "UTF-8";

    /**
     * 使用AES 算法 加密，默认模式 AES/ECB
     */
    public static byte[] aesEncrypt(String key, String data) throws Exception {
        Cipher cipher = encryptCipher(key);
        return cipher.doFinal(data.getBytes(UTF8)); // 按单部分操作加密或解密数据，或者结束一个多部分操作。
    }

    /**
     * 使用AES 算法 解密，默认模式 AES/ECB
     */
    public static String aesDecrypt(String key, byte[] encrypt) throws Exception {
        Cipher cipher = decryptCipher(key);
        byte[] decrypt = cipher.doFinal(encrypt);
        return new String(decrypt, UTF8);
    }

    private static Cipher encryptCipher(String key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, UnsupportedEncodingException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        Key secretKey = new SecretKeySpec(
                key.getBytes(UTF8), KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 使用加密模式初始化 密钥
        return cipher;
    }

    private static Cipher decryptCipher(String key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, UnsupportedEncodingException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        Key secretKey = new SecretKeySpec(
                key.getBytes(), KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);// 使用加密模式初始化 密钥
        return cipher;
    }

}