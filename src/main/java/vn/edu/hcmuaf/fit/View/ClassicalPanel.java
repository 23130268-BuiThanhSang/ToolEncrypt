package vn.edu.hcmuaf.fit.View;

import vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical.*;

import javax.swing.*;
import java.awt.*;

public class ClassicalPanel extends JPanel {
    private JComboBox<String> algorithmComboBox, languageComboBox;
    private JPanel subPannell,topPannel,subTopTitlePanel,subTopChooseLanguagePanel,subTopChooseAlgorithmPanel;
    private CardLayout contentLayout;
    private JLabel labelname, labellanguage, labelalgorithm;

    // create insstance for panel. to connect controller
    private CaesarPanel caesarPanel;
    private AffinePanel affinePanel;
    private VigenerePanel vigenerePanel;
    private SubstitutionPanel substitutionPanel;
    private HillPanel hillPanel;
    private TranspositionPanel transpositionPanel;

    public ClassicalPanel() {
        setLayout(new BorderLayout());
        topPannel = new JPanel();
        topPannel.setLayout(new GridLayout(3,1));

        subTopTitlePanel = new JPanel(new BorderLayout());
        labelname= new JLabel("Classical Algorithm ", SwingConstants.CENTER);
        labelname.setFont(new Font("Arial", Font.BOLD, 16));
        subTopTitlePanel.add(labelname, BorderLayout.CENTER);
        topPannel.add(subTopTitlePanel);


        /**
         * this is process for layout chooser
         */
        // process for choose language
        subTopChooseLanguagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labellanguage = new JLabel("Choose language");
        String [] language = {"Vietnamese", "English"};
        languageComboBox = new JComboBox<>(language);
        subTopChooseLanguagePanel.add(labellanguage);
        subTopChooseLanguagePanel.add(languageComboBox);

        // process for choose algorithm
        subTopChooseAlgorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelalgorithm = new JLabel("Choose algorithm");
        String [] algrorithmClassical = {"Caesar", "Affine","Vigenere","Substitution","Hill","Transposition"};
        algorithmComboBox = new JComboBox<>(algrorithmClassical);
        subTopChooseAlgorithmPanel.add(labelalgorithm);
        subTopChooseAlgorithmPanel.add(algorithmComboBox);


        topPannel.add(subTopTitlePanel);
        topPannel.add(subTopChooseLanguagePanel);
        topPannel.add(subTopChooseAlgorithmPanel);
        add(topPannel, BorderLayout.NORTH);
        contentLayout = new CardLayout();
        subPannell = new JPanel(contentLayout);

        // new panel instance
        caesarPanel = new CaesarPanel();
        affinePanel = new AffinePanel();
        vigenerePanel = new VigenerePanel();
        substitutionPanel = new SubstitutionPanel();
        hillPanel = new HillPanel();
        transpositionPanel = new TranspositionPanel();


        subPannell.add(caesarPanel, "Caesar");
        subPannell.add(affinePanel, "Affine");
        subPannell.add(vigenerePanel, "Vigenere");
        subPannell.add(substitutionPanel, "Substitution");
        subPannell.add(hillPanel, "Hill");
        subPannell.add(transpositionPanel, "Transposition");


        add(subPannell,BorderLayout.CENTER);

        algorithmComboBox.addActionListener(e -> switchPanelAlgorithmClassical());
    }

    private void switchPanelAlgorithmClassical() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        contentLayout.show(subPannell, selectedAlgorithm);
    }
    public CaesarPanel getCaesarPanel(){
        return caesarPanel;
    }
    public String getSelectedLanguage() {
        return (String) languageComboBox.getSelectedItem();
    }
    public AffinePanel getAffinePanel() {
        return affinePanel;
    }
    public VigenerePanel getVigenerePanel() {
        return vigenerePanel;
    }
    public SubstitutionPanel getSubstitutionPanel() {
        return substitutionPanel;
    }
    public HillPanel getHillPanel() {
            return hillPanel;
    }
    public TranspositionPanel getTranspositionPanel() {
            return transpositionPanel;
    }
}
