package crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class UtilDemo{
public static void main(String[] args) throws Exception {
    String keystorePath = "h:/keystore.jceks";
    String keystorePass = "secret";
//    String certPath = "C:/Users/CeoiHong/x003.cer";
    String certPass = "123456";
    String alias = "ES256";


    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
    KeyPair keyPair = keyPairGenerator.genKeyPair();


    System.out.println("==========|加密|==========");
    String source = "徐航 www.xuhang.us";
    String source2 = "wayne_xuhang@163.com";

    System.out.println("原始数据为：" + source);
    KeyStore keyStore = loadKeyStore(keystorePath, keystorePass);
    //获得私钥
    PrivateKey privateKey = getPrivateKey(keyStore, alias, certPass);
    //PrivateKey privateKey = keyPair.getPrivate();
    //使用私钥加密
    byte[] encrypted = asymmetricEncrypt("RSA/ECB/PKCS1Padding", source.getBytes("UTF-8"), privateKey);
    //加密后的数据
    System.out.println("加密算法（私钥）：" + privateKey.getAlgorithm());
    System.out.println("私钥：" + privateKey.getEncoded());
    System.out.println("加密后的数据（十六进制）：" + byte2Hex(encrypted));

    System.out.println("==========|解密|==========");
    //获得公钥
    PublicKey publicKey = getPublicKey(keyStore.getCertificate(alias));
    //PublicKey publicKey = keyPair.getPublic();
    //使用公钥解密
    byte[] decrypted = asymmetricDecrypt("RSA/ECB/PKCS1Padding", encrypted, publicKey);
    //解密后的数据
    System.out.println("解密算法（公钥）：" + publicKey.getAlgorithm());
    System.out.println("公钥：" + publicKey.getEncoded());
    System.out.println("解密后数据（字节）:" + byte2Hex(decrypted));
    System.out.println("解密后的数据（明文）:" + new String(decrypted, "UTF-8"));


    System.out.println("==========|签名|==========");
    //如果私钥使用的rsa算法，这里签名也只能使用rsa算法
    //获得签名对象Signature和使用的私钥算法必须一致，DSA不能配合md5使用

    String alg = ((X509Certificate)getCertFromKStore(alias, keyStore)).getSigAlgName();
    Signature signature = Signature.getInstance("SHA1WithDSA");
    signature.initSign(keyPair.getPrivate());
    signature.update(source.getBytes("UTF-8"));
    signature.update(source2.getBytes("UTF-8"),0,source2.getBytes("UTF-8").length);
    byte[] sign = signature.sign();
    System.out.println("签名plain：" + source);
    System.out.println("签名后数据：" + byte2Hex(sign));
    System.out.println("签名算法/私钥算法：" + signature.getAlgorithm() + "/" + keyPair.getPrivate().getAlgorithm());

    System.out.println("==========|验签|==========");
    Signature vSignature = Signature.getInstance("SHA1WithDSA");
    vSignature.initVerify(keyPair.getPublic());
    vSignature.update(source.getBytes("UTF-8"));
    vSignature.update(source2.getBytes("UTF-8"));
    boolean b = vSignature.verify(hex2Byte(byte2Hex(sign)));
    System.out.println("验签算法/公钥算法：" + vSignature.getAlgorithm() + "/" + keyPair.getPublic().getAlgorithm());
    System.out.println("验签结果：" + b);

    System.out.println("==========|手动签名|==========");
    System.out.println("签名plain：" + source);
    byte[] digestText = digest(source);
    System.out.println("信息摘要：" + byte2Hex(digestText));
    byte[] sign1 = asymmetricEncrypt("RSA", digestText, privateKey);
    System.out.println("数字签名：" + byte2Hex(sign1));


    System.out.println("==========|手动验签|==========");
    byte[] digest1 = asymmetricDecrypt("RSA", sign1, publicKey);
    System.out.println("解密签名得到的摘要：" + byte2Hex(digest1));
    System.out.println("手动验签结果" + byte2Hex(digest1).equals(byte2Hex(digestText)));




}

//加载密钥库keystore
public static KeyStore loadKeyStore(String keystorePath, String keystorePass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
    //提供密钥库类型
    KeyStore keyStore = KeyStore.getInstance("jceks");
    //读取keystore文件的输入流
    InputStream in = new FileInputStream(keystorePath);
    keyStore.load(in, keystorePass.toCharArray());

    return keyStore;

}

//直接从文件加载证书certificate
public static Certificate loadCertificate(String certPath, String certPass) throws CertificateException, FileNotFoundException{
    //证书格式为X509
    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
    //读取证书文件的输入流
    InputStream in = new FileInputStream(certPath);
    Certificate certificate = certificateFactory.generateCertificate(in);
    return certificate;
}

//从密钥库根据别名alias获得证书certificate
public static Certificate getCertFromKStore(String alias, KeyStore keyStore) throws KeyStoreException{
    return keyStore.getCertificate(alias);
}

//对称加密
public static byte[] symmetricEncrypt(String transformation, byte[] plainText, Key key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
    Cipher cipher = Cipher.getInstance(transformation);

    cipher.init(Cipher.ENCRYPT_MODE, key);

    cipher.update(plainText);

    return cipher.doFinal();
}

//对称解密
public static byte[] symmetricDecrypt(String transformation, byte[] cipherText, Key key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
    Cipher cipher = Cipher.getInstance(transformation);

    cipher.init(Cipher.DECRYPT_MODE, key);

    cipher.update(cipherText);

    return cipher.doFinal();
}

//非对称加密
public static byte[] asymmetricEncrypt(String transformation, byte[] plainText, PrivateKey key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
    Cipher cipher = Cipher.getInstance(transformation);

    cipher.init(Cipher.ENCRYPT_MODE, key);

    cipher.update(plainText);

    return cipher.doFinal();
}

//非对称解密
public static byte[] asymmetricDecrypt(String transformation, byte[] cipherText, PublicKey key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
    Cipher cipher = Cipher.getInstance(transformation);

    cipher.init(Cipher.DECRYPT_MODE, key);

    cipher.update(cipherText);

    return cipher.doFinal();
}



//获取公钥PublicKey
public static PublicKey getPublicKey(Certificate certificate){
    return certificate.getPublicKey();
}

//获取私钥PrivateKey
public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String certpass) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException{
    return (PrivateKey)keyStore.getKey(alias, certpass.toCharArray());
}

//对称
//TODO

//字节数组转十六进制
public static String byte2Hex(byte[] b){
    StringBuilder sb = new StringBuilder();
    for(int i=0;i<b.length;i++){
        String hex = Integer.toHexString(0x00ff & b[i]);
        if(hex.length()<2){
            sb.append('0');
        }
        sb.append(hex);
    }
    return sb.toString();
}

//十六进制转字节数组
public static byte[] hex2Byte(String hex){
    byte[] bytes = new byte[hex.length()/2];
    for(int i=0;i*2<hex.length();i++){
        bytes[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
    }
    return bytes;
}

public static void printByte(byte[] bytes){
    for(int i=0;i<bytes.length;i++){
        if(i>0){
            System.out.print(",");
        }
        System.out.print(bytes[i]);
    }
    System.out.println();
}

//信息摘要
public static byte[] digest(String source) throws NoSuchAlgorithmException{
    MessageDigest md = MessageDigest.getInstance("SHA1");

    md.update(source.getBytes());

    return md.digest();
}
}