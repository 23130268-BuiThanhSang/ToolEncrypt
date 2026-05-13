package vn.edu.hcmuaf.fit.Controller;

import vn.edu.hcmuaf.fit.View.ClassicalPanel;
import vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical.SubstitutionPanel;
import vn.edu.hcmuaf.fit.model.Classicals.SubstitutionCipher;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SubstitutionController {
    private SubstitutionCipher model;
    private SubstitutionPanel view;
    private ClassicalPanel parent;

    public SubstitutionController(SubstitutionPanel view, ClassicalPanel parent) {
        this.model = new SubstitutionCipher();
        this.view = view;
        this.parent = parent;

        handleEvents();
    }

    private void handleEvents() {
        // process for encrypt Text
        view.getEncryptButton().addActionListener(e -> {
            try {
                String plaintText = view.getInput().getText();
                String strkey = view.getKey().getText();
                String languege = parent.getSelectedLanguage();

                if (!validateInPut(plaintText, languege)){
                    showError("nội dung khác mẫu ngôn ngữ , Xin vui lòng thử lại !");
                    return;
                }

                if (strkey.isEmpty()) {
                    showError("Bạn hãy nhập khóa của bạn hoặc có thể chọn Generate key ngẫu nhiên");
                    return;
                }

                if (!model.validateKey(strkey, languege)) {
                    showError("Key không hợp lệ với ngôn ngữ đã chọn!");
                    return;
                }

                if (plaintText.isEmpty()) {
                    showError("Bạn hãy nhập nôi dung cần mã hóa");
                    return;
                }

                String result = model.encrypt(plaintText, languege, strkey);
                view.getOutput().setText(result);

            } catch (Exception ex) {
                showError("Lỗi quá trình mã hóa !");
            }
        });

        // process for decrypt Text
        view.getDecryptButton().addActionListener(e -> {
            try {
                String cipherText = view.getInput().getText();
                String strkey = view.getKey().getText();
                String language = parent.getSelectedLanguage();

                if (!validateInPut(cipherText, language)){
                    showError("nội dung khác mẫu ngôn ngữ , Xin vui lòng thử lại !");
                    return;
                }

                if (strkey.isEmpty()) {
                    showError("Bạn hãy nhập khóa của bạn hoặc có thể chọn Generate key ngẫu nhiên");
                    return;
                }

                if (!model.validateKey(strkey, language)) {
                    showError("Key không hợp lệ với ngôn ngữ đã chọn!");
                    return;
                }

                if (cipherText.isEmpty()) {
                    showError("Bạn hãy nhập nôi dung cần giải mã");
                    return;
                }

                String result = model.decrypt(cipherText, language, strkey);
                view.getOutput().setText(result);

            } catch (Exception ex) {
                showError("Lỗi quá trình giải mã !");
            }
        });

        // process for clear
        view.getClearButton().addActionListener(e -> {
            view.getInput().setText("");
            view.getOutput().setText("");
            view.getKey().setText("");
        });

        // process for genKey
        view.getGenKeyButton().addActionListener(e -> {
            String language = parent.getSelectedLanguage();
            String key = model.generateKey(language);
            view.getKey().setText(key);
        });

        // process read content from file and encrypt or decrypt
        view.getLoadFileButton().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    StringBuilder content = new StringBuilder();
                    String line;

                    while ((line= reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }

                    reader.close();
                    view.getInput().setText(content.toString());
                }catch (Exception ex){
                    showError("không thể thực hiện đoc file");
                }
            }
        });

        // process for import key
        view.getImportKeyButton().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String key = reader.readLine();
                    reader.close();
                    view.getKey().setText(key);
                }catch (Exception ex){
                    showError("không thể thực hiện import Key");
                }
            }
        });

        // process for export key
        view.getExportKeyButton().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try{
                    File file = chooser.getSelectedFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write(view.getKey().getText());
                    writer.close();

                    JOptionPane.showMessageDialog(null, "Export key thành công !");
                } catch (Exception ex) {
                    showError("không thể thực hiện export key");
                }
            }
        });
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

    private void showError(String mesage) {
        JOptionPane.showMessageDialog(null, mesage);
    }
}