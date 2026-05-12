package vn.edu.hcmuaf.fit.model.Classicals;

import java.util.Random;

public class AffineCipher{
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

    // process encrypt for text by affine cipher
    public String encrypt(String plainText, String language, String keyAStr, String keyBStr) {

        int a = normalizeA(keyAStr, language);
        int m = gettmode(language);
        int b = Math.floorMod(Integer.parseInt(keyBStr), m);



        StringBuilder result = new StringBuilder();

        for (char c : plainText.toCharArray()) {

            if (isEnglishMode(language)) {
                result.append(encryptForChar(c, EN_LOWER, EN_UPPER, a, b));
            } else if (isVietnameseMode(language)) {
                result.append(encryptForChar(c, VI_LOWER, VI_UPPER, a, b));
            } else {
                result.append(c);
            }
        }



        return result.toString();
    }
    // process decrypt for text by affine cipher
    public String decrypt(String text, String language, String keyaStr, String keybStr) {

        int a = normalizeA(keyaStr, language);

        int m = gettmode(language);
        int b = Math.floorMod(Integer.parseInt(keybStr), m);



        int inverseA = modInverse(a, m);

        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {

            if (isEnglishMode(language)) {
                result.append(DecryptForChar(c, EN_LOWER, EN_UPPER, inverseA, b, m));
            } else if (isVietnameseMode(language)) {
                result.append(DecryptForChar(c, VI_LOWER, VI_UPPER, inverseA, b, m));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    // process for encrypt character by affine cipher
    private char encryptForChar(char c, String lower, String upper, int a, int b) {

        int m = lower.length();


        if (Character.isDigit(c)) {
            int idx = DIGITS.indexOf(c);
            if (idx >= 0) {
                int newIndex = (a * idx + b) % DIGITS.length();
                return DIGITS.charAt(newIndex);
            }
            return c;
        }

        int idxLower = lower.indexOf(c);
        if (idxLower >= 0) {
            int newIndex = (a * idxLower + b) % m;
            return lower.charAt(newIndex);
        }


        int idxUpper = upper.indexOf(c);
        if (idxUpper >= 0) {
            int newIndex = (a * idxUpper + b) % m;
            return upper.charAt(newIndex);
        }

        return c;
    }
    // process for decrypt character by affine cipher
    private char DecryptForChar(char c, String lower, String upper, int invA, int b, int m) {

        if (Character.isDigit(c)) {
            int idx = DIGITS.indexOf(c);
            if (idx >= 0) {
                int newIndex = mod(invA * (idx - b), DIGITS.length());
                return DIGITS.charAt(newIndex);
            }
            return c;
        }



        int idxLower = lower.indexOf(c);
        if (idxLower >= 0) {
            int newIndex = mod(invA * (idxLower - b), m);
            return lower.charAt(newIndex);
        }

        int idxUpper = upper.indexOf(c);
        if (idxUpper >= 0) {
            int newIndex = mod(invA * (idxUpper - b), m);
            return upper.charAt(newIndex);
        }

        return c;
    }

    /**
     * method for mod value in case of negative value
     * @param value
     * @param mod
     * @return
     */
    private int mod(int value, int mod) {
        int r = value % mod;
        return (r < 0) ? r + mod : r;
    }

    private int modInverse(int a, int m) {
        a = a % m;

        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }

        throw new IllegalArgumentException("a không có nghịch đảo modular!");
    }

//    public int normalizeA(String aStr, String language) {
//        int m = gettmode(language);
//        int a = Integer.parseInt(aStr);
//        return Math.floorMod(a, m);
//    }
    public int normalizeA(String aStr, String language) {
        int m = gettmode(language);
        int a = Integer.parseInt(aStr);

        a = Math.floorMod(a, m);

        if (gcd(a, m) != 1) {
            throw new IllegalArgumentException("Key A không hợp lệ!");
        }

        return a;
    }


    public String generateKeyA(String language) {
        int m = gettmode(language);
        Random rd = new Random();

        int a;
        do {
            a = rd.nextInt(m - 1) + 1;
        } while (gcd(a, m) != 1);

        return String.valueOf(a);
    }

    public String generateKeyB(String language) {
        int m = gettmode(language);
        Random rd = new Random();
        return String.valueOf(rd.nextInt(m));
    }

    private int gettmode(String language) {
        return isVietnameseMode(language)
                ? VI_LOWER.length()
                : EN_LOWER.length();
    }

    private boolean isEnglishMode(String language) {
        return language != null && language.equalsIgnoreCase("English");
    }

    private boolean isVietnameseMode(String language) {
        return language != null && language.equalsIgnoreCase("Vietnamese");
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

}
