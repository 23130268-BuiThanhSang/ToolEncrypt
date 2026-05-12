package vn.edu.hcmuaf.fit.Controller;

import vn.edu.hcmuaf.fit.View.ClassicalPanel;
import vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical.VigenerePanel;
import vn.edu.hcmuaf.fit.model.Classicals.VigenereCipher;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class VigenereController {
    private VigenereCipher model;
    private VigenerePanel view;
    private ClassicalPanel parent;

    public VigenereController(VigenerePanel view, ClassicalPanel parent) {
        this.model = new VigenereCipher();
        this.view = view;
        this.parent = parent;
        handleEvents();
    }

    private void handleEvents() {
        // proceess for encrypt Text
        view.getEncryptButton().addActionListener(e -> {
            try {
                String text = view.getInput().getText();
                String key = view.getKeyField().getText().trim();
                String language = parent.getSelectedLanguage();

                if (text.isEmpty() || key.isEmpty()) {
                    showError("Vui lòng nhập đầy đủ nội dung và Key!");
                    return;
                }

                if (!validateInPut(text, language)) {
                    showError("Nội dung khác mẫu ngôn ngữ (Có chứa tiếng Việt khi đang chọn English). Xin vui lòng thử lại!");
                    return;
                }

                if (!validateKey(key, language)) {
                    showError("Key Vigenère không hợp lệ!\nKey CHỈ được chứa chữ cái (không số, khoảng trắng hay ký tự đặc biệt) và phải đúng ngôn ngữ đã chọn.");
                    return;
                }

                String result = model.encrypt(text, language, key);
                view.getOutput().setText(result);
            } catch (Exception ex) {
                showError("Lỗi khi mã hóa Vigenère: " + ex.getMessage());
            }
        });

        //  process fo r decrypt Text
        view.getDecryptButton().addActionListener(e -> {
            try {
                String text = view.getInput().getText();
                String key = view.getKeyField().getText().trim();
                String language = parent.getSelectedLanguage();

                if (text.isEmpty() || key.isEmpty()) {
                    showError("Vui lòng nhập đầy đủ nội dung và Key!");
                    return;
                }

                if (!validateInPut(text, language)) {
                    showError("Nội dung khác mẫu ngôn ngữ (Có chứa tiếng Việt khi đang chọn English). Xin vui lòng thử lại!");
                    return;
                }

                if (!validateKey(key, language)) {
                    showError("Key Vigenère không hợp lệ!\nKey CHỈ được chứa chữ cái (không số, khoảng trắng hay ký tự đặc biệt) và phải đúng ngôn ngữ đã chọn.");
                    return;
                }

                String result = model.decrypt(text, language, key);
                view.getOutput().setText(result);
            } catch (Exception ex) {
                showError("Lỗi khi giải mã Vigenère: " + ex.getMessage());
            }
        });

        // procedcass for generate key
        view.getGenKeyButton().addActionListener(e -> {
            String language = parent.getSelectedLanguage();
            String key = model.generateKey(language, 8);
            view.getKeyField().setText(key);
        });


        view.getClearButton().addActionListener(e -> {
            view.getInput().setText("");
            view.getOutput().setText("");
            view.getKeyField().setText("");
        });

        //process for load file
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
                    view.getInput().setText(content.toString().trim());
                } catch (Exception ex) {
                    showError("Không thể đọc file text!");
                }
            }
        });


        view.getImportKeyButton().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    String key = reader.readLine();
                    reader.close();

                    if (key != null && !key.trim().isEmpty()) {
                        view.getKeyField().setText(key.trim());
                    } else {
                        showError("File không chứa Key hợp lệ!");
                    }
                } catch (Exception ex) {
                    showError("Không thể import key!");
                }
            }
        });

        // process for export key
        view.getExportKeyButton().addActionListener(e -> {
            String currentKey = view.getKeyField().getText().trim();
            if (currentKey.isEmpty()) {
                showError("Không có Key để xuất. Vui lòng nhập Key trước!");
                return;
            }

            JFileChooser chooser = new JFileChooser();
            int result = chooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    FileWriter writer = new FileWriter(file);

                    writer.write(currentKey);
                    writer.close();

                    JOptionPane.showMessageDialog(null, "Export key thành công!");
                } catch (Exception ex) {
                    showError("Không thể export key!");
                }
            }
        });
    }


    private boolean validateInPut(String text, String language) {
        if (language.equalsIgnoreCase("English")) {
            for (char c : text.toCharArray()) {
                if (isVietnameseChar(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateKey(String key, String language) {
        if (key == null || key.trim().isEmpty()) {
            return false;
        }

        if (language.equalsIgnoreCase("English")) {
            for (char c : key.toCharArray()) {
                if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                    return false;
                }
            }
        } else if (language.equalsIgnoreCase("Vietnamese")) {
            for (char c : key.toCharArray()) {
                if (!Character.isLetter(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isVietnameseChar(char c) {
        String vietnameseChars = "aáàảãạâấầẩẫậăắằẳẵặ" +
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
        return vietnameseChars.indexOf(c) != -1;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
}