package vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical;

import javax.swing.*;
import java.awt.*;

public class VigenerePanel extends JPanel {
    private JTextArea input, output;
    private JTextField keyField;
    private JButton encryptButton, decryptButton, loadFileButton, clearButton , genKeyButton, importKeyButton, exportKeyButton;

    public VigenerePanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("VIGENÈRE CIPHER", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        input = new JTextArea();
        input.setLineWrap(true);
        input.setBorder(BorderFactory.createTitledBorder("Input"));

        output = new JTextArea();
        output.setLineWrap(true);
        output.setEditable(false);
        output.setBorder(BorderFactory.createTitledBorder("Output"));

        centerPanel.add(new JScrollPane(input));
        centerPanel.add(new JScrollPane(output));
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        // process for key
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyPanel.add(new JLabel("Key:"));
        keyField = new JTextField(15);
        keyPanel.add(keyField);

        genKeyButton = new JButton("Generate Key");
        importKeyButton = new JButton("Import Key");
        exportKeyButton = new JButton("Export Key");

        keyPanel.add(genKeyButton);
        keyPanel.add(importKeyButton);
        keyPanel.add(exportKeyButton);

        // process for button in panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        loadFileButton = new JButton("Load File .txt" );
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
    public JTextField getKeyField() {
        return keyField;
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