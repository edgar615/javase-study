package crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class JKSTesting {
  public static PublicKey getPublicKey(String keyStoreFile,
                                       String storeFilePass, String keyAlias) {

    // 读取密钥是所要用到的工具类
    KeyStore ks;

    // 公钥类所对应的类
    PublicKey pubkey = null;
    try {

      // 得到实例对象
      ks = KeyStore.getInstance("jceks");
      FileInputStream fin;
      try {

        // 读取JKS文件
        fin = new FileInputStream(keyStoreFile);
        try {
          // 读取公钥
          ks.load(fin, storeFilePass.toCharArray());
          java.security.cert.Certificate cert = ks
                  .getCertificate(keyAlias);
          pubkey = cert.getPublicKey();
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        } catch (CertificateException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    } catch (KeyStoreException e) {
      e.printStackTrace();
    }
    return pubkey;
  }

  /**
   * 得到私钥
   *
   * @param keyStoreFile  私钥文件
   * @param storeFilePass 私钥文件的密码
   * @param keyAlias      别名
   * @param keyAliasPass  密码
   * @return
   */
  public static PrivateKey getPrivateKey(String keyStoreFile,
                                         String storeFilePass, String keyAlias,
                                         String keyAliasPass) {
    KeyStore ks;
    PrivateKey prikey = null;
    try {
      ks = KeyStore.getInstance("jceks");
      FileInputStream fin;
      try {
        fin = new FileInputStream(keyStoreFile);
        try {
          try {
            ks.load(fin, storeFilePass.toCharArray());
            // 先打开文件
            prikey = (PrivateKey) ks.getKey(keyAlias, keyAliasPass
                    .toCharArray());
            // 通过别名和密码得到私钥
          } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
          } catch (CertificateException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    } catch (KeyStoreException e) {
      e.printStackTrace();
    }
    return prikey;
  }

  public static void main(String[] args) {
    PublicKey publicKey;
    PrivateKey privateKey;

    publicKey = getPublicKey("h:/keystore.jceks", "secret", "ES256");
    privateKey = getPrivateKey("h:/keystore.jceks", "secret", "ES256", "123456");

    System.out.println(publicKey.toString());
    System.out.println(privateKey.toString());
  }
}