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
    }
}
