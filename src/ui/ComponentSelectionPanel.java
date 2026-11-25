package ui;
import java.awt.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.BuildRequest;
import model.ComponentLibrary;
import model.PCBuild;
import service.BuildValidationService;


public class ComponentSelectionPanel extends JPanel {
    
    //create attributes
    private BuildRequest request;
    private ComponentLibrary library;
    private PCBuild build;
    private JButton submitButton;

    //create Jlabels for total, remaining, and budget using summaryPanel
    private BuildSummaryPanel summaryPanel;

    //create object for dropdown options
    private ComponentDropdownPanel dropdownPanel;

    //create a Consumer of PCBuild to handle submission
    private Consumer<PCBuild> onSubmit;

    //create number format for currency
    private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);


    public ComponentSelectionPanel(BuildRequest request, ComponentLibrary library, Runnable onBack, Consumer<PCBuild> onSubmit) {

        this.request = request;
        this.library = library;
        this.build = new PCBuild();
        this.onSubmit = onSubmit;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(15, 23, 42));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        //initialize the summary first
        summaryPanel = new BuildSummaryPanel(request, build);

        //create the dropdown panel using the same build
        dropdownPanel = new ComponentDropdownPanel(request, library, build, summaryPanel);

        // ============================
        // RECOMMENDED COMPONENTS
        // ============================

        //create the recommendations panel
        ComponentRecommendationPanel combinedRecommendationsPanel = new ComponentRecommendationPanel(request, library, dropdownPanel::applyRecommendedPair);

        // ============================
        // ORGANIZE CENTER CONTENT
        // ============================

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));

        centerPanel.setBackground(new Color(15, 23, 42));

        //recommendations appear above the dropdowns
        centerPanel.add(combinedRecommendationsPanel, BorderLayout.NORTH);

        //existing component selection area
        centerPanel.add(dropdownPanel, BorderLayout.CENTER);

        //add scrolling so the window remains usable
        JScrollPane scrollPane = new JScrollPane(centerPanel);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        scrollPane.getViewport().setBackground(new Color(15, 23, 42));

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JButton backButton = new JButton("Back");
        submitButton = new JButton("Submit Build");

        UIStyles.styleMenuButton(backButton);
        UIStyles.styleMenuButton(submitButton);

        backButton.addActionListener(e -> onBack.run());
        submitButton.addActionListener(e -> handleSubmit());

        //create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(15, 23, 42));   

        buttonPanel.add(backButton);
        buttonPanel.add(submitButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(15, 23, 42));

        bottomPanel.add(summaryPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        //refeshes the budget
        summaryPanel.refreshBudget();
    }

    //overloaded constructor to handle edit build requests
    public ComponentSelectionPanel(BuildRequest request, ComponentLibrary library, PCBuild existingBuild, Runnable onBack, Consumer<PCBuild> onSave) {

        //reuse the existing component selection screen
        this(request, library, onBack, onSave);

        //update the button label for editing
        setSubmitButtonText("Save Changes");

        //load previously selected components
        dropdownPanel.loadExistingBuild(existingBuild);
    }

    private void handleSubmit() {
 
        //create the validation service
        BuildValidationService validationService = new BuildValidationService();

        //validate the current build
        ArrayList<String> errors = validationService.validateBuild(request, build);

        //block submission if errors exist
        if (!errors.isEmpty()) {

            String message = "Please fix the following issues:\n\n- " + String.join("\n- ", errors);

            JOptionPane.showMessageDialog(this, message, "Build Validation Failed", JOptionPane.ERROR_MESSAGE);

            return;
        }

        //check whether the build exceeds the budget
        double totalCost = build.calculateTotalPrice();
        double budget = request.getBudget();

        if (totalCost > budget) {

            double overBudget = totalCost - budget;

            String message = String.format(
                "Your build exceeds the budget by $%.2f.\n\n" +
                "Do you want to submit it anyway?",
                overBudget
            );

            int choice = JOptionPane.showConfirmDialog(
                this,
                message,
                "Budget Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            //cancel submission if user selects No
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
        }

        //submit the build
        onSubmit.accept(build);
    }

    public void setSubmitButtonText(String text) {

        submitButton.setText(text);
    }
}
