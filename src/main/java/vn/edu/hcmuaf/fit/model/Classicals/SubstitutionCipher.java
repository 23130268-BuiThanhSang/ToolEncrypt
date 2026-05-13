package vn.edu.hcmuaf.fit.model.Classicals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubstitutionCipher {

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
        StringBuilder result = new StringBuilder();

        String[] keys = keyStr.split("-");
        String lowerKey = keys[0];
        String upperKey = keys[1];
        String digitKey = keys[2];

        for (char c : plainText.toCharArray()) {
            if (isEnglishMode(language)) {
                result.append(encryptChar(c, EN_LOWER, EN_UPPER, lowerKey, upperKey, digitKey));
            } else if (isVietnameseMode(language)) {
                result.append(encryptChar(c, VI_LOWER, VI_UPPER, lowerKey, upperKey, digitKey));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    // process decrypt for text
    public String decrypt(String cipherText, String language, String keyStr) {
        StringBuilder result = new StringBuilder();

        String[] keys = keyStr.split("-");
        String lowerKey = keys[0];
        String upperKey = keys[1];
        String digitKey = keys[2];

        for (char c : cipherText.toCharArray()) {
            if (isEnglishMode(language)) {
                result.append(decryptChar(c, EN_LOWER, EN_UPPER, lowerKey, upperKey, digitKey));
            } else if (isVietnameseMode(language)) {
                result.append(decryptChar(c, VI_LOWER, VI_UPPER, lowerKey, upperKey, digitKey));
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


    private char encryptChar(char c, String lowerAlphabet, String upperAlphabet, String lowerKey, String upperKey, String digitKey) {
        if (Character.isDigit(c)) {
            int idx = DIGITS.indexOf(c);
            if (idx != -1) {
                return digitKey.charAt(idx);
            }
        }


        int lowerIdx = lowerAlphabet.indexOf(c);
        if (lowerIdx != -1) {
            return lowerKey.charAt(lowerIdx);
        }


        int upperIdx = upperAlphabet.indexOf(c);
        if (upperIdx != -1) {
            return upperKey.charAt(upperIdx);
        }

        return c;
    }

    private char decryptChar(char c, String lowerAlphabet, String upperAlphabet, String lowerKey, String upperKey, String digitKey) {
        // xet so: tim trong key roi lay chu trong DIGITS
        if (Character.isDigit(c)) {
            int idx = digitKey.indexOf(c);
            if (idx != -1) {
                return DIGITS.charAt(idx);
            }
        }


        int lowerIdx = lowerKey.indexOf(c);
        if (lowerIdx != -1) {
            return lowerAlphabet.charAt(lowerIdx);
        }

        int upperIdx = upperKey.indexOf(c);
        if (upperIdx != -1) {
            return upperAlphabet.charAt(upperIdx);
        }

        return c;
    }

    // procewss for generate key
    public String generateKey(String language) {
        String lower = isVietnameseMode(language) ? VI_LOWER : EN_LOWER;
        String upper = isVietnameseMode(language) ? VI_UPPER : EN_UPPER;

        String shuffledLower = shuffleString(lower);
        String shuffledUpper = shuffleString(upper);
        String shuffledDigits = shuffleString(DIGITS);

        return shuffledLower + "-" + shuffledUpper + "-" + shuffledDigits;
    }

    // method main to shuffle string
    private String shuffleString(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);

        StringBuilder output = new StringBuilder();
        for (char c : characters) {
            output.append(c);
        }
        return output.toString();
    }

    // process for validate key
    public boolean validateKey(String keyStr, String language) {
        try {
            if (keyStr == null || keyStr.equals("")) {
                return false;
            }

            String[] keys = keyStr.split("-");
            if (keys.length != 3) {
                return false;
            }


            int expectedLength = isVietnameseMode(language) ? VI_LOWER.length() : EN_LOWER.length();

            // kiểm tra xem do dai 2 chuoi lower va upper co bang bang chu cai khong
            if (keys[0].length() != expectedLength || keys[1].length() != expectedLength) {
                return false;
            }





            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}