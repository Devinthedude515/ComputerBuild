package ui;
/*
    This class will be used to create a GUI for the user to interact with. 
    It will have a text field for the user to enter their name, and a button to submit the name. 
    The name will be validated using the UserInputService class, and the validated name will be displayed on the GUI.
 */
//imports
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.swing.*;
import model.BuildRequest;
import model.ComponentLibrary;
import model.PCBuild; //there is a java.awt.Component so we must be explicit
import model.SavedBuild;
import service.BuildFileService;
import service.BuildScoringService;
import service.RecommendationService;
import service.RecommendationTier;
import service.RecommendedPair;
import service.RecommendedPairTierService;

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

    //create variables for the panels
    private BrowseBuildPanel browseBuildPanel;
    private BuildDetailsPanel buildDetailsPanel;
    private ComponentSelectionPanel componentSelectionPanel;
    private EditBuildPanel editBuildPanel;
    private ComponentSelectionPanel editSelectionPanel;
    
    //create variables for the component library
    private BuildRequest currentRequest;
    private PCBuild completedBuild;

    //allows the saving of builds locally
    private final BuildFileService buildFileService = new BuildFileService();


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

            () -> {

                //refresh the saved-build list
                editBuildPanel.showBuilds(browseBuildPanel.getSavedBuildCards());

                //open Edit Builds
                cardLayout.show(mainPanel, "EDIT_LIST");
            },

            () -> frame.dispose()
        );

        //setup the create build panel
        createBuildPanel = new CreateBuildPanel(
            
            //use lambda expressions to define the actions for each button
            () -> cardLayout.show(mainPanel, "HOME"),

            request -> {
                
                //store the user's specifications
                currentRequest = request;

                //create the component library object
                ComponentLibrary library = new ComponentLibrary();

                //create recommendation sevice object
                RecommendationService recommendationService = new RecommendationService();

                //create the tier service
                RecommendedPairTierService tierService = new RecommendedPairTierService();

                //retrieve all eligible CPU/GPU pairs
                ArrayList<RecommendedPair> eligiblePairs = recommendationService.findEligiblePairs(currentRequest, library);

                //calculate the recommendation tiers
                EnumMap<RecommendationTier, RecommendedPair> pairTiers = tierService.calculateTiers(eligiblePairs);
                
                // remove the previos selection panel, if one exists
                if (componentSelectionPanel != null) {
                    mainPanel.remove(componentSelectionPanel);
                }

                //create the component selection panel, and add it to the main panel.
                componentSelectionPanel = new ComponentSelectionPanel(
                    currentRequest,
                    library,

                    // Back button returns to Create Build
                    () -> cardLayout.show(mainPanel, "CREATE"),

                    // Submit button returns to Submitted panel
                    build -> {

                        completedBuild = build;

                        // Check that GUIManager received the build
                        System.out.println("GUIManager received PC Build!");
                        System.out.println("Total Cost: $" + completedBuild.calculateTotalPrice());

                        // Add the completed build to the Browse Builds gallery
                        browseBuildPanel.addBuild(currentRequest, completedBuild);
                        //saves created builds locally
                        saveAllBuilds();
                        cardLayout.show(mainPanel, "SUBMITTED");

                        // Create the scoring service
                        BuildScoringService scoringService = new BuildScoringService();

                        // Check whether the build can be scored
                        if (scoringService.canScore(completedBuild)) {

                            double gamingScore = scoringService.calculateGamingScore(completedBuild);

                            System.out.printf("Gaming Performance Score: %.1f / 100%n", gamingScore);

                        } 
                        else {

                            System.out.println("Gaming score unavailable: CPU or GPU not selected.");
                        }

                        // Check the user's budget
                        boolean withinBudget = scoringService.isWithinBudget(completedBuild, currentRequest);
                        
                        //print the budget
                        System.out.println("Within Budget: " + withinBudget);
                    }

                    
                );
                
                mainPanel.add(componentSelectionPanel, "COMPONENT_SELECTION");
                mainPanel.revalidate();
                mainPanel.repaint();
                
                cardLayout.show(mainPanel, "COMPONENT_SELECTION");
            }
        );

        //create the browse gallery
        browseBuildPanel = new BrowseBuildPanel(

            //use lambda expressions to define the actions for each button
            () -> cardLayout.show(mainPanel, "HOME"),

            card -> {

                BuildRequest request = card.getBuildRequest();
                PCBuild build = card.getPCBuild();

                // Check whether the card contains a completed build
                if (request != null && build != null) {

                    buildDetailsPanel.showBuild(request, build);
                } 
                else {

                    // Display placeholder information for sample builds
                    buildDetailsPanel.showBuild(
                        card.getBuildName(),
                        card.getCreator()
                    );
                }

                cardLayout.show(mainPanel, "BUILD_DETAILS");
            }
        );

        //create the Edit Builds list
        editBuildPanel = new EditBuildPanel(

            //back button returns home
            () -> cardLayout.show(mainPanel, "HOME"),

            //selecting a build opens its editor
            card -> openBuildEditor(card)
        );
        
        //create the build details screen
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
        mainPanel.add(editBuildPanel, "EDIT_LIST");

        //set the frame size, title, and default close operation.
        frame.setSize(width, height);
        frame.setTitle("PCBuilder Unlimited");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //open home screen first
        cardLayout.show(mainPanel, "HOME");

        //restore saved builds
        loadAllBuilds();

        //center the window on the screen
        frame.setVisible(true);
    }

    //allows the edit build button to choose a card to edit
    private void openBuildEditor(BuildCard originalCard) {

        //retrieve the saved build information
        BuildRequest savedRequest = originalCard.getBuildRequest();

        PCBuild savedBuild = originalCard.getPCBuild();

        //ignore cards without actual build data
        if (savedRequest == null || savedBuild == null) {
            return;
        }

        //create the component library
        ComponentLibrary library = new ComponentLibrary();

        //remove the previous editor if it exists
        if (editSelectionPanel != null) {
            mainPanel.remove(editSelectionPanel);
        }

        //create the editing version of ComponentSelectionPanel
        editSelectionPanel = new ComponentSelectionPanel(

            savedRequest,
            library,
            savedBuild,

            //cancel edits and return to the list
            () -> {
                editBuildPanel.showBuilds(browseBuildPanel.getSavedBuildCards());

                cardLayout.show(mainPanel, "EDIT_LIST");
            },

            //save the edited build
            editedBuild -> {

                boolean updated = browseBuildPanel.replaceBuild(originalCard, savedRequest, editedBuild);

                if (!updated) {

                    JOptionPane.showMessageDialog(frame, "Unable to update the selected build.");

                    return;
                }

                //save the updated builds to the Desktop file
                saveAllBuilds();

                //refresh the Edit Builds list
                editBuildPanel.showBuilds(browseBuildPanel.getSavedBuildCards());

                //return to Edit Builds
                cardLayout.show(mainPanel, "EDIT_LIST");
            }
        );

        //register and display the editor
        mainPanel.add(editSelectionPanel, "EDIT_COMPONENTS");

        mainPanel.revalidate();
        mainPanel.repaint();

        cardLayout.show(mainPanel, "EDIT_COMPONENTS");
    }

    //saves buiilds locally
    private void saveAllBuilds() {

        List<SavedBuild> saved = new ArrayList<>();

        //collect user-created builds, excluding sample cards
        for (BuildCard card : browseBuildPanel.getSavedBuildCards()) {

            if (card.getBuildRequest() != null && card.getPCBuild() != null) {

                saved.add(new SavedBuild(card.getBuildRequest(), card.getPCBuild()));
            }
        }

        try {
            Path location = buildFileService.saveBuilds(saved);

            System.out.println("Builds saved to: " + location);

        } 
        catch (IOException ex) {

            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Could Not Save Builds", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAllBuilds() {

        try {
            List<SavedBuild> saved = buildFileService.loadBuilds();

            //addBuild() inserts at the beginning, so load in reverse to preserve display order
            for (int i = saved.size() - 1; i >= 0; i--) {

                SavedBuild item = saved.get(i);

                browseBuildPanel.addBuild(item.getRequest(), item.getBuild());
            }

            System.out.println("Loaded " + saved.size() + " saved builds.");

        } 
        catch (IOException ex) {

            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Could Not Load Builds", JOptionPane.ERROR_MESSAGE);
        }
    }
}
