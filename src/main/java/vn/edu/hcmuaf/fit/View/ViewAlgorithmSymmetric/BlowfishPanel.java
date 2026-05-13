package vn.edu.hcmuaf.fit.View.ViewAlgorithmSymmetric;

import javax.swing.*;
import java.awt.*;

public class BlowfishPanel extends JPanel {

    private JComboBox<String> modeComboBox, paddingComboBox, keySizeComboBox;
    private JTextArea textInput, textOutput;
    private JTextField textKey, textIv, sourceFile, destFile, fileKey, fileIv;
    private JButton genKeyTextBtn, importKeyTextBtn, exportKeyTextBtn,encryptTextBtn, decryptTextBtn, loadTextFileBtn, clearTextBtn;
    private JButton browseSourceBtn, browseDestBtn;
    private JButton encryptFileBtn, decryptFileBtn, clearFileBtn;
    private JButton genKeyFileBtn, importKeyFileBtn, exportKeyFileBtn;

    public BlowfishPanel() {
        setLayout(new BorderLayout(10, 10));

        // process choose mode, padđing, keysize
        JPanel configPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        configPanel.setBorder(BorderFactory.createTitledBorder("Blowfish Configuration"));
        configPanel.add(new JLabel("Mode:"));
        modeComboBox = new JComboBox<>(new String[]{"ECB", "CBC", "CFB", "OFB"});
        configPanel.add(modeComboBox);

        configPanel.add(new JLabel("Padding:"));
        paddingComboBox = new JComboBox<>(new String[]{"PKCS5Padding", "ISO10126Padding"});
        configPanel.add(paddingComboBox);


        configPanel.add(new JLabel("Key Size:"));
        keySizeComboBox = new JComboBox<>(new String[]{"128", "256", "448"});
        configPanel.add(keySizeComboBox);

        add(configPanel, BorderLayout.NORTH);

        //process forr tab text and file use tabpane
        JTabbedPane tabbedPane = new JTabbedPane();


        // for text
        JPanel textTab = new JPanel(new BorderLayout(10, 10));

        JPanel textCenterPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        textInput = new JTextArea();
        textInput.setLineWrap(true);
        textInput.setBorder(BorderFactory.createTitledBorder("Input Text"));

        textOutput = new JTextArea();
        textOutput.setLineWrap(true);
        textOutput.setBorder(BorderFactory.createTitledBorder("Output Text (Base64)"));
        textOutput.setEditable(false);

        textCenterPanel.add(new JScrollPane(textInput));
        textCenterPanel.add(new JScrollPane(textOutput));
        textTab.add(textCenterPanel, BorderLayout.CENTER);

        JPanel textBottomPanel = new JPanel(new BorderLayout(10, 10));
        JPanel textKeyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textKeyPanel.add(new JLabel("Key:"));
        textKey = new JTextField(15);
        textKeyPanel.add(textKey);
        textKeyPanel.add(new JLabel("IV:"));
        textIv = new JTextField(10);
        textKeyPanel.add(textIv);
        genKeyTextBtn = new JButton("Generate Key");
        importKeyTextBtn = new JButton("Import Key");
        exportKeyTextBtn = new JButton("Export Key");
        textKeyPanel.add(genKeyTextBtn);
        textKeyPanel.add(importKeyTextBtn);
        textKeyPanel.add(exportKeyTextBtn);

        JPanel textActionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        encryptTextBtn = new JButton("Encrypt Text");
        decryptTextBtn = new JButton("Decrypt Text");
        loadTextFileBtn = new JButton("Load File .txt");
        clearTextBtn = new JButton("Clear");
        textActionPanel.add(encryptTextBtn);
        textActionPanel.add(decryptTextBtn);
        textActionPanel.add(loadTextFileBtn);
        textActionPanel.add(clearTextBtn);

        textBottomPanel.add(textKeyPanel, BorderLayout.NORTH);
        textBottomPanel.add(textActionPanel, BorderLayout.CENTER);
        textTab.add(textBottomPanel, BorderLayout.SOUTH);

        // file tab
        JPanel fileTab = new JPanel(new BorderLayout(10, 10));

        JPanel fileCenterPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JPanel pSource = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pSource.add(new JLabel("Source File:"));
        sourceFile = new JTextField(30);
        sourceFile.setEditable(false);
        pSource.add(sourceFile);
        browseSourceBtn = new JButton("Browse");
        pSource.add(browseSourceBtn);

        JPanel pDest = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pDest.add(new JLabel("Destination File:"));
        destFile = new JTextField(30);
        destFile.setEditable(false);
        pDest.add(destFile);
        browseDestBtn = new JButton("Browse");
        pDest.add(browseDestBtn);

        fileCenterPanel.add(pSource);
        fileCenterPanel.add(pDest);
        fileTab.add(fileCenterPanel, BorderLayout.CENTER);

        JPanel fileBottomPanel = new JPanel(new BorderLayout(10, 10));
        JPanel fileKeyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fileKeyPanel.add(new JLabel("Key:"));
        fileKey = new JTextField(15);
        fileKeyPanel.add(fileKey);
        fileKeyPanel.add(new JLabel("IV:"));
        fileIv = new JTextField(10);
        fileKeyPanel.add(fileIv);
        genKeyFileBtn = new JButton("Generate Key");
        importKeyFileBtn = new JButton("Import Key");
        exportKeyFileBtn = new JButton("Export Key");
        fileKeyPanel.add(genKeyFileBtn);
        fileKeyPanel.add(importKeyFileBtn);
        fileKeyPanel.add(exportKeyFileBtn);

        JPanel fileActionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        encryptFileBtn = new JButton("Encrypt File");
        decryptFileBtn = new JButton("Decrypt File");
        clearFileBtn = new JButton("Clear");
        fileActionPanel.add(encryptFileBtn);
        fileActionPanel.add(decryptFileBtn);
        fileActionPanel.add(clearFileBtn);

        fileBottomPanel.add(fileKeyPanel, BorderLayout.NORTH);
        fileBottomPanel.add(fileActionPanel, BorderLayout.CENTER);
        fileTab.add(fileBottomPanel, BorderLayout.SOUTH);

        // add tab file and text
        tabbedPane.addTab("Text", textTab);
        tabbedPane.addTab("File", fileTab);
        add(tabbedPane, BorderLayout.CENTER);

        // process update state for IV because ECB not use IV
        modeComboBox.addActionListener(e -> {
            boolean isNotECB = !"ECB".equals(modeComboBox.getSelectedItem());
            textIv.setEnabled(isNotECB);
            fileIv.setEnabled(isNotECB);
        });
        textIv.setEnabled(false);
        fileIv.setEnabled(false);
    }


