package vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical;

import javax.swing.*;
import java.awt.*;
public class AffinePanel extends JPanel {
    private JTextArea input, output;
    private JTextField keyA, keyB;
    private JButton encryptButton, decryptButton,loadFileButton, clearButton,genKeyButton, importKeyButton, exportKeyButton;
    private JLabel title;

    public AffinePanel() {
        setLayout(new BorderLayout(10, 10));

       // process for title affine
        title = new JLabel("AFFINE CIPHER", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        add(title, BorderLayout.NORTH);

        // process for content include input and output
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        input = new JTextArea();
        input.setLineWrap(true);
        input.setWrapStyleWord(false);
        input.setBorder(BorderFactory.createTitledBorder("Input"));

        output = new JTextArea();
        output.setLineWrap(true);
        output.setWrapStyleWord(false);
        output.setEditable(false);
        output.setBorder(BorderFactory.createTitledBorder("Output"));

        centerPanel.add(new JScrollPane(input));
        centerPanel.add(new JScrollPane(output));

        add(centerPanel, BorderLayout.CENTER);

        // process for button in panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        // process for key
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        keyPanel.add(new JLabel("Key A:"));
        keyA = new JTextField(5);
        keyPanel.add(keyA);

        keyPanel.add(new JLabel("Key B:"));
        keyB = new JTextField(5);
        keyPanel.add(keyB);

        genKeyButton = new JButton("Generate Key");
        importKeyButton = new JButton("Import Key");
        exportKeyButton = new JButton("Export Key");

        keyPanel.add(genKeyButton);
        keyPanel.add(importKeyButton);
        keyPanel.add(exportKeyButton);

        // button choose encrypt or decrypt
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        loadFileButton = new JButton("Load File .txt");
        clearButton = new JButton("Clear");

        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(loadFileButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(keyPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
    }


    public JTextArea getInput() {
        return input;
    }

    public JTextArea getOutput() {
        return output;
    }


    public JTextField getKeyA() {
        return keyA;
    }

    public JTextField getKeyB() {
        return keyB;
    }

    public JButton getEncryptButton() {
        return encryptButton;
    }

    public JButton getDecryptButton() {
        return decryptButton;
    }

    public JButton getLoadFileButton() {
        return loadFileButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getGenKeyButton() {
        return genKeyButton;
    }

    public JButton getImportKeyButton() {
        return importKeyButton;
    }

    public JButton getExportKeyButton() {
        return exportKeyButton;
    }
}