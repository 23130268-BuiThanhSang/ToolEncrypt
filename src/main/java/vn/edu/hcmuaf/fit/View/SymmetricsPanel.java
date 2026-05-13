package vn.edu.hcmuaf.fit.View;

import vn.edu.hcmuaf.fit.View.ViewAlgorithmSymmetric.AESPanel;
import vn.edu.hcmuaf.fit.View.ViewAlgorithmSymmetric.DESPanel;

import javax.swing.*;
import java.awt.*;

public class SymmetricsPanel extends JPanel {
    private JComboBox<String> algorithmComboBox;
    private JPanel topPanel, subTopTitlePanel, subTopChooseAlgorithmPanel, contentPanel;
    private CardLayout contentLayout;


    // create instance for panel
    private AESPanel aesPanel;
    private DESPanel desPanel;

    public SymmetricsPanel() {
        setLayout(new BorderLayout());
        topPanel = new JPanel(new GridLayout(2, 1));

        // process for title
        subTopTitlePanel = new JPanel(new BorderLayout());
        JLabel labelName = new JLabel("Modern Symmetrics Algorithm", SwingConstants.CENTER);
        labelName.setFont(new Font("Arial", Font.BOLD, 16));
        subTopTitlePanel.add(labelName, BorderLayout.CENTER);
        topPanel.add(subTopTitlePanel);

        // process for choose algorithm
        subTopChooseAlgorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        subTopChooseAlgorithmPanel.add(new JLabel("Algorithm:"));
        String[] algorithms = {"AES","DES","Blowfish","RC2"};
        algorithmComboBox = new JComboBox<>(algorithms);
        subTopChooseAlgorithmPanel.add(algorithmComboBox);

        topPanel.add(subTopChooseAlgorithmPanel);
        add(topPanel, BorderLayout.NORTH);

        // process for content panel
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);

        aesPanel = new AESPanel();
        desPanel = new DESPanel();
        contentPanel.add(aesPanel, "AES");
        contentPanel.add(desPanel, "DES");

        add(contentPanel, BorderLayout.CENTER);

        //proceess switch panel when select algorithm
        algorithmComboBox.addActionListener(e -> {
            String selected = (String) algorithmComboBox.getSelectedItem();
            contentLayout.show(contentPanel, selected);
        });
    }

    public AESPanel getAesPanel() {
        return aesPanel;
    }
    public DESPanel getDesPanel() {
        return desPanel;
    }
}