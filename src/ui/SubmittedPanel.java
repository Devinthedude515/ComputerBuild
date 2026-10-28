package ui;
import javax.swing.*;
import java.awt.*;

public class SubmittedPanel extends JPanel {

    //create constructor using Runnable callbacks for button actions
    public SubmittedPanel(Runnable onHome) {
        
        //set the GridBagLayout and background
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 23, 42));

        //create GBC object for layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Set the title, forground, and font
        JLabel title = new JLabel("Build Submitted!", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        
        //set a new inset for the title for spacing
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 30, 10);
        add(title, gbc);

        //create buttons for the main actions
        JButton homeButton = new JButton("Return to Home");
        homeButton.addActionListener(e -> onHome.run());
        gbc.gridy = 1;
        add(homeButton, gbc);

        //style the buttons using the UIStyles class
        UIStyles.styleMenuButton(homeButton);
    }
}