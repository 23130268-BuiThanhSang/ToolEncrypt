package vn.edu.hcmuaf.fit.model.Symmetrics;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Caesar implements ISymmetricCipher{
    /**
     * this is method encrypt Text with Caesar cipher, use multiple language
     * @param plainText
     * @param key
     * @return
     */
    @Override
    public String encrypt(String plainText, String key) {
        int shift = Integer.parseInt(key.trim());
        byte[] input = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] output = processBytes(input, shift, true);

        return Base64.getEncoder().encodeToString(output);
    }

    /**
     * this is method decypt Text with Caesar cipher, use while encrypt
     * @param cipherText
     * @param key
     * @return
     */
    @Override
    public String decrypt(String cipherText, String key) {
        int shift = Integer.parseInt(key.trim());
        byte[] input = Base64.getDecoder().decode(cipherText);
        byte[] output = processBytes(input, shift, false);
        return new String(output, StandardCharsets.UTF_8);
    }

    /**
     * this is method process byte encrypt or decrypt with Caesar cipher, use for Text
     * @param input
     * @param shift
     * @param mode
     * @return
     */
    private byte[] processBytes(byte[] input, int shift, boolean mode) {
        byte[] result = new byte[input.length];
        for (int i = 0; i<input.length; i++){
            int val = input[i] & 0xFF;
             int newVal;
            if (mode == true) {
                newVal = (val+ shift)% 256;
            }else{
                newVal = (val - shift)%256;
                if(newVal<0){
                    newVal += 256;
                }
            }
            result[i] = (byte) newVal;
        }
        return result;

    }

    @Override
    public void encryptFile(String srcFilePath, String destFilePath, String key) {

    }

    @Override
    public void decryptFile(String srcFilePath, String destFilePath, String key) {

    }

    @Override
    public String generatekey(int keysize) {
        return "";
    }
}
