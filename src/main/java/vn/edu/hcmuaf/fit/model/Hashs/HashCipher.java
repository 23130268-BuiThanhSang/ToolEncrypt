package vn.edu.hcmuaf.fit.model.Hashs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCipher {
    // process for hash text
    public String hashText(String plaintText, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = md.digest(plaintText.getBytes("UTF-8"));
        return bytesToHex(hashBytes);
    }
    // process for hash file
    public void hashFile(String sourceFile, String destFile, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        FileInputStream fis = new FileInputStream(sourceFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            md.update(buffer, 0, bytesRead);
        }
        fis.close();
        byte[] hashBytes = md.digest();
        String hashHex = bytesToHex(hashBytes);
        FileOutputStream fos = new FileOutputStream(destFile);
        fos.write(hashHex.getBytes("UTF-8"));
        fos.close();
    }
    // process convert byte array to hex string
    private String bytesToHex(byte[] hashBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
