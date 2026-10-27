package ui;
import java.awt.*;
import javax.swing.*;

public class UIStyles {
    
    //create method to style the buttons
    //styling for the buttons
    public static void styleMenuButton(JButton button) {
        //set the dimensions
        button.setPreferredSize(new Dimension(240, 50));
        
        //set the Font
        button.setFont(new Font("SansSerif", Font.BOLD, 16));

        //set the background and Foreground colors
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
    }

    //method to style the labels
    public static void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
    }
}
