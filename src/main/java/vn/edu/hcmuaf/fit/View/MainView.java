package vn.edu.hcmuaf.fit.View;

import vn.edu.hcmuaf.fit.Controller.*;
import vn.edu.hcmuaf.fit.View.ViewAlgorithmSymmetric.AESPanel;
import vn.edu.hcmuaf.fit.View.ViewAlgorithmSymmetric.DESPanel;
import vn.edu.hcmuaf.fit.View.ViewAlgrorithmClassical.*;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JPanel cards;
    private JMenuBar menubar;
    private JMenu menu, exitMenu;
    private JMenuItem classicalItem, symmetricsItem, asymmetricsItem, hashItem, exitItem,homeItem;

    private ClassicalPanel classicalPanel;
    private SymmetricsPanel symmetricsPanel;
    private ASymmetricsPanel aSymmetricsPanel;
    private HashPanel hashPanel;


    public MainView() throws HeadlessException {
        setTitle("CryptoTool");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // menu for select model
        menubar = new JMenuBar();
        menu = new JMenu("Mode Crypto");
        exitMenu = new JMenu("Exit");
        homeItem = new JMenuItem("Home");
        classicalItem = new JMenuItem("Classical");
        symmetricsItem = new JMenuItem("Symmetrics");
        asymmetricsItem = new JMenuItem("Asymmetrics");
        hashItem = new JMenuItem("Hash");
        exitItem = new JMenuItem("comfirm exit");
        menu.add(homeItem);
        menu.add(classicalItem);
        menu.add(symmetricsItem);
        menu.add(asymmetricsItem);
        menu.add(hashItem);
        exitMenu.add(exitItem);
        menubar.add(exitMenu);
        menubar.add(menu);
        setJMenuBar(menubar);

        // create class instance for panel view
        classicalPanel = new ClassicalPanel();
        symmetricsPanel = new SymmetricsPanel();
        aSymmetricsPanel = new ASymmetricsPanel();
        hashPanel = new HashPanel();






        // cards for algrothim
        cards = new JPanel();
        cards.setLayout(new CardLayout());
        cards.add(new HomePanel(), "Home");
        cards.add(classicalPanel, "Classical Algorithm");
        cards.add(symmetricsPanel, "Symmetrics Algorithm");
        cards.add(aSymmetricsPanel, "ASymmetrics Algorithm");
        cards.add(hashPanel, "Hash Algorithm");
        add(cards);
        // add actionlistenner for menu itemm
        homeItem.addActionListener(e->switchCard("Home"));
        classicalItem.addActionListener(e->switchCard("Classical Algorithm"));
        symmetricsItem.addActionListener(e->switchCard("Symmetrics Algorithm"));
        asymmetricsItem.addActionListener(e->switchCard("ASymmetrics Algorithm"));
        hashItem.addActionListener(e->switchCard("Hash Algorithm"));
        // add actionlistenner for exit item
        exitItem.addActionListener(e->System.exit(0));


        // connect View to controller for all

        /**
         * - connect view to controller for classical algorithm
         */
        // classical algorithm
        CaesarPanel caesarPanel = classicalPanel.getCaesarPanel();
        CaesarController caesarController = new CaesarController(caesarPanel, classicalPanel);
        AffinePanel affinePannel = classicalPanel.getAffinePanel();
        AffineController affineController = new AffineController(affinePannel, classicalPanel);
        VigenerePanel vigenerePanel = classicalPanel.getVigenerePanel();
        VigenereController vigenereController = new VigenereController(vigenerePanel,classicalPanel);
        SubstitutionPanel substitutionPanel = classicalPanel.getSubstitutionPanel();
        SubstitutionController substitutionController = new SubstitutionController(substitutionPanel, classicalPanel);
        HillPanel hillPanel = classicalPanel.getHillPanel();
        HillController hillController = new HillController(hillPanel, classicalPanel);
        TranspositionPanel transpositionPanel = classicalPanel.getTranspositionPanel();
        TranspositionController transpositionController = new TranspositionController(transpositionPanel, classicalPanel);
        // modern symmetrics algorithm
        AESPanel aesPanel = symmetricsPanel.getAesPanel();
        AESController aesController = new AESController(aesPanel);
        DESPanel desPanel = symmetricsPanel.getDesPanel();
        DESController desController = new DESController(desPanel);


        setVisible(true);
    }

    private void switchCard(String nameItem) {
        CardLayout temp = (CardLayout) cards.getLayout();
        temp.show(cards, nameItem);
    }

    public static void main(String[] args) {
        new MainView();
    }
}
