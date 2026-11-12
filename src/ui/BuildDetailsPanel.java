package ui;

import java.awt.*;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;
import model.*;
import model.Component;

public class BuildDetailsPanel extends JPanel {
    
    //create private Variables to reflect user input
    private JLabel nameLabel, buildNameLabel, budgetLabel, targetYearLabel, gamingGenreLabel, screenResolutionLabel, frameRateLabel;

    //create EnumMap for componentLabels and totalPriceLabel with remainingBudgetLabel
    private EnumMap<ComponentType, JLabel> componentLabels;
    private JLabel totalPriceLabel, remainingBudgetLabel;

    //create content Panel
    private JPanel contentPanel;

    //create NumberFormat for currency
    private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    //create constructor
    public BuildDetailsPanel(Runnable onBack) {

        //set the layout and background
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 23, 42));

        //initialize the componentLabels map
        componentLabels = new EnumMap<>(ComponentType.class);

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

        //component heading section
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        JLabel componentTitle = new JLabel("Selected Components");
        componentTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        componentTitle.setForeground(Color.WHITE);
        add(componentTitle, gbc);

        //reset the width
        gbc.gridwidth = 1;

        // Create a label for every component category
        for (ComponentType type : ComponentType.values()) {

            JLabel valueLabel = createLabel();
            componentLabels.put(type, valueLabel);
            
            // Add the label to the panel
            addRow(type.name().replace("_", " ") + ":", valueLabel, gbc);
        }

        //Price summary
        totalPriceLabel = createLabel();
        remainingBudgetLabel = createLabel();

        addRow("Total Cost:", totalPriceLabel, gbc);
        addRow("Remaining Budget:", remainingBudgetLabel, gbc);

        //create the back button
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

    //overloaded method to show the Build once opened
    // Display a submitted build
    public void showBuild(BuildRequest request, PCBuild build) {

        // Display original specifications
        buildNameLabel.setText(request.getNameOfBuild() == null || request.getNameOfBuild().trim().isEmpty()
                ? "Unnamed Build"
                : request.getNameOfBuild()
        );

        nameLabel.setText(request.getName());
        budgetLabel.setText(currency.format(request.getBudget()));
        targetYearLabel.setText(String.valueOf(request.getTargetYear()));

        gamingGenreLabel.setText(String.valueOf(request.getGamingGenre()));

        screenResolutionLabel.setText(String.valueOf(request.getScreenResolution()).replace("_", " "));

        frameRateLabel.setText(String.valueOf(request.getFrameRate()).replace("FPS_", "") + " FPS");

        // Display the selected hardware
        for (ComponentType type : ComponentType.values()) {

            Component component = build.getSelectedComponent(type);
            JLabel label = componentLabels.get(type);

            //check if null then display "Not Selected" else display the component name and price
            if (component != null) {
                    label.setText(
                    component.getName() + " - " +
                    currency.format(component.getPrice())
                );
            } 
            else {
                label.setText("Not Selected");
            }
        }

        // Display total price
        totalPriceLabel.setText(currency.format(build.calculateTotalPrice()));

        remainingBudgetLabel.setText(currency.format(build.getRemainingBudget(request.getBudget())));
    }

    //method to show the Build once opened
    public void showBuild(String buildName, String creator) {
        buildNameLabel.setText(buildName);
        nameLabel.setText(creator);

        //Temporarily set the other labels to "Not available" until a build is selected
        budgetLabel.setText("Not available");
        targetYearLabel.setText("Not available");
        gamingGenreLabel.setText("Not available");
        screenResolutionLabel.setText("Not available");
        frameRateLabel.setText("Not available");

        for (JLabel label : componentLabels.values()) {
            label.setText("Not available");
        }

        totalPriceLabel.setText("Not available");
        remainingBudgetLabel.setText("Not available");
    }
}
