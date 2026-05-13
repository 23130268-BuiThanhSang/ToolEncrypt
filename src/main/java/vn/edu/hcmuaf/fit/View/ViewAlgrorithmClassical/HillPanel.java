package vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical;

import javax.swing.*;
import java.awt.*;

public class HillPanel extends JPanel {
    private JTextArea input,output;
    private JTextField key;
    private JButton encryptButton, decryptButton, loadFileButton, clearButton, genKeyButton, importKeyButton, exportKeyButton;
    private JLabel title;
    private JScrollPane inputScroll, outputScroll;

    public HillPanel() {
        setLayout(new BorderLayout(10,10));
        // title
        title = new JLabel("HILL CIPHER", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title,BorderLayout.NORTH);
        //content include input
        JPanel centerPanel = new JPanel(new GridLayout(2,1,10,10));
        input = new JTextArea();
        input.setLineWrap(true);
        input.setWrapStyleWord(false);
        input.setBorder(BorderFactory.createTitledBorder("Input"));
        inputScroll = new JScrollPane(input);
        inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        output = new JTextArea();
        output.setLineWrap(true);
        output.setWrapStyleWord(false);
        output.setBorder(BorderFactory.createTitledBorder("Output"));
        // not edit but user can coppy-BuiThanhSang writting not AI please. all comment in this project author is bui thanh sang never AI
        output.setEditable(false);
        outputScroll = new JScrollPane(output);
        outputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        centerPanel.add(inputScroll);
        centerPanel.add(outputScroll);
        add(centerPanel,BorderLayout.CENTER);

        // for all button in panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10,10));

        // pannel for show key and user can generate key
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyPanel.add(new JLabel("key matrix 2x2: x1-x2-y1-y2 :"));
        key = new JTextField(15);
        keyPanel.add(key);
        genKeyButton = new JButton("Generate Key");
        importKeyButton = new JButton("Import Key");
        exportKeyButton = new JButton("Export Key");

        keyPanel.add(genKeyButton);
        keyPanel.add(importKeyButton);
        keyPanel.add(exportKeyButton);

        // pannel for button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        loadFileButton = new JButton("Load File .txt");
        clearButton = new JButton("Clear");
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(loadFileButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(keyPanel,BorderLayout.NORTH);
        bottomPanel.add(buttonPanel,BorderLayout.CENTER);

        add(bottomPanel,BorderLayout.SOUTH);
    }

    // process getter for controller call and set data to view
    public JTextArea getInput() {
        return input;
    }
    public JTextArea getOutput() {
        return output;
    }
    public JTextField getKey() {
        return key;
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