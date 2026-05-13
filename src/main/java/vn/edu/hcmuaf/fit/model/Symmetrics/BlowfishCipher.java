package vn.edu.hcmuaf.fit.model.Symmetrics;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Base64;

public class BlowfishCipher {

    // process encrypt for text
    public String encryptText(String plaintText, String mode, String padding, String strkey, String striv) throws Exception {
        String transformation = "Blowfish/" + mode + "/" + padding;
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKey secretKey = getSecretKeyFromString(strkey);
        if (mode.equals("ECB")) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else {
            IvParameterSpec ivSpec = getIvFromString(striv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        }
        byte[] byteEncrypted = cipher.doFinal(plaintText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(byteEncrypted);
    }

    // process decrypt for text
    public String decryptText(String cipherText, String mode, String padding, String strkey, String striv) throws Exception {
        String transformation = "Blowfish/" + mode + "/" + padding;
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKey secretKey = getSecretKeyFromString(strkey);


        if (mode.equals("ECB")) {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } else {
            IvParameterSpec ivSpec = getIvFromString(striv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        }

        byte[] byteDecrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(byteDecrypted, "UTF-8");
    }

    // process encrypt for file
    public void encryptFile(String sourceFile, String destFile, String mode, String padding, String strkey, String striv) throws Exception {
        String transformation = "Blowfish/" + mode + "/" + padding;
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKey secretKey = getSecretKeyFromString(strkey);


        if (mode.equals("ECB")) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else {
            IvParameterSpec ivSpec = getIvFromString(striv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        }


        FileInputStream fis = new FileInputStream(sourceFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);



        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            cos.write(buffer, 0, bytesRead);
        }

        cos.close();
        fos.close();
        fis.close();
    }

    // process decrypt for file
    public void decryptFile(String sourceFile, String destFile, String mode, String padding, String strkey, String striv) throws Exception {
        String transformation = "Blowfish/" + mode + "/" + padding;
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKey secretKey = getSecretKeyFromString(strkey);


        if (mode.equals("ECB")) {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } else {
            IvParameterSpec ivSpec = getIvFromString(striv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        }

        FileInputStream fis = new FileInputStream(sourceFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            cos.write(buffer, 0, bytesRead);
        }

        cos.close();
        fos.close();
        fis.close();
    }

    // procewss for generate key
    public String generateKey(int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("Blowfish");
        keyGen.init(keySize);

        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // process for generate IV
    public String generateIV() {
        byte[] ivBytes = new byte[8];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        return Base64.getEncoder().encodeToString(ivBytes);
    }

    private SecretKey getSecretKeyFromString(String strkey) {
        byte[] decodedKey = Base64.getDecoder().decode(strkey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "Blowfish");
    }

    private IvParameterSpec getIvFromString(String striv) {
        byte[] decodedIv = Base64.getDecoder().decode(striv);
        return new IvParameterSpec(decodedIv);
    }
}