package vn.edu.hcmuaf.fit.model.Classicals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VigenereCipher {
    private static final String EN_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String EN_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String VI_LOWER =
            "aáàảãạâấầẩẫậăắằẳẵặ" +
                    "bcdđ" +
                    "eéèẻẽẹêếềểễệ" +
                    "ghiíìỉĩị" +
                    "klmnoóòỏõọôốồổỗộơớờởỡợ" +
                    "pqrstuúùủũụưứừửữự" +
                    "vwx" +
                    "yýỳỷỹỵ";

    private static final String VI_UPPER =
            "AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶ" +
                    "BCDĐ" +
                    "EÉÈẺẼẸÊẾỀỂỄỆ" +
                    "GHIÍÌỈĨỊ" +
                    "KLMNOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢ" +
                    "PQRSTUÚÙỦŨỤƯỨỪỬỮỰ" +
                    "VWX" +
                    "YÝỲỶỸỴ";

    private static final String DIGITS = "0123456789";

    public String encrypt(String plainText, String language, String key) {
        return processForVigenere(plainText, language, key, true);
    }

    public String decrypt(String cipherText, String language, String key) {
        return processForVigenere(cipherText, language, key, false);
    }

    private String processForVigenere(String text, String language, String key, boolean isEncrypt) {

        String ChuThuong = isVietnameseMode(language) ? VI_LOWER : EN_LOWER;
        String chuHoa = isVietnameseMode(language) ? VI_UPPER : EN_UPPER;
        int m = ChuThuong.length();

        List<Integer> shifts = new ArrayList<>();
        for (char k : key.toCharArray()) {

            int shift = ChuThuong.indexOf(k);
            if (shift == -1) {
                shift = chuHoa.indexOf(k);
            }
            if (shift != -1) {
                shifts.add(shift);
            }
        }

        if (shifts.isEmpty()) {
            throw new IllegalArgumentException("Key không hợp lệ! Key phải chứa ít nhất 1 ký tự chữ cái.");
        }

        StringBuilder result = new StringBuilder();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            int shift = shifts.get(keyIndex % shifts.size());

            if (Character.isDigit(c)) {

                int p = DIGITS.indexOf(c);
                int newIndex = isEncrypt ? mod(p + shift, DIGITS.length()) : mod(p - shift, DIGITS.length());

                result.append(DIGITS.charAt(newIndex));
                keyIndex++;

            } else if (ChuThuong.indexOf(c) != -1) {
                int p = ChuThuong.indexOf(c);

                int newIndex = isEncrypt ? mod(p + shift, m) : mod(p - shift, m);
                result.append(ChuThuong.charAt(newIndex));

                keyIndex++;

            } else if (chuHoa.indexOf(c) != -1) {

                int p = chuHoa.indexOf(c);
                int newIndex = isEncrypt ? mod(p + shift, m) : mod(p - shift, m);
                result.append(chuHoa.charAt(newIndex));
                keyIndex++;

            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    private int mod(int value, int mod) {
        int r = value % mod;
        return (r < 0) ? r + mod : r;
    }

    public String generateKey(String language, int length) {
        String alphabet = isVietnameseMode(language) ? VI_LOWER : EN_LOWER;
        Random rd = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(alphabet.charAt(rd.nextInt(alphabet.length())));
        }
        return key.toString();
    }

    private boolean isVietnameseMode(String language) {
        return language != null && language.equalsIgnoreCase("Vietnamese");
    }
}