package vn.edu.hcmuaf.fit.model.Asymmetrics;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSACipher {

    // process encrypt for text
    public String encryptText(String plaintText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] encryptedBytes = cipher.doFinal(plaintText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    // process decrypt for text
    public String decryptText(String cipherText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
    // proces encrypt for file . and fixx bug write file with AES
    public void encryptFile(String sourceFile, String destFile, PublicKey publicKey) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey aesKey = keyGen.generateKey();
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedAesKey = rsaCipher.doFinal(aesKey.getEncoded());

        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(destFile));
        FileInputStream fis = new FileInputStream(sourceFile);
        dos.writeInt(encryptedAesKey.length);
        dos.write(encryptedAesKey);
        dos.writeInt(iv.length);
        dos.write(iv);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            byte[] encryptedData = aesCipher.update(buffer, 0, bytesRead);
            if (encryptedData != null) {
                dos.write(encryptedData);
            }
        }
        byte[] finalEncryptedData = aesCipher.doFinal();
        if (finalEncryptedData != null) {
            dos.write(finalEncryptedData);
        }
        fis.close();
        dos.close();
    }
    //process decrypt for file . and fix bug read file with AES same encrypt file
    public void decryptFile(String sourceFile, String destFile, PrivateKey privateKey) throws Exception {
        DataInputStream dis = new DataInputStream(new FileInputStream(sourceFile));
        FileOutputStream fos = new FileOutputStream(destFile);
        int keyLength = dis.readInt();
        byte[] encryptedAesKey = new byte[keyLength];
        dis.readFully(encryptedAesKey);

        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] aesKeyBytes = rsaCipher.doFinal(encryptedAesKey);
        SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
        int ivLength = dis.readInt();
        byte[] iv = new byte[ivLength];
        dis.readFully(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
        byte[] inputBuffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = dis.read(inputBuffer)) != -1) {
            byte[] decryptedData = aesCipher.update(inputBuffer, 0, bytesRead);
            if (decryptedData != null) {
                fos.write(decryptedData);
            }
        }
        byte[] finalDecryptedData = aesCipher.doFinal();
        if (finalDecryptedData != null) {
            fos.write(finalDecryptedData);
        }
        dis.close();
        fos.close();

    }

    // process create key pair. include public key and private key
    public KeyPair generateKeyPair(int keySize) throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }
    // process transform texx to object public key
    public PublicKey getPublicKeyFromString(String strPublicKey) throws Exception {
       byte[] keyBytes = Base64.getDecoder().decode(strPublicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
    // process transform text to object private key
    public PrivateKey getPrivateKeyFromString(String strPrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(strPrivateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }
    // process key to String display in Jtextfield
    public String keyToString(Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

}
