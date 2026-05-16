package vn.edu.hcmuaf.fit.Controller;

import vn.edu.hcmuaf.fit.View.ClassicalPanel;
import vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical.AffinePanel;
import vn.edu.hcmuaf.fit.model.Classicals.AffineCipher;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AffineController {

    private AffineCipher model;
    private AffinePanel view;
    private ClassicalPanel parent;

    public AffineController(AffinePanel view, ClassicalPanel parent) {
        this.model = new AffineCipher();
        this.view = view;
        this.parent = parent;

        handleEvents();
    }

    private void handleEvents() {

        // process for encrypt Text
        view.getEncryptButton().addActionListener(e -> {
            try {
                String plainttex = view.getInput().getText();
                String languaguee = parent.getSelectedLanguage();

                if (!validateInPut(plainttex, languaguee)) {
                    showError("Nội dung khác mẫu ngôn ngữ, Xin vui lòng thử lại!");
                    return;
                }

                String aStr = view.getKeyA().getText();
                String bStr = view.getKeyB().getText();

                if (plainttex.isEmpty()) {
                    showError("Bạn hãy nhập nội dung cần mã hóa");
                    return;
                }

                if (aStr.isEmpty() || bStr.isEmpty()) {
                    showError("Bạn hãy nhập đầy đủ Key A và Key B");
                    return;
                }

                int a = model.normalizeA(aStr, languaguee);

                if (!isValidA(a, languaguee)) {
                    showError("Key A không hợp lệ (phải nguyên tố cùng nhau với bảng ký tự)");
                    return;
                }

                String result = model.encrypt(plainttex, languaguee,String.valueOf(a), bStr);
                view.getOutput().setText(result);

            }catch (NumberFormatException ex) {
                showError("Key A và Key B phải là số nguyên, vui lòng nhập lại!");
            }
            catch (Exception ex) {
                showError("Lỗi khi mã hóa Affine!");
            }
        });

        // process for decrypt Text
        view.getDecryptButton().addActionListener(e -> {
            try {
                String Ciphertext = view.getInput().getText();
                String language = parent.getSelectedLanguage();


                if (!validateInPut(Ciphertext, language)) {
                    showError("Nội dung khác mẫu ngôn ngữ, Xin vui lòng thử lại!");
                    return;
                }


                String aStr = view.getKeyA().getText();
                String bStr = view.getKeyB().getText();

                if (Ciphertext.isEmpty()) {
                    showError("Bạn hãy nhập nội dung cần giải mã");
                    return;
                }

                if (aStr.isEmpty() || bStr.isEmpty()) {
                    showError("Bạn hãy nhập đầy đủ Key A và Key B");
                    return;
                }

                int a = model.normalizeA(aStr, language);

                if (!isValidA(a, language)) {
                    showError("Key A không hợp lệ (không có nghịch đảo modular)");
                    return;
                }

                String result = model.decrypt(Ciphertext, language, String.valueOf(a), bStr);
                view.getOutput().setText(result);

            }catch (NumberFormatException ex) {
                showError("Key A và Key B phải là số nguyên, vui lòng nhập lại!");
            }
            catch (Exception ex) {
                showError("Lỗi khi giải mã Affine!");
            }
        });

        // process for button clear
        view.getClearButton().addActionListener(e -> {
            view.getInput().setText("");
            view.getOutput().setText("");
            view.getKeyA().setText("");
            view.getKeyB().setText("");
        });

        // process for generate key
        view.getGenKeyButton().addActionListener(e -> {
            String language = parent.getSelectedLanguage();

            String a = model.generateKeyA(language);
            String b = model.generateKeyB(language);

            view.getKeyA().setText(a);
            view.getKeyB().setText(b);
        });

        // process fpr load file
        view.getLoadFileButton().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    StringBuilder content = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }

                    reader.close();
                    view.getInput().setText(content.toString());

                } catch (Exception ex) {
                    showError("Không thể đọc file!");
                }
            }
        });

        // process for immport key
        view.getImportKeyButton().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    String a = reader.readLine();
                    String b = reader.readLine();

                    reader.close();

                    view.getKeyA().setText(a);
                    view.getKeyB().setText(b);

                } catch (Exception ex) {
                    showError("Không thể import key!");
                }
            }
        });

        //proocess fpr export key
        view.getExportKeyButton().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    FileWriter writer = new FileWriter(file);

                    writer.write(view.getKeyA().getText() + "\n");
                    writer.write(view.getKeyB().getText());

                    writer.close();

                    JOptionPane.showMessageDialog(null, "Export key thành công!");

                } catch (Exception ex) {
                    showError("Không thể export key!");
                }
            }
        });
    }

    // validate key A (không biết viết tiếng anh, kiểm tra A có phải số nguyên tố cùng nhau hay không )
    private boolean isValidA(int a, String language) {
        int m = getMod(language);
        return gcd(a, m) == 1;
    }

    private int getMod(String language) {
        return language.equalsIgnoreCase("Vietnamese")
                ? 67   // gần đúng độ dài bảng VI_LOWER
                : 26;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    private boolean validateInPut(String plaintText, String languege) {
        if (languege.equalsIgnoreCase("English")) {
            for(char c : plaintText.toCharArray()) {
                if (isVietNameseChar(c)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isVietNameseChar(char c) {
        String vietNameseChars ="aáàảãạâấầẩẫậăắằẳẵặ" +
                "bcdđ" +
                "eéèẻẽẹêếềểễệ" +
                "ghiíìỉĩị" +
                "klmnoóòỏõọôốồổỗộơớờởỡợ" +
                "pqrstuúùủũụưứừửữự" +
                "vwx" +
                "yýỳỷỹỵ" +
                "AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶ" +
                "BCDĐ" +
                "EÉÈẺẼẸÊẾỀỂỄỆ" +
                "GHIÍÌỈĨỊ" +
                "KLMNOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢ" +
                "PQRSTUÚÙỦŨỤƯỨỪỬỮỰ" +
                "VWX" +
                "YÝỲỶỸỴ";
        return vietNameseChars.indexOf(c) != -1;
    }
}