package vn.edu.hcmuaf.fit.View;

import vn.edu.hcmuaf.fit.View.ViewAlgorithmSymmetric.*;

import javax.swing.*;
import java.awt.*;

public class SymmetricsPanel extends JPanel {
    private JComboBox<String> algorithmComboBox;
    private JPanel topPanel, subTopTitlePanel, subTopChooseAlgorithmPanel, contentPanel;
    private CardLayout contentLayout;


    // create instance for panel
    private AESPanel aesPanel;
    private DESPanel desPanel;
    private BlowfishPanel blowfishPanel;
    private RC2Panel rc2Panel;
    private DESedePanel DESedePanel;
    private SerpentPanel serpentPanel;
    private RC6Panel rc6Panel;

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
        String[] algorithms = {"AES","DES","Blowfish","RC2","DESede","Serpent","RC6"};
        algorithmComboBox = new JComboBox<>(algorithms);
        subTopChooseAlgorithmPanel.add(algorithmComboBox);

        topPanel.add(subTopChooseAlgorithmPanel);
        add(topPanel, BorderLayout.NORTH);

        // process for content panel
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);

        aesPanel = new AESPanel();
        desPanel = new DESPanel();
        blowfishPanel = new BlowfishPanel();
        rc2Panel = new RC2Panel();
        DESedePanel = new DESedePanel();
        serpentPanel = new SerpentPanel();
        rc6Panel = new RC6Panel();
        contentPanel.add(aesPanel, "AES");
        contentPanel.add(desPanel, "DES");
        contentPanel.add(blowfishPanel, "Blowfish");
        contentPanel.add(rc2Panel, "RC2");
        contentPanel.add(DESedePanel, "DESede");
        contentPanel.add(serpentPanel, "Serpent");
        contentPanel.add(rc6Panel, "RC6");




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
    public  BlowfishPanel getBlowfishPanel() {
        return  blowfishPanel;
    }
    public RC2Panel getRc2Panel() {
        return rc2Panel;
    }
    public DESedePanel getTripleDESPanel() {
        return DESedePanel;
    }
    public SerpentPanel getSerpentPanel() {
        return serpentPanel;
    }
    public RC6Panel getRc6Panel() {
        return rc6Panel;
    }

}