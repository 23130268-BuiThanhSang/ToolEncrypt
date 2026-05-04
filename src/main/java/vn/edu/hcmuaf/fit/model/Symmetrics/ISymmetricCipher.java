package vn.edu.hcmuaf.fit.model.Symmetrics;

public interface ISymmetricCipher {
//    this is method for Text
    public String encrypt(String plainText, String key);
    public String decrypt(String cipherText, String key);
//    this is method for File
    public void encryptFile(String srcFilePath, String destFilePath, String key);
    public void decryptFile(String srcFilePath, String destFilePath, String key);
//    this is method generate key while user no create key
    public String generatekey(int keysize);
}
