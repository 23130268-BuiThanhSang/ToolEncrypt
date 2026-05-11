package vn.edu.hcmuaf.fit.View;

import vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical.AffinePanel;
import vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical.CaesarPanel;
import vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical.VigenerePanel;

import javax.swing.*;
import java.awt.*;

public class ClassicalPanel extends JPanel {
    private JComboBox<String> algorithmComboBox;
    private JPanel subPannell,topPannel;
    private CardLayout contentLayout;
    private JLabel labelname;

    // create insstance for panel. to connect controller
    private CaesarPanel caesarPanel;
    private AffinePanel affinePanel;
    private VigenerePanel vigenerePanel;

    public ClassicalPanel() {
        setLayout(new BorderLayout());
        topPannel = new JPanel();
        topPannel.setLayout(new GridLayout(1,2));

        labelname= new JLabel("Classical Algorithm ", SwingConstants.CENTER);
        labelname.setFont(new Font("Arial", Font.BOLD, 16));
        topPannel.add(labelname);
        String [] algrorithmClassical = {"Caesar", "Affine","Vigenere"};
        algorithmComboBox = new JComboBox<>(algrorithmClassical);
        topPannel.add(algorithmComboBox);
        add(topPannel, BorderLayout.NORTH);
        contentLayout = new CardLayout();
        subPannell = new JPanel(contentLayout);

        // new panel instance
        caesarPanel = new CaesarPanel();
        affinePanel = new AffinePanel();
        vigenerePanel = new VigenerePanel();


        subPannell.add(caesarPanel, "Caesar");
        subPannell.add(affinePanel, "Affine");
        subPannell.add(vigenerePanel, "Vigenere");




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
}
