package vn.edu.hcmuaf.fit.View;

import javax.swing.*;
import java.awt.*;

public class HashPanel extends JPanel {

    private JComboBox<String> algorithmComboBox;
    private JTextArea textInput, textOutput;
    private JTextField sourceFile, destFile;
    private JButton hashTextBtn, loadTextFileBtn, clearTextBtn;
    private JButton browseSourceBtn, browseDestBtn;
    private JButton hashFileBtn, clearFileBtn;
    private JPanel topPanel, subTopTitlePanel, subTopOptionPanel;
    private JLabel titleLabel, algorithmLabel;

    public HashPanel() {
        setLayout(new BorderLayout());

        // process for top panel
        topPanel = new JPanel(new BorderLayout(10, 10));
        subTopTitlePanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("Hash Algorithms", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        subTopTitlePanel.add(titleLabel, BorderLayout.NORTH);

        subTopOptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        algorithmLabel = new JLabel("Algorithm:");
        subTopOptionPanel.add(algorithmLabel);

        String[] algorithms = {"MD2", "MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "SHA-512/224", "SHA-512/256", "SHA3-224", "SHA3-256", "SHA3-384", "SHA3-512"};
        algorithmComboBox = new JComboBox<>(algorithms);
        subTopOptionPanel.add(algorithmComboBox);

        topPanel.add(subTopTitlePanel, BorderLayout.NORTH);
        topPanel.add(subTopOptionPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // process for tab text and file
        JTabbedPane tabbedPane = new JTabbedPane();

        // for text tab
        JPanel textTab = new JPanel(new BorderLayout(10, 10));

        JPanel textCenterPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        textInput = new JTextArea();
        textInput.setLineWrap(true);
        textInput.setBorder(BorderFactory.createTitledBorder("Input Text"));

        textOutput = new JTextArea();
        textOutput.setLineWrap(true);
        textOutput.setBorder(BorderFactory.createTitledBorder("Output Hash (Hex)"));
        textOutput.setEditable(false);

        textCenterPanel.add(new JScrollPane(textInput));
        textCenterPanel.add(new JScrollPane(textOutput));
        textTab.add(textCenterPanel, BorderLayout.CENTER);

        JPanel textBottomPanel = new JPanel(new BorderLayout(10, 10));
        JPanel textActionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        hashTextBtn = new JButton("Hash Text");
        loadTextFileBtn = new JButton("Load File .txt");
        clearTextBtn = new JButton("Clear");

        textActionPanel.add(hashTextBtn);
        textActionPanel.add(loadTextFileBtn);
        textActionPanel.add(clearTextBtn);

        textBottomPanel.add(textActionPanel, BorderLayout.CENTER);
        textTab.add(textBottomPanel, BorderLayout.SOUTH);

        // for file tab
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
        pDest.add(new JLabel("Destination File (Save Hash to):"));
        destFile = new JTextField(30);
        destFile.setEditable(false);
        pDest.add(destFile);
        browseDestBtn = new JButton("Browse");
        pDest.add(browseDestBtn);

        fileCenterPanel.add(pSource);
        fileCenterPanel.add(pDest);
        fileTab.add(fileCenterPanel, BorderLayout.CENTER);

        JPanel fileBottomPanel = new JPanel(new BorderLayout(10, 10));
        JPanel fileActionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        hashFileBtn = new JButton("Hash File");
        clearFileBtn = new JButton("Clear");

        fileActionPanel.add(hashFileBtn);
        fileActionPanel.add(clearFileBtn);

        fileBottomPanel.add(fileActionPanel, BorderLayout.CENTER);
        fileTab.add(fileBottomPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Text", textTab);
        tabbedPane.addTab("File", fileTab);
        add(tabbedPane, BorderLayout.CENTER);
    }

    public JComboBox<String> getAlgorithmComboBox() {
        return algorithmComboBox;
    }

    public JTextArea getTextInput() {
        return textInput;
    }

    public JTextArea getTextOutput() {
        return textOutput;
    }

    public JTextField getSourceFile() {
        return sourceFile;
    }

    public JTextField getDestFile() {
        return destFile;
    }

    public JButton getHashTextBtn() {
        return hashTextBtn;
    }

    public JButton getLoadTextFileBtn() {
        return loadTextFileBtn;
    }

    public JButton getClearTextBtn() {
        return clearTextBtn;
    }

    public JButton getBrowseSourceBtn() {
        return browseSourceBtn;
    }

    public JButton getBrowseDestBtn() {
        return browseDestBtn;
    }

    public JButton getHashFileBtn() {
        return hashFileBtn;
    }

    public JButton getClearFileBtn() {
        return clearFileBtn;
    }
}