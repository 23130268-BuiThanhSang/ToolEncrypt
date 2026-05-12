package vn.edu.hcmuaf.fit.model.Classicals;

import java.util.Random;

public class CaesarCipher {

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

    // process encrypt for text
    public String encrypt(String plainText, String language, String keyStr) {
        int key = parseKey(keyStr);
        StringBuilder result = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            if (isEnglishMode(language)) {
                result.append(shiftChar(c, EN_LOWER, EN_UPPER, key));
            } else if (isVietnameseMode(language)) {
                result.append(shiftChar(c, VI_LOWER, VI_UPPER, key));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    // process decrypt for text
    public String decrypt(String cipherText, String language, String keyStr) {
        int key = parseKey(keyStr);
        StringBuilder result = new StringBuilder();

        for (char c : cipherText.toCharArray()) {
            if (isEnglishMode(language)) {
                result.append(shiftChar(c, EN_LOWER, EN_UPPER, -key));
            } else if (isVietnameseMode(language)) {
                result.append(shiftChar(c, VI_LOWER, VI_UPPER, -key));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    // process for check language mode
    private boolean isEnglishMode(String language) {
        return language != null && language.equalsIgnoreCase("English");
    }

    private boolean isVietnameseMode(String language) {
        return language != null && language.equalsIgnoreCase("Vietnamese");
    }

    private int parseKey(String keyStr) {
        return Integer.parseInt(keyStr);
    }

    /**
     * important method for shift char in text
     * @param c
     * @param lowerAlphabet
     * @param upperAlphabet
     * @param key
     * @return
     */
    private char shiftChar(char c, String lowerAlphabet, String upperAlphabet, int key) {
        if (Character.isDigit(c)) {
            int idx = DIGITS.indexOf(c);
            int newIndex = mod(idx + key, DIGITS.length());
            return DIGITS.charAt(newIndex);
        }

        int lowerIdx = lowerAlphabet.indexOf(c);
        if (lowerIdx >= 0) {
            int newIndex = mod(lowerIdx + key, lowerAlphabet.length());
            return lowerAlphabet.charAt(newIndex);
        }

        int upperIdx = upperAlphabet.indexOf(c);
        if (upperIdx >= 0) {
            int newIndex = mod(upperIdx + key, upperAlphabet.length());
            return upperAlphabet.charAt(newIndex);
        }

        return c;
    }

    private int mod(int value, int mod) {
        int r = value % mod;
        return (r < 0) ? r + mod : r;
    }

    // procewss for generate key
    public String generateKey(String language) {
        int mod = isVietnameseMode(language)
                ? VI_LOWER.length()
                : EN_LOWER.length();
        Random rd = new Random();
        int key = rd.nextInt(mod-1) + 1;

        return String.valueOf(key);

    }

    // process for validate key
    public int normalizeKey(String keyStr, String language) {
        try {
            int key = Integer.parseInt(keyStr.trim());
            int mod = isVietnameseMode(language)
                    ? VI_LOWER.length()
                    : EN_LOWER.length();
            return Math.floorMod(key, mod);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Key phải là một số nguyên !");
        }
    }
}