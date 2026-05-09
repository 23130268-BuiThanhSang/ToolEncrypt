package vn.edu.hcmuaf.fit.model.Symmetrics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class Caesar implements ISymmetricCipher {
    /**
     * this is method encrypt Text with Caesar cipher, use multiple language
     *
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
     *
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
     *
     * @param input
     * @param shift
     * @param mode
     * @return
     */
    private byte[] processBytes(byte[] input, int shift, boolean mode) {
        byte[] result = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            int val = input[i] & 0xFF;
            int newVal;
            if (mode == true) {
                newVal = (val + shift) % 256;
            } else {
                newVal = (val - shift) % 256;

            }
            if (newVal < 0) {
                newVal += 256;
            }
            result[i] = (byte) newVal;
        }
        return result;

    }

    /**
     * this is method encrypt for File with Caesar cipher, use multiple file type
     *
     * @param srcFilePath
     * @param destFilePath
     * @param key
     */
    @Override
    public void encryptFile(String srcFilePath, String destFilePath, String key) throws IOException {
        processFile(srcFilePath, destFilePath, key, true);
    }

    /**
     * this is method decrypt for File with Caesar cipher, use multiple file type
     *
     * @param srcFilePath
     * @param destFilePath
     * @param key
     */
    @Override
    public void decryptFile(String srcFilePath, String destFilePath, String key) throws IOException {
        processFile(srcFilePath, destFilePath, key, false);
    }

    /**
     * this is method process file . main for encrypt and decrypt file with Caesar cipher
     *
     * @param srcFilePath
     * @param destFilePath
     * @param key
     * @param b
     */
    private void processFile(String srcFilePath, String destFilePath, String key, boolean b) throws IOException {
        int shift = Integer.parseInt(key.trim());
        try { InputStream is = new FileInputStream(srcFilePath);
            OutputStream os = new FileOutputStream(destFilePath);
            byte[] buffer = new byte[1024 * 8];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                byte[] temp = new byte[bytesRead];
                System.arraycopy(buffer, 0, temp, 0, bytesRead);
                byte[] processedTemp = processBytes(temp, shift, b);
                os.write(processedTemp);
            }
            is.close();
            os.close();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }


    /**
     * this is method generate key with random bumber 0-256
     *
     * @param keysize
     * @return
     */
    @Override
    public String generatekey(int keysize) {
        Random random = new Random();
        int key = random.nextInt(256);
        return String.valueOf(key);
    }
}
