package vn.edu.hcmuaf.fit.Controller;

import vn.edu.hcmuaf.fit.View.HashPanel;
import vn.edu.hcmuaf.fit.model.Hashs.HashCipher;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HashController {
    private HashCipher model;
    private HashPanel view;

    public HashController(HashPanel view) {
        this.model = new HashCipher();
        this.view = view;

        handleTextEvents();
        handleFileEvents();
    }

    private void handleTextEvents() {
        // process for hash Text
        view.getHashTextBtn().addActionListener(e -> {
            try {
                String plaintText = view.getTextInput().getText();
                String algorithm = (String) view.getAlgorithmComboBox().getSelectedItem();

                if (plaintText.isEmpty()) {
                    showError("Bạn hãy nhập nội dung cần băm (hash)");
                    return;
                }

                String result = model.hashText(plaintText, algorithm);
                view.getTextOutput().setText(result);
            } catch (Exception ex) {
                showError("Lỗi băm văn bản: Thuật toán không được hỗ trợ hoặc có lỗi xảy ra!");
            }
        });

        // process read content from file
        view.getLoadTextFileBtn().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) content.append(line).append("\n");
                    reader.close();
                    view.getTextInput().setText(content.toString().trim());
                } catch (Exception ex) {
                    showError("Không thể thực hiện đọc file");
                }
            }
        });

        // process for clear Text
        view.getClearTextBtn().addActionListener(e -> {
            view.getTextInput().setText("");
            view.getTextOutput().setText("");
        });
    }

    private void handleFileEvents() {
        // process for browse source file
        view.getBrowseSourceBtn().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                view.getSourceFile().setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        // process for browse dest file
        view.getBrowseDestBtn().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                view.getDestFile().setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        // process for hash file
        view.getHashFileBtn().addActionListener(e -> {
            try {
                String source = view.getSourceFile().getText();
                String dest = view.getDestFile().getText();
                String algorithm = (String) view.getAlgorithmComboBox().getSelectedItem();

                if (source.isEmpty() || dest.isEmpty()) {
                    showError("Bạn hãy chọn File nguồn và File đích để lưu mã băm!");
                    return;
                }

                model.hashFile(source, dest, algorithm);
                JOptionPane.showMessageDialog(null, "Băm File thành công!");
            } catch (Exception ex) {
                showError("Lỗi băm File: " + ex.getMessage());
            }
        });

        // process for clear file
        view.getClearFileBtn().addActionListener(e -> {
            view.getSourceFile().setText("");
            view.getDestFile().setText("");
        });
    }

    private void showError(String mesage) {
        JOptionPane.showMessageDialog(null, mesage);
    }
}