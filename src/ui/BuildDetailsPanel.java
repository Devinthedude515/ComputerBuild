package ui;

import java.awt.*;
import javax.swing.*;

public class BuildDetailsPanel extends JPanel {
    
    //create private Variables to reflect user input
    private JLabel nameLabel, buildNameLabel, budgetLabel, targetYearLabel, gamingGenreLabel, screenResolutionLabel, frameRateLabel;

    //create constructor
    public BuildDetailsPanel(Runnable onBack) {

        //set the layout and background
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 23, 42));

        //create gbc object to organize the layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.anchor = GridBagConstraints.WEST;

        //create the title and add to the panel
        JLabel title = new JLabel("Build Specifications");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        add(title, gbc);

        //reset the width
        gbc.gridwidth = 1;

        //create the labels
        buildNameLabel = createLabel();
        nameLabel = createLabel();
        budgetLabel = createLabel();
        targetYearLabel = createLabel();
        gamingGenreLabel = createLabel();
        screenResolutionLabel = createLabel();
        frameRateLabel = createLabel();

        //add each label
        addRow("Build Name:", buildNameLabel, gbc);
        addRow("Created by:", nameLabel, gbc);
        addRow("Budget:", budgetLabel, gbc);
        addRow("Target Year:", targetYearLabel, gbc);
        addRow("Gaming Genre:", gamingGenreLabel, gbc);
        addRow("Resolution:", screenResolutionLabel, gbc);
        addRow("Frame Rate:", frameRateLabel, gbc);

        ///create the back button
        JButton backButton = new JButton("Back to Browse");
        UIStyles.styleMenuButton(backButton);
        backButton.addActionListener(e -> onBack.run());

        //place the back button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);

        // Initial placeholder content
        showBuild("Sample Build", "Sample User");
    }

    //method to create the labels
     private JLabel createLabel() {
        
        JLabel label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return label;
    }

    //method to add the labels to a row
    private void addRow(String labelText, JLabel valueLabel, GridBagConstraints gbc) {
        
        //positons the label
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;

        //creates and adds the label
        JLabel label = new JLabel(labelText);
        UIStyles.styleLabel(label);
        add(label, gbc);

        //adds the value of that label
        gbc.gridx = 1;
        add(valueLabel, gbc);
    }

    //method to show the Build once opened
    public void showBuild(String buildName, String creator) {
        buildNameLabel.setText(buildName);
        nameLabel.setText(creator);

        // Temporary sample specifications
        budgetLabel.setText("$1,500");
        targetYearLabel.setText("2026");
        gamingGenreLabel.setText("RPG");
        screenResolutionLabel.setText("QHD 1440p");
        frameRateLabel.setText("144 FPS");
    }
}
