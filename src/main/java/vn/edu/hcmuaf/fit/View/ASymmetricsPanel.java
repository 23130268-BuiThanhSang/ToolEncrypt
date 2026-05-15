package vn.edu.hcmuaf.fit.View;

import javax.swing.*;
import java.awt.*;

public class ASymmetricsPanel extends JPanel {

    private JComboBox<String> keySizeComboBox;
    private JTextField modeField, paddingField, algorithmfield;
    private JTextArea textInput, textOutput;
    private JTextField sourceFile, destFile;
    private JButton genKeyTextBtn, importKeyTextBtn, exportKeyTextBtn, encryptTextBtn, decryptTextBtn, loadTextFileBtn, clearTextBtn;
    private JButton browseSourceBtn, browseDestBtn;
    private JPanel topPanel,subTopTitlePanel,subTopOptionPanel;
    private JLabel titleLabel, algorithmLabel, modeLabel, paddingLabel, keySizeLabel;
    private JPanel publicKeyTextPanel, privateKeyTextPanel, actionKeyTextPanel;
    private JLabel publicKeyTextLabel, privateKeyTextLabel, actionKeyTextLabel;
    private JTextField publicKeyTextField, privateKeyTextField;
    private JPanel publicKeyFilePanel, privateKeyFilePanel, actionKeyFilePanel;
    private JLabel publicKeyFileLabel, privateKeyFileLabel, actionKeyFileLabel;
    private JTextField publicKeyFileField, privateKeyFileField;
    private JButton genKeyFileBtn, importKeyFileBtn, exportKeyFileBtn;
    private JButton encryptFileBtn, decryptFileBtn, clearFileBtn;

    public ASymmetricsPanel() {
        setLayout(new BorderLayout());
        topPanel = new JPanel(new BorderLayout(10,10));
        subTopTitlePanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("Asymmetric", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        subTopTitlePanel.add(titleLabel, BorderLayout.NORTH);
        subTopOptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        algorithmLabel = new JLabel("Algorithm:");
        subTopOptionPanel.add(algorithmLabel);
        algorithmfield = new JTextField("RSA",10);
        algorithmfield.setEditable(false);
        subTopOptionPanel.add(algorithmfield);
        modeLabel = new JLabel("Mode:");
        subTopOptionPanel.add(modeLabel);
        modeField = new JTextField("ECB",10);
        modeField.setEditable(false);
        subTopOptionPanel.add(modeField);
        paddingLabel = new JLabel("Padding:");
        subTopOptionPanel.add(paddingLabel);
        paddingField = new JTextField("PKCS1Padding",10);
        paddingField.setEditable(false);
        subTopOptionPanel.add(paddingField);
        keySizeLabel = new JLabel("Key Size:");
        subTopOptionPanel.add(keySizeLabel);
        keySizeComboBox = new JComboBox<>(new String[]{ "2048", "4096"});
        subTopOptionPanel.add(keySizeComboBox);
        topPanel.add(subTopTitlePanel,BorderLayout.NORTH);
        topPanel.add(subTopOptionPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);


        // process for tab text and file
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
        JPanel textKeyPanel = new JPanel(new GridLayout(3,1));
        publicKeyTextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        publicKeyTextLabel = new JLabel("PublicKey:");
        publicKeyTextField = new JTextField(30);
        publicKeyTextPanel.add(publicKeyTextLabel);
        publicKeyTextPanel.add(publicKeyTextField);
        privateKeyTextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        privateKeyTextLabel = new JLabel("PrivateKey:");
        privateKeyTextField = new JTextField(30);
        privateKeyTextPanel.add(privateKeyTextLabel);
        privateKeyTextPanel.add(privateKeyTextField);
        actionKeyTextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionKeyTextLabel = new JLabel("Action Key:");


        genKeyTextBtn = new JButton("Generate Key");
        importKeyTextBtn = new JButton("Import Key");
        exportKeyTextBtn = new JButton("Export Key");
        actionKeyTextPanel.add(actionKeyTextLabel);
        actionKeyTextPanel.add(genKeyTextBtn);
        actionKeyTextPanel.add(importKeyTextBtn);
        actionKeyTextPanel.add(exportKeyTextBtn);
        textKeyPanel.add(publicKeyTextPanel);
        textKeyPanel.add(privateKeyTextPanel);
        textKeyPanel.add(actionKeyTextPanel);


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
        JPanel fileKeyPanel = new JPanel(new GridLayout(3,1));
        publicKeyFilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        publicKeyFileLabel = new JLabel("PublicKey:");
        publicKeyFileField = new JTextField(30);
        publicKeyFilePanel.add(publicKeyFileLabel);
        publicKeyFilePanel.add(publicKeyFileField);
        privateKeyFilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        privateKeyFileLabel = new JLabel("PrivateKey:");
        privateKeyFileField = new JTextField(30);
        privateKeyFilePanel.add(privateKeyFileLabel);
        privateKeyFilePanel.add(privateKeyFileField);
        actionKeyFilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionKeyFileLabel = new JLabel("Action Key:");


        genKeyFileBtn = new JButton("Generate Key");
        importKeyFileBtn = new JButton("Import Key");
        exportKeyFileBtn = new JButton("Export Key");
        actionKeyFilePanel.add(actionKeyFileLabel);
        actionKeyFilePanel.add(genKeyFileBtn);
        actionKeyFilePanel.add(importKeyFileBtn);
        actionKeyFilePanel.add(exportKeyFileBtn);
        fileKeyPanel.add(publicKeyFilePanel);
        fileKeyPanel.add(privateKeyFilePanel);
        fileKeyPanel.add(actionKeyFilePanel);


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

        tabbedPane.add("Text",textTab);
        tabbedPane.add("File",fileTab);
        add(tabbedPane, BorderLayout.CENTER);
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
    public JTextField getPublicKeyTextField() {
        return publicKeyTextField;
    }
    public JTextField getPrivateKeyTextField() {
        return privateKeyTextField;
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
    public JTextField getSourceFile() {
        return sourceFile;
    }
    public JTextField getDestFile() {
        return destFile;
    }
    public JButton getBrowseSourceBtn() {
        return browseSourceBtn;
    }
    public JButton getBrowseDestBtn() {
        return browseDestBtn;
    }
    public JTextField getPublicKeyFileField() {
        return publicKeyFileField;
    }
    public JTextField getPrivateKeyFileField() {
        return privateKeyFileField;
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
    public JButton getEncryptFileBtn() {
        return encryptFileBtn;
    }
    public JButton getDecryptFileBtn() {
        return decryptFileBtn;
    }
    public JButton getClearFileBtn() {
        return clearFileBtn;
    }
}