package vn.edu.hcmuaf.fit.Controller;

import vn.edu.hcmuaf.fit.View.ViewAlgorithmSymmetric.DESedePanel;
import vn.edu.hcmuaf.fit.model.Symmetrics.DESedeCipher;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class DESedeController {
    private DESedeCipher model;
    private DESedePanel view;

    public DESedeController(DESedePanel view) {
        this.model = new DESedeCipher();
        this.view = view;

        handleTextEvents();
        handleFileEvents();
    }

    private void handleTextEvents() {
        // process for encrypt Text
        view.getEncryptTextBtn().addActionListener(e -> {
            try {
                String plaintText = view.getTextInput().getText();
                String strkey = view.getTextKey().getText();
                String striv = view.getTextIv().getText();
                String mode = (String) view.getModeComboBox().getSelectedItem();
                String padding = (String) view.getPaddingComboBox().getSelectedItem();

                if (plaintText.isEmpty()) {
                    showError("Bạn hãy nhập nôi dung cần mã hóa");
                    return;
                }
                if (strkey.isEmpty()) {
                    showError("Bạn hãy nhập khóa của bạn");
                    return;
                }
                if (!mode.equals("ECB") && striv.isEmpty()) {
                    showError("Mode " + mode + " cần phải có IV!");
                    return;
                }

                String result = model.encryptText(plaintText, mode, padding, strkey, striv);
                view.getTextOutput().setText(result);
            } catch (Exception ex) {
                showError("Lỗi mã hóa: " + ex.getMessage());
            }
        });

        // process for decrypt Text
        view.getDecryptTextBtn().addActionListener(e -> {
            try {
                String cipherText = view.getTextInput().getText();
                String strkey = view.getTextKey().getText();
                String striv = view.getTextIv().getText();
                String mode = (String) view.getModeComboBox().getSelectedItem();
                String padding = (String) view.getPaddingComboBox().getSelectedItem();

                if (cipherText.isEmpty() || strkey.isEmpty()) {
                    showError("Bạn hãy nhập nội dung và khóa");
                    return;
                }

                String result = model.decryptText(cipherText, mode, padding, strkey, striv);
                view.getTextOutput().setText(result);
            } catch (Exception ex) {
                showError("Lỗi giải mã: Khóa hoặc dữ liệu sai!");
            }
        });

        // process for genKey Text
        view.getGenKeyTextBtn().addActionListener(e -> {
            try {
                int keySize = Integer.parseInt((String) view.getKeySizeComboBox().getSelectedItem());
                String mode = (String) view.getModeComboBox().getSelectedItem();

                view.getTextKey().setText(model.generateKey(keySize));
                if (!mode.equals("ECB")) {
                    view.getTextIv().setText(model.generateIV());
                }
            } catch (Exception ex) {
                showError("Lỗi tạo khóa!");
            }
        });

        // process for import key Text
        view.getImportKeyTextBtn().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
                    String key = reader.readLine();
                    String iv = reader.readLine();
                    reader.close();

                    if (key != null) view.getTextKey().setText(key);
                    if (iv != null) view.getTextIv().setText(iv);
                } catch (Exception ex) {
                    showError("Không thể đọc file Key!");
                }
            }
        });

        // process for export key Text
        view.getExportKeyTextBtn().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    FileWriter writer = new FileWriter(chooser.getSelectedFile());
                    writer.write(view.getTextKey().getText() + "\n");
                    if (!view.getTextIv().getText().isEmpty()) {
                        writer.write(view.getTextIv().getText());
                    }
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Export Key thành công!");
                } catch (Exception ex) {
                    showError("Không thể lưu file Key!");
                }
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
                    showError("không thể thực hiện đoc file");
                }
            }
        });

        // process for clear Text
        view.getClearTextBtn().addActionListener(e -> {
            view.getTextInput().setText("");
            view.getTextOutput().setText("");
            view.getTextKey().setText("");
            view.getTextIv().setText("");
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

        // process for encrypt file
        view.getEncryptFileBtn().addActionListener(e -> {
            try {
                String source = view.getSourceFile().getText();
                String dest = view.getDestFile().getText();
                String strkey = view.getFileKey().getText();
                String striv = view.getFileIv().getText();
                String mode = (String) view.getModeComboBox().getSelectedItem();
                String padding = (String) view.getPaddingComboBox().getSelectedItem();

                if (source.isEmpty() || dest.isEmpty() || strkey.isEmpty()) {
                    showError("Bạn hãy chọn File và nhập Khóa!");
                    return;
                }

                model.encryptFile(source, dest, mode, padding, strkey, striv);
                JOptionPane.showMessageDialog(null, "Mã hóa File thành công!");
            } catch (Exception ex) {
                showError("Lỗi mã hóa File!");
            }
        });

        // process for decrypt file
        view.getDecryptFileBtn().addActionListener(e -> {
            try {
                String source = view.getSourceFile().getText();
                String dest = view.getDestFile().getText();
                String strkey = view.getFileKey().getText();
                String striv = view.getFileIv().getText();
                String mode = (String) view.getModeComboBox().getSelectedItem();
                String padding = (String) view.getPaddingComboBox().getSelectedItem();

                if (source.isEmpty() || dest.isEmpty() || strkey.isEmpty()) {
                    showError("Bạn hãy chọn File và nhập Khóa!");
                    return;
                }

                model.decryptFile(source, dest, mode, padding, strkey, striv);
                JOptionPane.showMessageDialog(null, "Giải mã File thành công!");
            } catch (Exception ex) {
                showError("Lỗi giải mã File: Key của bạn bị sai dữ liệu bị hỏng!");
            }
        });

        // process for genKey file
        view.getGenKeyFileBtn().addActionListener(e -> {
            try {
                int keySize = Integer.parseInt((String) view.getKeySizeComboBox().getSelectedItem());
                String mode = (String) view.getModeComboBox().getSelectedItem();

                view.getFileKey().setText(model.generateKey(keySize));
                if (!mode.equals("ECB")) {
                    view.getFileIv().setText(model.generateIV());
                }
            } catch (Exception ex) {
                showError("Lỗi tạo khóa!");
            }
        });
        // process for import key File
        view.getImportKeyFileBtn().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
                    String key = reader.readLine();
                    String iv = reader.readLine();
                    reader.close();

                    if (key != null) view.getFileKey().setText(key);
                    if (iv != null) view.getFileIv().setText(iv);
                } catch (Exception ex) {
                    showError("Không thể đọc file Key!");
                }
            }
        });

        // process for export key File
        view.getExportKeyFileBtn().addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    FileWriter writer = new FileWriter(chooser.getSelectedFile());
                    writer.write(view.getFileKey().getText() + "\n");
                    if (!view.getFileIv().getText().isEmpty()) {
                        writer.write(view.getFileIv().getText());
                    }
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Export Key thành công!");
                } catch (Exception ex) {
                    showError("Không thể lưu file Key!");
                }
            }
        });

        // process for clear file
        view.getClearFileBtn().addActionListener(e -> {
            view.getSourceFile().setText("");
            view.getDestFile().setText("");
            view.getFileKey().setText("");
            view.getFileIv().setText("");
        });
    }

    private void showError(String mesage) {
        JOptionPane.showMessageDialog(null, mesage);
    }
}