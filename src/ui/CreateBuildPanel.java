package ui;
import java.awt.*;
import javax.swing.*;


public class CreateBuildPanel extends JPanel {
    
    //all labels like questions, instructions, and output should be in a JLabel object
    private JLabel nameLabel, buildNameLabel, budgetLabel, targetYearLabel, gamingGenreLabel, screenResolutionLabel, frameRateLabel;

    //text field to collect user input, it can be set to a certain number of columns, which is the width of the text field
    private JTextField nameInput, buildNameInput, budgetInput, targetYearInput, gamingGenreInput, screenResolutionInput, frameRateInput;

    //create constructor
    public CreateBuildPanel(Runnable onBack, Runnable onSubmit) {

        //initialize the labels and text fields
        //labels
        nameLabel = new JLabel("Name: ");
        buildNameLabel = new JLabel("PC Build Name: ");
        budgetLabel = new JLabel("Budget: ");
        targetYearLabel = new JLabel("Target Year: ");
        gamingGenreLabel = new JLabel("Gaming Genre: ");
        screenResolutionLabel = new JLabel("Screen Resolution: ");
        frameRateLabel = new JLabel("Frame Rate: ");

        //text fields
        nameInput = new JTextField(10);
        buildNameInput = new JTextField(10);
        budgetInput = new JTextField(10);
        targetYearInput = new JTextField(10);
        gamingGenreInput = new JTextField(10);
        screenResolutionInput = new JTextField(10);
        frameRateInput = new JTextField(10);

        //style the labels using the UIStyles class
        UIStyles.styleLabel(nameLabel);
        UIStyles.styleLabel(buildNameLabel);
        UIStyles.styleLabel(budgetLabel);
        UIStyles.styleLabel(targetYearLabel);
        UIStyles.styleLabel(gamingGenreLabel);
        UIStyles.styleLabel(screenResolutionLabel);
        UIStyles.styleLabel(frameRateLabel);
        
        //set the layout and background
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 23, 42));

        //set the constraints for the components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //create a title for the create build panel
        JLabel createBuildLabel = new JLabel("Step 1: Enter Specifications");

        //set the font and color for the title label
        createBuildLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        createBuildLabel.setForeground(Color.WHITE);

        //add the title label to the panel
        gbc.gridy = 0;
        add(createBuildLabel, gbc);

        //add components to the panel
        //Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameInput, gbc);

        //Build Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(buildNameLabel, gbc);
        gbc.gridx = 1;
        add(buildNameInput, gbc);

        //Budget
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(budgetLabel, gbc);
        gbc.gridx = 1;
        add(budgetInput, gbc);

        //Target Year
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(targetYearLabel, gbc);
        gbc.gridx = 1;
        add(targetYearInput, gbc);

        //Gaming Genre
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(gamingGenreLabel, gbc);
        gbc.gridx = 1;
        add(gamingGenreInput, gbc);

        //Screen Resolution
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(screenResolutionLabel, gbc);
        gbc.gridx = 1;
        add(screenResolutionInput, gbc);

        //Frame Rate
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(frameRateLabel, gbc);
        gbc.gridx = 1;
        add(frameRateInput, gbc);

        //add back button and submit button
        JButton backButton = new JButton("Back");
        JButton submitButton = new JButton("Submit");

        //create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.add(backButton, gbc);
        buttonPanel.add(submitButton, gbc);

        //add the button panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(buttonPanel, gbc);

        //create action listener for back button
        backButton.addActionListener(e -> onBack.run());

        //create action listener for submit button
        submitButton.addActionListener(e -> onSubmit.run());
    }
}
