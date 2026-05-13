package vn.edu.hcmuaf.fit.model.Classicals;

import java.util.Random;

public class HillCipher {

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
        int mod = lowerAlpha.length();

        int[][] keyMatrix = parseKey(keyStr);

        StringBuilder cleanText = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            if (lowerAlpha.indexOf(c) != -1) {
                cleanText.append(c);
            } else if (upperAlpha.indexOf(c) != -1) {
                int idx = upperAlpha.indexOf(c);
                cleanText.append(lowerAlpha.charAt(idx));
            }
        }

        if (cleanText.length() % 2 != 0) {
            cleanText.append(lowerAlpha.charAt(0));
        }

        StringBuilder cipherClean = new StringBuilder();
        for (int i = 0; i < cleanText.length(); i += 2) {
            int p1 = lowerAlpha.indexOf(cleanText.charAt(i));
            int p2 = lowerAlpha.indexOf(cleanText.charAt(i + 1));

            int c1 = (keyMatrix[0][0] * p1 + keyMatrix[0][1] * p2) % mod;
            int c2 = (keyMatrix[1][0] * p1 + keyMatrix[1][1] * p2) % mod;

            cipherClean.append(lowerAlpha.charAt(c1));
            cipherClean.append(lowerAlpha.charAt(c2));
        }

        StringBuilder result = new StringBuilder();
        int index = 0;
        for (char c : plainText.toCharArray()) {
            if (lowerAlpha.indexOf(c) != -1) {
                result.append(cipherClean.charAt(index));
                index++;
            } else if (upperAlpha.indexOf(c) != -1) {
                char enChar = cipherClean.charAt(index);
                int idx = lowerAlpha.indexOf(enChar);
                result.append(upperAlpha.charAt(idx));
                index++;
            } else {
                result.append(c);
            }
        }

        if (index < cipherClean.length()) {
            result.append(cipherClean.charAt(index));
        }

        return result.toString();
    }

    // process decrypt for text
    public String decrypt(String cipherText, String language, String keyStr) {
        String lowerAlpha = isVietnameseMode(language) ? VI_LOWER : EN_LOWER;
        String upperAlpha = isVietnameseMode(language) ? VI_UPPER : EN_UPPER;
        int mod = lowerAlpha.length();

        int[][] keyMatrix = parseKey(keyStr);
        int[][] inverseMatrix = getInverseMatrix(keyMatrix, mod);

        StringBuilder cleanText = new StringBuilder();
        for (char c : cipherText.toCharArray()) {
            if (lowerAlpha.indexOf(c) != -1) {
                cleanText.append(c);
            } else if (upperAlpha.indexOf(c) != -1) {
                int idx = upperAlpha.indexOf(c);
                cleanText.append(lowerAlpha.charAt(idx));
            }
        }

        if (cleanText.length() % 2 != 0) {
            cleanText.append(lowerAlpha.charAt(0));
        }

        StringBuilder plainClean = new StringBuilder();
        for (int i = 0; i < cleanText.length(); i += 2) {
            int c1 = lowerAlpha.indexOf(cleanText.charAt(i));
            int c2 = lowerAlpha.indexOf(cleanText.charAt(i + 1));

            int p1 = (inverseMatrix[0][0] * c1 + inverseMatrix[0][1] * c2) % mod;
            int p2 = (inverseMatrix[1][0] * c1 + inverseMatrix[1][1] * c2) % mod;

            plainClean.append(lowerAlpha.charAt(p1));
            plainClean.append(lowerAlpha.charAt(p2));
        }

        StringBuilder result = new StringBuilder();
        int index = 0;
        for (char c : cipherText.toCharArray()) {
            if (lowerAlpha.indexOf(c) != -1) {
                result.append(plainClean.charAt(index));
                index++;
            } else if (upperAlpha.indexOf(c) != -1) {
                char deChar = plainClean.charAt(index);
                int idx = lowerAlpha.indexOf(deChar);
                result.append(upperAlpha.charAt(idx));
                index++;
            } else {
                result.append(c);
            }
        }

        if (index < plainClean.length()) {
            result.append(plainClean.charAt(index));
        }

        return result.toString();
    }

    // process for check language mode
    private boolean isVietnameseMode(String language) {
        return language != null && language.equalsIgnoreCase("Vietnamese");
    }

    private int[][] getInverseMatrix(int[][] matrix, int mod) {
        int det = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % mod;
        det = (det % mod + mod) % mod;



        int detInverse = -1;
        for (int i = 1; i < mod; i++) {
            if ((det * i) % mod == 1) {
                detInverse = i;
                break;
            }
        }




        int[][] inv = new int[2][2];
        inv[0][0] = (matrix[1][1] * detInverse) % mod;
        inv[0][1] = (-matrix[0][1] * detInverse) % mod;

        inv[1][0] = (-matrix[1][0] * detInverse) % mod;
        inv[1][1] = (matrix[0][0] * detInverse) % mod;




        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inv[i][j] = (inv[i][j] % mod + mod) % mod;
            }
        }
        return inv;
    }

    private int[][] parseKey(String keyStr) {
        String[] parts = keyStr.trim().split("-");
        int[][] matrix = new int[2][2];
        matrix[0][0] = Integer.parseInt(parts[0]);
        matrix[0][1] = Integer.parseInt(parts[1]);
        matrix[1][0] = Integer.parseInt(parts[2]);
        matrix[1][1] = Integer.parseInt(parts[3]);
        return matrix;
    }



    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // process for generate key
    public String generateKey(String language) {
        int mod = isVietnameseMode(language) ? VI_LOWER.length() : EN_LOWER.length();
        Random rd = new Random();

        while (true) {
            int k00 = rd.nextInt(mod);
            int k01 = rd.nextInt(mod);
            int k10 = rd.nextInt(mod);
            int k11 = rd.nextInt(mod);

            int det = (k00 * k11 - k01 * k10) % mod;
            det = (det % mod + mod) % mod;

            if (gcd(det, mod) == 1) {
                return k00 + "-" + k01 + "-" + k10 + "-" + k11;
            }
        }
    }


    public boolean validateKey(String keyStr, String language) {
        try {
            if (keyStr == null || !keyStr.contains("-")) {
                return false;
            }

            String[] parts = keyStr.trim().split("-");
            if (parts.length != 4) {
                return false;
            }

            int mod = isVietnameseMode(language) ? VI_LOWER.length() : EN_LOWER.length();
            int k00 = Integer.parseInt(parts[0]);
            int k01 = Integer.parseInt(parts[1]);
            int k10 = Integer.parseInt(parts[2]);
            int k11 = Integer.parseInt(parts[3]);

            int det = (k00 * k11 - k01 * k10) % mod;
            det = (det % mod + mod) % mod;

            if (gcd(det, mod) != 1) {
                return false;
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}