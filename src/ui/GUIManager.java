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

    //text area to display output, or for user input, or both. It can be used to display multiple lines of text, 
    // and can be set to be editable or not.
    private JTextArea ta;

    //create variables for the browseBuildPanel and the buildDetailsPanel
    private BrowseBuildPanel browseBuildPanel;
    private BuildDetailsPanel buildDetailsPanel;


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

    }

    //this function controls what is being displayed on the GUI, and how it is laid out. 
    public void setUpGUI() {
       
        //set up the home panel
        homePanel = new HomePanel(
            
            //use lambda expressions to define the actions for each button
            () -> cardLayout.show(mainPanel, "CREATE"),
            
            () -> cardLayout.show(mainPanel, "BROWSE"),

            () -> JOptionPane.showMessageDialog(
                frame, "Edit Build is not implemented yet."),

            () -> frame.dispose()
        );

        //setup the create build panel
        createBuildPanel = new CreateBuildPanel(
            //use lambda expressions to define the actions for each button
            () -> cardLayout.show(mainPanel, "HOME"),

            () -> cardLayout.show(mainPanel, "SUBMITTED")
        );

        // Create the browse gallery
        browseBuildPanel = new BrowseBuildPanel(
            //use lambda expressions to define the actions for each button
            () -> cardLayout.show(mainPanel, "HOME"),

            card -> {

                buildDetailsPanel.showBuild(card.getBuildName(), card.getCreator());

                cardLayout.show(mainPanel, "BUILD_DETAILS");
            }
        );
        // Create the build details screen
        buildDetailsPanel = new BuildDetailsPanel(
            () -> cardLayout.show(mainPanel, "BROWSE")
        );

        submittedPanel = new SubmittedPanel(
            //use lambda expressions to define the actions for each button
            () -> cardLayout.show(mainPanel, "HOME")
        );

        //add the panels to the main panel
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(createBuildPanel, "CREATE");
        mainPanel.add(submittedPanel, "SUBMITTED");
        mainPanel.add(browseBuildPanel, "BROWSE");
mainPanel.add(buildDetailsPanel, "BUILD_DETAILS");

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
