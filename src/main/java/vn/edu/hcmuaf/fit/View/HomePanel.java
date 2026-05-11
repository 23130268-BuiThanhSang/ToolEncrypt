package vn.edu.hcmuaf.fit.View;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(){
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Crypto Tool - 23130268_Bùi Thanh Sang",SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);
    }
}
