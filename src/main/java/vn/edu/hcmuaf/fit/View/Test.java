package vn.edu.hcmuaf.fit.View;

import vn.edu.hcmuaf.fit.model.Symmetrics.Caesar;
import vn.edu.hcmuaf.fit.model.Symmetrics.ISymmetricCipher;

public class Test {
    public static void main(String[] args) {
        String plaintText = "Thanh sang xin chào nhá các bạn . thành công Caesar cipher :)";
        String key = "4";
        ISymmetricCipher CaesarCipher = new Caesar();
        String cipherText = CaesarCipher.encrypt(plaintText, key);
        System.out.println("Cipher Text: " + cipherText);
        String decryptedText = CaesarCipher.decrypt(cipherText, key);
        System.out.println("Decrypted Text: " + decryptedText);
        String fileSrc = "C:\\Users\\Admin\\Desktop\\aa.png";
        String destFile = "C:\\Users\\Admin\\Desktop\\abc.png";
        String fileSrc2 = "C:\\Users\\Admin\\Desktop\\plaint.png";
        try {
            CaesarCipher.encryptFile(fileSrc, destFile, key);
            System.out.println("File encrypted successfully.");
            CaesarCipher.decryptFile(destFile, fileSrc2, key);
            System.out.println("File decrypted successfully.");
        } catch (Exception e) {
            System.out.println("Error encrypting file: " + e.getMessage());
        }
    }
}
