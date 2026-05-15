package vn.edu.hcmuaf.fit.Controller;

import vn.edu.hcmuaf.fit.View.ASymmetricsPanel;
import vn.edu.hcmuaf.fit.model.Asymmetrics.RSACipher;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAController {
    private RSACipher model;
    private ASymmetricsPanel view;

    public RSAController(ASymmetricsPanel view) {
        this.model = new RSACipher();
        this.view = view;

        handleTextEvents();
        handleFileEvents();
    }

    private void handleTextEvents() {
        // process for encrypt Text
        view.getEncryptTextBtn().addActionListener(e -> {
            try {
                String plaintText = view.getTextInput().getText();
                String strPublicKey = view.getPublicKeyTextField().getText();

                if (plaintText.isEmpty()) {
                    showError("Bạn hãy nhập nội dung cần mã hóa");
                    return;
                }
                if (strPublicKey.isEmpty()) {
                    showError("Bạn hãy nhập Public Key để mã hóa");
                    return;
                }

                PublicKey publicKey = model.getPublicKeyFromString(strPublicKey);
                String result = model.encryptText(plaintText, publicKey);
                view.getTextOutput().setText(result);
            } catch (Exception ex) {
                showError("Lỗi mã hóa: " + ex.getMessage());
            }
        });

        // process for decrypt Text
        view.getDecryptTextBtn().addActionListener(e -> {
            try {
                String cipherText = view.getTextInput().getText();
                String strPrivateKey = view.getPrivateKeyTextField().getText();

                if (cipherText.isEmpty() || strPrivateKey.isEmpty()) {
                    showError("Bạn hãy nhập nội dung và Private Key");
                    return;
                }

                PrivateKey privateKey = model.getPrivateKeyFromString(strPrivateKey);
                String result = model.decryptText(cipherText, privateKey);
                view.getTextOutput().setText(result);
            } catch (Exception ex) {
                showError("Lỗi giải mã: Khóa hoặc dữ liệu sai!");
            }
        });

        // process for genKey Text
        view.getGenKeyTextBtn().addActionListener(e -> {
            try {
                int keySize = Integer.parseInt((String) view.getKeySizeComboBox().getSelectedItem());
                KeyPair keyPair = model.generateKeyPair(keySize);

                view.getPublicKeyTextField().setText(model.keyToString(keyPair.getPublic()));
                view.getPrivateKeyTextField().setText(model.keyToString(keyPair.getPrivate()));
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
                    String publicKey = reader.readLine();
                    String privateKey = reader.readLine();
                    reader.close();

                    if (publicKey != null) view.getPublicKeyTextField().setText(publicKey);
                    if (privateKey != null) view.getPrivateKeyTextField().setText(privateKey);
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
                    writer.write(view.getPublicKeyTextField().getText() + "\n");
                    if (!view.getPrivateKeyTextField().getText().isEmpty()) {
                        writer.write(view.getPrivateKeyTextField().getText());
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
                    showError("Không thể thực hiện đọc file");
                }
            }
        });

        // process for clear Text
        view.getClearTextBtn().addActionListener(e -> {
            view.getTextInput().setText("");
            view.getTextOutput().setText("");
            view.getPublicKeyTextField().setText("");
            view.getPrivateKeyTextField().setText("");
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
                String strPublicKey = view.getPublicKeyFileField().getText();

                if (source.isEmpty() || dest.isEmpty() || strPublicKey.isEmpty()) {
                    showError("Bạn hãy chọn File và nhập Public Key!");
                    return;
                }

                PublicKey publicKey = model.getPublicKeyFromString(strPublicKey);
                model.encryptFile(source, dest, publicKey);
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
                String strPrivateKey = view.getPrivateKeyFileField().getText();

                if (source.isEmpty() || dest.isEmpty() || strPrivateKey.isEmpty()) {
                    showError("Bạn hãy chọn File và nhập Private Key!");
                    return;
                }

                PrivateKey privateKey = model.getPrivateKeyFromString(strPrivateKey);
                model.decryptFile(source, dest, privateKey);
                JOptionPane.showMessageDialog(null, "Giải mã File thành công!");
            } catch (Exception ex) {
                showError("Lỗi giải mã File: Key của bạn bị sai hoặc dữ liệu bị hỏng!");
            }
        });

        // process for genKey file
        view.getGenKeyFileBtn().addActionListener(e -> {
            try {
                int keySize = Integer.parseInt((String) view.getKeySizeComboBox().getSelectedItem());
                KeyPair keyPair = model.generateKeyPair(keySize);

                view.getPublicKeyFileField().setText(model.keyToString(keyPair.getPublic()));
                view.getPrivateKeyFileField().setText(model.keyToString(keyPair.getPrivate()));
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
                    String publicKey = reader.readLine();
                    String privateKey = reader.readLine();
                    reader.close();

                    if (publicKey != null) view.getPublicKeyFileField().setText(publicKey);
                    if (privateKey != null) view.getPrivateKeyFileField().setText(privateKey);
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
                    writer.write(view.getPublicKeyFileField().getText() + "\n");
                    if (!view.getPrivateKeyFileField().getText().isEmpty()) {
                        writer.write(view.getPrivateKeyFileField().getText());
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
            view.getPublicKeyFileField().setText("");
            view.getPrivateKeyFileField().setText("");
        });
    }

    private void showError(String mesage) {
        JOptionPane.showMessageDialog(null, mesage);
    }
}