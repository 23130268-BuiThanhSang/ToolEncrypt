package vn.edu.hcmuaf.fit.model.Classicals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TranspositionCipher {

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

    // process encrypt for text
    public String encrypt(String plainText, String language, String keyStr) {
        String lowerAlpha = isVietnameseMode(language) ? VI_LOWER : EN_LOWER;
        String upperAlpha = isVietnameseMode(language) ? VI_UPPER : EN_UPPER;

        int[] key = parseKey(keyStr);
        int cols = key.length;

        StringBuilder cleanText = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            if (lowerAlpha.indexOf(c) != -1 || upperAlpha.indexOf(c) != -1) {
                cleanText.append(c);
            }
        }

        while (cleanText.length() % cols != 0) {
            cleanText.append(lowerAlpha.charAt(0));
        }

        int rows = cleanText.length() / cols;
        char[][] matrix = new char[rows][cols];


        int idx = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = cleanText.charAt(idx);
                idx++;
            }
        }


        StringBuilder cipherClean = new StringBuilder();
        for (int k = 1; k <= cols; k++) {
            int colIndex = -1;
            for (int j = 0; j < cols; j++) {
                if (key[j] == k) {
                    colIndex = j;
                    break;
                }
            }
            for (int i = 0; i < rows; i++) {
                cipherClean.append(matrix[i][colIndex]);
            }
        }

        StringBuilder result = new StringBuilder();
        int indexEnc = 0;
        for (char c : plainText.toCharArray()) {
            if (lowerAlpha.indexOf(c) != -1 || upperAlpha.indexOf(c) != -1) {
                result.append(cipherClean.charAt(indexEnc));
                indexEnc++;
            } else {
                result.append(c);
            }
        }

        while (indexEnc < cipherClean.length()) {
            result.append(cipherClean.charAt(indexEnc));
            indexEnc++;
        }

        return result.toString();
    }

    // process decrypt for text
    public String decrypt(String cipherText, String language, String keyStr) {
        String lowerAlpha = isVietnameseMode(language) ? VI_LOWER : EN_LOWER;
        String upperAlpha = isVietnameseMode(language) ? VI_UPPER : EN_UPPER;

        int[] key = parseKey(keyStr);
        int cols = key.length;

        StringBuilder cleanText = new StringBuilder();
        for (char c : cipherText.toCharArray()) {
            if (lowerAlpha.indexOf(c) != -1 || upperAlpha.indexOf(c) != -1) {
                cleanText.append(c);
            }
        }

        while (cleanText.length() % cols != 0) {
            cleanText.append(lowerAlpha.charAt(0));
        }

        int rows = cleanText.length() / cols;
        char[][] matrix = new char[rows][cols];

        int idx = 0;
        for (int k = 1; k <= cols; k++) {
            int colIndex = -1;
            for (int j = 0; j < cols; j++) {
                if (key[j] == k) {
                    colIndex = j;
                    break;
                }
            }
            for (int i = 0; i < rows; i++) {
                matrix[i][colIndex] = cleanText.charAt(idx);
                idx++;
            }
        }

        StringBuilder plainClean = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                plainClean.append(matrix[i][j]);
            }
        }

        StringBuilder result = new StringBuilder();
        int indexDec = 0;
        for (char c : cipherText.toCharArray()) {
            if (lowerAlpha.indexOf(c) != -1 || upperAlpha.indexOf(c) != -1) {
                result.append(plainClean.charAt(indexDec));
                indexDec++;
            } else {
                result.append(c);
            }
        }

        while (indexDec < plainClean.length()) {
            result.append(plainClean.charAt(indexDec));
            indexDec++;
        }

        return result.toString();
    }

    // process for check language mode
    private boolean isVietnameseMode(String language) {
        return language != null && language.equalsIgnoreCase("Vietnamese");
    }

    private int[] parseKey(String keyStr) {
        String[] parts = keyStr.trim().split("-");
        int[] key = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            key[i] = Integer.parseInt(parts[i]);
        }
        return key;
    }

    // procewss for generate key
    public String generateKey(String language) {
        Random rd = new Random();


        int cols = rd.nextInt(4) + 4;

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= cols; i++) {
            list.add(i);
        }


        Collections.shuffle(list);

        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            keyBuilder.append(list.get(i));
            if (i < list.size() - 1) {
                keyBuilder.append("-");
            }
        }
        return keyBuilder.toString();
    }

    // process for validate key
    public boolean validateKey(String keyStr, String language) {
        try {
            if (keyStr == null || !keyStr.contains("-")) {
                return false;
            }

            String[] parts = keyStr.trim().split("-");
            int n = parts.length;
            boolean[] check = new boolean[n + 1];


            for (String p : parts) {
                int val = Integer.parseInt(p);
                if (val < 1 || val > n) return false;
                check[val] = true;
            }

            for (int i = 1; i <= n; i++) {
                if (!check[i]) return false;
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}