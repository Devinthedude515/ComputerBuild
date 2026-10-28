package ui;
import java.awt.*;
import javax.swing.*;
import model.GamingGenre;
import model.ScreenResolution;
import model.FrameRate;


public class CreateBuildPanel extends JPanel {
    
    //all labels like questions, instructions, and output should be in a JLabel object
    private JLabel nameLabel, buildNameLabel, budgetLabel, targetYearLabel, gamingGenreLabel, screenResolutionLabel, frameRateLabel;

    //text field to collect user input, it can be set to a certain number of columns, which is the width of the text field
    private JTextField nameInput, buildNameInput, budgetInput, targetYearInput;

    //create ComboBox for gaming genre, screen resolution, and frame rate
    private JComboBox<GamingGenre> gamingGenreInput;
    private JComboBox<ScreenResolution> screenResolutionInput;
    private JComboBox<FrameRate> frameRateInput;

    //create constructor
    public CreateBuildPanel(Runnable onBack, Runnable onSubmit) {

        //test
        System.out.println("Updated CreateBuildPanel is running!");
        
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
        gamingGenreInput = new JComboBox<>(GamingGenre.values());
        screenResolutionInput = new JComboBox<>(ScreenResolution.values());
        frameRateInput = new JComboBox<>(FrameRate.values());

        //create help buttons 
        JButton nameHelp = createHelpButton(
            "Enter your name so you can identify who created this build."
        );

        JButton buildNameHelp = createHelpButton(
            "Give your PC build a memorable name so you can identify it later."
        );

        JButton budgetHelp = createHelpButton(
            "Enter the maximum amount you want to spend on your PC. " +
            "Consider whether your budget includes peripherals."
        );

        JButton targetYearHelp = createHelpButton(
            "Enter the year you want this PC to be suitable for. " +
            "Consider both current and future game requirements."
        );

        JButton genreHelp = createHelpButton(
            "Choose the genre you play most often. " +
            "Different game genres can place different demands on hardware."
        );

        JButton resolutionHelp = createHelpButton(
            "Choose your target screen resolution. " +
            "Higher resolutions generally require more graphics processing power."
        );

        JButton frameRateHelp = createHelpButton(
            "Choose your target frames per second (FPS). " +
            "Higher frame rates generally require more processing power."
        );

        //style the labels using the UIStyles class
        UIStyles.styleLabel(nameLabel);
        UIStyles.styleLabel(buildNameLabel);
        UIStyles.styleLabel(budgetLabel);
        UIStyles.styleLabel(targetYearLabel);
        UIStyles.styleLabel(gamingGenreLabel);
        UIStyles.styleLabel(screenResolutionLabel);
        UIStyles.styleLabel(frameRateLabel);

        //style the dropdowns using the UIStyles class
        UIStyles.styleComboBox(gamingGenreInput);
        UIStyles.styleComboBox(screenResolutionInput);
        UIStyles.styleComboBox(frameRateInput);

        //style the help buttons
        UIStyles.styleHelpButton(nameHelp);
        UIStyles.styleHelpButton(buildNameHelp);
        UIStyles.styleHelpButton(budgetHelp);
        UIStyles.styleHelpButton(targetYearHelp);
        UIStyles.styleHelpButton(genreHelp);
        UIStyles.styleHelpButton(resolutionHelp);
        UIStyles.styleHelpButton(frameRateHelp);
        
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
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        add(createBuildLabel, gbc);

        //reset gridwidth for the rest of the components
        gbc.gridwidth = 1;

        //add components to the panel
        //Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameInput, gbc);
        //add name help button
        gbc.gridx = 2;
        add(nameHelp, gbc);

        //Build Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(buildNameLabel, gbc);
        gbc.gridx = 1;
        add(buildNameInput, gbc);
        //add build name help button
        gbc.gridx = 2;
        add(buildNameHelp, gbc);

        //Budget
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(budgetLabel, gbc);
        gbc.gridx = 1;
        add(budgetInput, gbc);
        //add budget help button
        gbc.gridx = 2;
        add(budgetHelp, gbc);

        //Target Year
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(targetYearLabel, gbc);
        gbc.gridx = 1;
        add(targetYearInput, gbc);
        //add target year help button
        gbc.gridx = 2;
        add(targetYearHelp, gbc);

        //Gaming Genre
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(gamingGenreLabel, gbc);
        gbc.gridx = 1;
        add(gamingGenreInput, gbc);
        //add gaming genre help button
        gbc.gridx = 2;
        add(genreHelp, gbc);

        //Screen Resolution
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(screenResolutionLabel, gbc);
        gbc.gridx = 1;
        add(screenResolutionInput, gbc);
        //add screen resolution help button
        gbc.gridx = 2;
        add(resolutionHelp, gbc);

        //Frame Rate
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(frameRateLabel, gbc);
        gbc.gridx = 1;
        add(frameRateInput, gbc);
        //add frame rate help button
        gbc.gridx = 2;
        add(frameRateHelp, gbc);

        //add back button and submit button
        JButton backButton = new JButton("Back");
        JButton submitButton = new JButton("Submit");

        //create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.add(backButton);
        buttonPanel.add(submitButton);

        //add the button panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        //create action listener for back button
        backButton.addActionListener(e -> onBack.run());

        //create action listener for submit button
        submitButton.addActionListener(e -> onSubmit.run());
    }

    //create helpButton method to give user instructions on how to fill out the form
    private JButton createHelpButton(String hint) {

        JButton helpButton = new JButton("?");

        //set the font and color for the help button
        helpButton.setToolTipText("Click for help");

        //use UIStyles class to style the button
        UIStyles.styleMenuButton(helpButton);

        //finish styling the button
        helpButton.setFocusPainted(false);
        helpButton.setBorderPainted(false);
        helpButton.setMargin(new Insets(0, 0, 0, 0));

        //create action listener for help button
        helpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, hint, "Helpful Hint", JOptionPane.INFORMATION_MESSAGE);
        });
        
        return helpButton;
    }
}
