package ui;
import java.awt.*;
import javax.swing.*;

public class HomePanel extends JPanel {
    
    //create constructor using Runnable callbacks for button actions
    public HomePanel(Runnable onCreateBuild, Runnable onBrowseBuild, Runnable onEditBuild, Runnable onExit) {

        //set the GridBagLayout and background
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 23, 42));

        //create GBC object for layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Set the title, forground, and font
        JLabel title = new JLabel("PCbuilder Unlimited", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        
        //set a new inset for the title for spacing
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 30, 10);
        add(title, gbc);

        //create buttons for the main actions
        JButton createBuildButton = new JButton("Create Build");
        createBuildButton.addActionListener(e -> onCreateBuild.run());
        gbc.gridy = 1;
        add(createBuildButton, gbc);

        JButton browseBuildButton = new JButton("Browse Builds");
        browseBuildButton.addActionListener(e -> onBrowseBuild.run());
        gbc.gridy = 2;
        add(browseBuildButton, gbc);

        JButton editBuildButton = new JButton("Edit Build");
        editBuildButton.addActionListener(e -> onEditBuild.run());
        gbc.gridy = 3;
        add(editBuildButton, gbc);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> onExit.run());
        gbc.gridy = 4;
        add(exitButton, gbc);
    }

    //create private createMenuButton
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);

        //set common button properties
        button.setPreferredSize(new Dimension(240, 50));
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        return button;
    }
}