    public JComboBox<String> getModeComboBox() {
        return modeComboBox;
    }
    public JComboBox<String> getPaddingComboBox() {
        return paddingComboBox;
    }
    public JComboBox<String> getKeySizeComboBox() {
        return keySizeComboBox;
    }
    public JTextArea getTextInput() {
        return textInput;
    }
    public JTextArea getTextOutput() {
        return textOutput;
    }
    public JTextField getTextKey() {
        return textKey;
    }
    public JTextField getTextIv() {
        return textIv;
    }
    public JButton getEncryptTextBtn() {
        return encryptTextBtn;
    }
    public JButton getDecryptTextBtn() {
        return decryptTextBtn;
    }
    public JButton getLoadTextFileBtn() {
        return loadTextFileBtn;
    }
    public JButton getClearTextBtn() {
        return clearTextBtn;
    }
    public JButton getGenKeyTextBtn() {
        return genKeyTextBtn;
    }
    public JButton getImportKeyTextBtn() {
        return importKeyTextBtn;
    }
    public JButton getExportKeyTextBtn() {
        return exportKeyTextBtn;
    }
    public JTextField getSourceFile() {
        return sourceFile;
    }
    public JTextField getDestFile() {
        return destFile;
    }
    public JTextField getFileKey() {
        return fileKey;
    }
    public JTextField getFileIv() {
        return fileIv;
    }
    public JButton getBrowseSourceBtn() {
        return browseSourceBtn;
    }
    public JButton getBrowseDestBtn() {
        return browseDestBtn;
    }
    public JButton getEncryptFileBtn() {
        return encryptFileBtn;
    }
    public JButton getDecryptFileBtn() {
        return decryptFileBtn;
    }
    public JButton getClearFileBtn() {
        return clearFileBtn;
    }
    public JButton getGenKeyFileBtn() {
        return genKeyFileBtn;
    }
    public JButton getImportKeyFileBtn() {
        return importKeyFileBtn;
    }
    public JButton getExportKeyFileBtn() {
        return exportKeyFileBtn;
    }
}