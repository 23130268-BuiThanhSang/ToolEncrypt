package vn.edu.hcmuaf.fit.Controller;

import vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical.CaesarPanel;
import vn.edu.hcmuaf.fit.model.Symmetrics.CaesarCipher;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CaesarController {
    private CaesarCipher model;
    private CaesarPanel view;

    public CaesarController( CaesarPanel view) {
        this.model = new CaesarCipher();
        this.view = view;

        handleEvents();
    }

    private void handleEvents() {
        // process for encrypt Text
        view.getEncryptButton().addActionListener(e -> {
            try {
                String plaintText = view.getInput().getText();
                String key = view.getKey().getText();

                if (plaintText.isEmpty()) {
                    showError("Bạn hãy nhập nôi dung cần mã hóa");
                    return;
                }

                if (key.isEmpty()) {
                    showError("Bạn hãy nhập khóa của bạn hoặc có thể chọn Generate key ngẫu nhiên");
                    return;
                }

                String result = model.encrypt(plaintText, key);

                view.getOutput().setText(result);
            } catch (Exception ex) {
                showError("Key không hợp lệ !");
            }
        });


        // process for decrypt Text
        view.getDecryptButton().addActionListener(e -> {
            try {
                String cipherText = view.getInput().getText();
                String key = view.getKey().getText();

                if (cipherText.isEmpty()) {
                    showError("Bạn hãy nhập nôi dung cần giải mã");
                    return;
                }

                if (key.isEmpty()) {
                    showError("Bạn hãy nhập khóa của bạn hoặc có thể chọn Generate key ngẫu nhiên");
                    return;
                }

                String result = model.decrypt(cipherText, key);
                view.getOutput().setText(result);
            }catch (Exception ex){
                showError("sai key hoặc sai định dạng của cipherText(Base64), Xin vui lòng thử lại !)");
            }
        });

        // process for clear
        view.getClearButton().addActionListener(e -> {
           view.getInput().setText("");
           view.getOutput().setText("");
           view.getKey().setText("");
        });

        // process for genKey
        view.getGenKeyButton().addActionListener(e -> {;
            String key = model.generatekey(0);
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
    }

    private void showError(String mesage) {
        JOptionPane.showMessageDialog(null, mesage);
    }
}
