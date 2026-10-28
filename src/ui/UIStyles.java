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

    //method to style Dropdowns
    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
        comboBox.setPreferredSize(new Dimension(220, 38));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(15, 23, 42));
    }

    //method to style the help buttons
    public static void styleHelpButton(JButton button) {
        button.setPreferredSize(new Dimension(28, 28));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setMargin(new Insets(0, 0, 0, 0));
    }
}
