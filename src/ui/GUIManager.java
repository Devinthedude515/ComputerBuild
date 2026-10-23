package ui;
/*
    This class will be used to create a GUI for the user to interact with. 
    It will have a text field for the user to enter their name, and a button to submit the name. 
    The name will be validated using the UserInputService class, and the validated name will be displayed on the GUI.
 */
//imports
import java.awt.*;
import javax.swing.*;

public class GUIManager {

    /*  
        fields that control the size of the GUI, and the components that are displayed on it. 
        The frame is the main window that contains all the other components. The buttons are used to perform actions, 
        and the text field is used to collect user input. The text area is used to display output, 
        and the label is used to display instructions or questions.
    */
    private JFrame frame;
    private int width;
    private int height;

    //create cardLayout to switch between different panels, and a flow layout to arrange components in a row
    private CardLayout cardLayout;
    private JPanel mainPanel, homePanel, createBuildPanel, submittedPanel;

    //all labels like questions, instructions, and output should be in a JLabel object
    private JLabel nameLabel, buildNameLabel, budgetLabel, targetYearLabel, gamingGenreLabel, screenResolutionLabel, frameRateLabel;

    //text field to collect user input, it can be set to a certain number of columns, which is the width of the text field
    private JTextField nameInput, buildNameInput, budgetInput, targetYearInput, gamingGenreInput, screenResolutionInput, frameRateInput;

    //text area to display output, or for user input, or both. It can be used to display multiple lines of text, 
    // and can be set to be editable or not.
    private JTextArea ta;


    //create constructor
    //This sets the Frame and controls what is printed to the user, and how the user can interact with the GUI. 
    // It also sets the size of the frame.
    public GUIManager(int w, int h) {
        
        //sets the Frame
        frame = new JFrame();

        //sets the size of the frame
        width = w;
        height = h;

        //set the cardlayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

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
    }

    //create method to style the buttons
    //styling for the buttons
    private void styleMenuButton(JButton button) {
        //set the dimensions
        button.setPreferredSize(new Dimension(240, 50));
        
        //set the Font
        button.setFont(new Font("SansSerif", Font.BOLD, 16));

        //set the background and Foreground colors
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
    }

    //method to style the labels
    private void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
    }
    //setup the home panel
    private JPanel createHomePanel() {
        //create a new panel with a grid bag layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //set background color of the panel
        panel.setBackground(new Color(15, 23, 42));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        //set the insets for the components
        gbc.insets = new Insets(10, 10, 10, 10);

        //create a label to display the title of the application
        JLabel titleLabel = new JLabel("Welcome to PCBuilder Unlimited!");

        //set font of the title label
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));

        //add the title label to the panel
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        //create a button to navigate to the create build panel and add it to the panel
        JButton createBuildButton = new JButton("Create Build");
        gbc.gridy = 1;
        panel.add(createBuildButton, gbc);

        //create a button to navigate to the browse build panel and add it to the panel
        JButton browseButton = new JButton("Browse Build");
        gbc.gridy = 2;
        panel.add(browseButton, gbc);

        //create a button to navigate to the edit build panel and add it to the panel
        JButton editButton = new JButton("Edit Build");
        gbc.gridy = 3;
        panel.add(editButton, gbc);

        //create a button to exit the application and add it to the panel
        JButton exitButton = new JButton("Exit");
        gbc.gridy = 4;
        panel.add(exitButton, gbc);

        //style the buttons
        styleMenuButton(createBuildButton);
        styleMenuButton(browseButton);
        styleMenuButton(editButton);
        styleMenuButton(exitButton);

        //create action listener for the create build button
        createBuildButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "CREATE");
        });

        //create action listener for the browse build button
        browseButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "BROWSE");
        });

        //create action listener for the edit build button
        editButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "EDIT");
        });

        //create action listener for the exit button
        exitButton.addActionListener(e -> frame.dispose());

        return panel;
    }

    //setup the create Build panel
    private JPanel createBuildPanel() {
        //create panel and set the layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //set background color of the panel
        panel.setBackground(new Color(15, 23, 42));

        //create a title for the create build panel
        JLabel createBuildLabel = new JLabel("Step 1: Enter Specifications");

        //set the font and color for the title label
        createBuildLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        createBuildLabel.setForeground(Color.WHITE);
        
        //add the title label to the panel
        gbc.gridy = 0;
        panel.add(createBuildLabel, gbc);

        //add components to the panel
        //Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameInput, gbc);

        //Build Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(buildNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(buildNameInput, gbc);

        //Budget
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(budgetLabel, gbc);
        gbc.gridx = 1;
        panel.add(budgetInput, gbc);

        //Target Year
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(targetYearLabel, gbc);
        gbc.gridx = 1;
        panel.add(targetYearInput, gbc);

        //Gaming Genre
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(gamingGenreLabel, gbc);
        gbc.gridx = 1;
        panel.add(gamingGenreInput, gbc);

        //Screen Resolution
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(screenResolutionLabel, gbc);
        gbc.gridx = 1;
        panel.add(screenResolutionInput, gbc);

        //Frame Rate
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(frameRateLabel, gbc);
        gbc.gridx = 1;
        panel.add(frameRateInput, gbc);

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
        panel.add(buttonPanel, gbc);

        //style all the labels
        styleLabel(nameLabel);
        styleLabel(buildNameLabel);
        styleLabel(budgetLabel);
        styleLabel(targetYearLabel);
        styleLabel(gamingGenreLabel);
        styleLabel(screenResolutionLabel);
        styleLabel(frameRateLabel);
        
        //create action listener for back button
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "HOME");
        });

        //create action listener for submit button
        submitButton.addActionListener(e ->
            cardLayout.show(mainPanel, "SUBMITTED")
        );

        return panel;
    }

    private JPanel createSubmittedPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Build Submitted!");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel message = new JLabel("Your build information has been received.");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 10, 15, 10);

        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridy = 1;
        panel.add(message, gbc);

        //add back button and submit button
        JButton homeButton = new JButton("Home");
        JButton finishButton = new JButton("Finish");
        
        //create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.add(homeButton, gbc);
        buttonPanel.add(finishButton, gbc);

        //add the button panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(buttonPanel, gbc);

        //create action listener for back button
        homeButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "HOME");
        });

        //create action listener for submit button
        finishButton.addActionListener(e ->
            cardLayout.show(mainPanel, "HOME")
        );

        return panel;
    }

    //this function controls what is being displayed on the GUI, and how it is laid out. 
    // It also sets the size of the frame and makes it visible.
    public void setUpGUI() {
       
        //set up the home panel
        homePanel = createHomePanel();
        createBuildPanel = createBuildPanel();
        submittedPanel = createSubmittedPanel();

        //add the panels to the main panel
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(createBuildPanel, "CREATE");
        mainPanel.add(submittedPanel, "SUBMITTED");

        //set the frame size, title, and default close operation.
        frame.setSize(width, height);
        frame.setTitle("PCBuilder Unlimited");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //Open home screen first
        cardLayout.show(mainPanel, "HOME");

        //center the window on the screen
        frame.setVisible(true);
    }
}
