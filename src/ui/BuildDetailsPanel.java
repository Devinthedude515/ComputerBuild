package ui;

import java.awt.*;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import model.*;
import model.Component;
import service.ConsoleComparisonService;


public class BuildDetailsPanel extends JPanel {
    
    //create private Variables to reflect user input
    private JLabel nameLabel, buildNameLabel, budgetLabel, targetYearLabel, 
                   gamingGenreLabel, screenResolutionLabel, frameRateLabel;

    //set final color scheme's for the section and background
    private final Color backGroundColor = new Color(15, 23, 42);
    private final Color sectionColor = new Color(30, 41, 60);

    private JTextPane consoleComparisonText;

    //create EnumMap for componentLabels and totalPriceLabel with remainingBudgetLabel
    private EnumMap<ComponentType, JLabel> componentLabels;
    private JLabel totalPriceLabel, remainingBudgetLabel;

    //create content Panel and scroll pane to hold the content
    private JPanel contentPanel;
    private JScrollPane scrollPane;

    //create NumberFormat for currency
    private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    //create constructor
    public BuildDetailsPanel(Runnable onBack) {

        //set the Main layout, background, and border
        setLayout(new BorderLayout(0, 15));
        setBackground(backGroundColor);
        setBorder(new EmptyBorder(20, 25, 20, 25));

        //initialize the componentLabels map
        componentLabels = new EnumMap<>(ComponentType.class);

        // ============================
        // HEADER
        // ============================

        //create header panel and set the layout
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        //create the title and set the font and color
        JLabel title = new JLabel("Build Details");
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        //create and set the back button
        JButton backButton = new JButton("Back to Browse");
        UIStyles.styleMenuButton(backButton);
        backButton.addActionListener(e -> onBack.run());

        //add the title and back button to the header panel
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);

        //add the header panel to the top of the main panel
        add(headerPanel, BorderLayout.NORTH);

        // ============================
        // SCROLLABLE CONTENT
        // ============================

        //create the content panel and set the layout
        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(backGroundColor);

        // ============================
        // SECTION 1: SPECIFICATIONS
        // ============================

        //create title for the specifications section
        JPanel specsPanel = createSection("Build Specifications");

        //create the labels
        buildNameLabel = createLabel();
        nameLabel = createLabel();
        budgetLabel = createLabel();
        targetYearLabel = createLabel();
        gamingGenreLabel = createLabel();
        screenResolutionLabel = createLabel();
        frameRateLabel = createLabel();

        int row = 1;

        //add each label
        addRow(specsPanel, "Build Name:", buildNameLabel, row++);
        addRow(specsPanel, "Created by:", nameLabel, row++);
        addRow(specsPanel, "Budget:", budgetLabel, row++);
        addRow(specsPanel, "Target Year:", targetYearLabel, row++);
        addRow(specsPanel, "Gaming Genre:", gamingGenreLabel, row++);
        addRow(specsPanel, "Resolution:", screenResolutionLabel, row++);
        addRow(specsPanel, "Frame Rate:", frameRateLabel, row++);

        //add the specifications panel to the content panel
        addSection(specsPanel, 0);

        // ============================
        // SECTION 2: COMPONENTS
        // ============================

        //create title for the components section
        JPanel componentsPanel = createSection("Selected Components");

        row = 1;

        //use for each to sift through the ComponentType enum and create a label for each component type
        for (ComponentType type : ComponentType.values()) {

            //create a label for each component type and add it to the map
            JLabel valueLabel = createLabel();
            componentLabels.put(type, valueLabel);

            addRow(componentsPanel, type.name().replace("_", " ") + ":", valueLabel, row++);
        }

        //add the components panel to the content panel
        addSection(componentsPanel, 1);

        // ============================
        // SECTION 3: COST SUMMARY
        // ============================

        //create title for the cost summary section
        JPanel costPanel = createSection("Cost Summary");

        //create total price and remaining budget labels
        totalPriceLabel = createLabel();
        remainingBudgetLabel = createLabel();

        //set the font and color for the total price and remaining budget labels
        totalPriceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        remainingBudgetLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        //add the total price and remaining budget labels to the cost panel
        addRow(costPanel, "Total Cost:", totalPriceLabel, 1);
        addRow(costPanel, "Remaining Budget:", remainingBudgetLabel, 2);

        //add the cost panel
        addSection(costPanel, 2);

        // ============================
        // SECTION 4: PERFORMANCE COMPARISON
        // ============================

        //create the performance comparison section
        JPanel scoringPanel = createSection("Gaming Performance Comparison");

        //create a text pane to display styled comparisons
        consoleComparisonText = new JTextPane();

        consoleComparisonText.setEditable(false);
        consoleComparisonText.setFocusable(false);

        //match the existing dark theme
        consoleComparisonText.setBackground(sectionColor);
        consoleComparisonText.setForeground(Color.WHITE);

        //set the default font
        consoleComparisonText.setFont(new Font("SansSerif", Font.PLAIN, 14));

        //give the text space around the edges
        consoleComparisonText.setBorder(new EmptyBorder(15, 10, 15, 10));

        //position the text within scoringPanel
        GridBagConstraints comparisonGbc = new GridBagConstraints();

        comparisonGbc.gridx = 0;
        comparisonGbc.gridy = 1;
        comparisonGbc.gridwidth = 2;
        comparisonGbc.weightx = 1.0;
        comparisonGbc.fill = GridBagConstraints.HORIZONTAL;
        comparisonGbc.anchor = GridBagConstraints.WEST;

        //add the text pane to the scoring panel
        scoringPanel.add(consoleComparisonText, comparisonGbc);

        //add scoring panel to the content panel
        addSection(scoringPanel, 3);

        //keep sections aligned at the top of the panel
        GridBagConstraints filler = new GridBagConstraints();
        filler.gridx = 0;
        filler.gridy = 4;
        filler.weighty = 1.0; //distributes vertical space to push the sections to the top
        filler.fill = GridBagConstraints.VERTICAL;

        //add the filler to the content panel
        contentPanel.add(Box.createVerticalGlue(), filler);

        //add the content panel to a scroll pane
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(backGroundColor);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        //add the scroll pane to the main panel
        add(scrollPane, BorderLayout.CENTER);

        //initial placeholder content
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
    private void addRow(JPanel section, String labelText, JLabel valueLabel, int row) {

        //create gbc object to organize the layout
        GridBagConstraints gbc = new GridBagConstraints();
        
        //positons the label
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //creates and adds the label description to the panel
        JLabel description = new JLabel(labelText);
        UIStyles.styleLabel(description);

        //sets the position and size of the description label
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(6, 0, 6, 15);
        section.add(description, gbc);

        //adds the value label to the panel
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(6, 0, 6, 15);
        section.add(valueLabel, gbc);

    }

    //create a styled section containing a title
    private JPanel createSection(String heading) {

        JPanel section = new JPanel(new GridBagLayout());
        section.setBackground(sectionColor);

        section.setBorder(
                BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 65, 85)),
                new EmptyBorder(15, 20, 15, 20)
            )
        );

        JLabel sectionTitle = new JLabel(heading);
        sectionTitle.setForeground(Color.WHITE);
        sectionTitle.setFont(
            new Font("SansSerif", Font.BOLD, 20)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 15, 0);

        section.add(sectionTitle, gbc);

        return section;
    }

    //method to add a section to the content panel
    private void addSection(JPanel section, int row) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 15, 0);

        contentPanel.add(section, gbc);
    }

    // ============================
    // DISPLAY SUBMITTED BUILD
    // ============================

    //overloaded method to show the Build once opened
    //display a submitted build
    public void showBuild(BuildRequest request, PCBuild build) {

        //display original specifications
        buildNameLabel.setText(request.getNameOfBuild() == null || request.getNameOfBuild().trim().isEmpty()
                ? "Unnamed Build"
                : request.getNameOfBuild()
        );

        //get all of the values for specification
        nameLabel.setText(request.getName());
        budgetLabel.setText(currency.format(request.getBudget()));
        targetYearLabel.setText(String.valueOf(request.getTargetYear()));

        gamingGenreLabel.setText(String.valueOf(request.getGamingGenre()));

        screenResolutionLabel.setText(String.valueOf(request.getScreenResolution()).replace("_", " "));

        frameRateLabel.setText(String.valueOf(request.getFrameRate()).replace("FPS_", "") + " FPS");

        ConsoleComparisonService comparisonService = new ConsoleComparisonService();

        styleConsoleComparison(comparisonService.getComparisonText(build));

        //display the selected hardware
        for (ComponentType type : ComponentType.values()) {

            Component component = build.getSelectedComponent(type);
            JLabel label = componentLabels.get(type);

            //check if null then display "Not Selected" else display the component name and price
            if (component != null) {
                    label.setText(component.getName() + " - " + currency.format(component.getPrice()));
            } 
            else {
                label.setText("Not Selected");
            }
        }

        //create total and remaing values to keep track of the total price and remaining budget
        double total = build.calculateTotalPrice();
        double remaining = build.getRemainingBudget(request.getBudget());

        // Display total price
        totalPriceLabel.setText(currency.format(total));
        remainingBudgetLabel.setText(currency.format(remaining));

         //change budget color based on the result
        if (remaining < 0) {
            //set to red if the remaining budget is negative
            remainingBudgetLabel.setForeground(new Color(248, 113, 113));
        } else {
            //set to green if the remaining budget is positive
            remainingBudgetLabel.setForeground(new Color(74, 222, 128));
        }

        //set vertical scroll bar to the top of the panel
        scrollPane.getVerticalScrollBar().setValue(0);
    }

    //method to show the Build once opened
    public void showBuild(String buildName, String creator) {

        buildNameLabel.setText(buildName);
        nameLabel.setText(creator);

        //temporarily set the other labels to "Not available" until a build is selected
        budgetLabel.setText("Not available");
        targetYearLabel.setText("Not available");
        gamingGenreLabel.setText("Not available");
        screenResolutionLabel.setText("Not available");
        frameRateLabel.setText("Not available");
        consoleComparisonText.setText("Console comparison unavailable for sample builds.");

        for (JLabel label : componentLabels.values()) {
            label.setText("Not available");
        }

        totalPriceLabel.setText("Not available");
        remainingBudgetLabel.setText("Not available");
    }

    private SimpleAttributeSet createComparisonStyle(int fontSize, boolean bold, Color color) {

        SimpleAttributeSet style = new SimpleAttributeSet();

        StyleConstants.setFontFamily(style, "SansSerif");
        StyleConstants.setFontSize(style, fontSize);
        StyleConstants.setBold(style, bold);
        StyleConstants.setForeground(style, color);

        return style;
    }

    private void styleConsoleComparison(String comparisonText) {

        StyledDocument document = consoleComparisonText.getStyledDocument();

        //define text styles
        SimpleAttributeSet titleStyle = createComparisonStyle(18, true, new Color(125, 211, 252));

        SimpleAttributeSet headingStyle = createComparisonStyle(16, true, Color.WHITE);

        SimpleAttributeSet normalStyle = createComparisonStyle(14, false, new Color(203, 213, 225));

        SimpleAttributeSet priceStyle = createComparisonStyle(14, true, new Color(250, 204, 21));

        SimpleAttributeSet positiveStyle = createComparisonStyle(14, true, new Color(74, 222, 128));

        SimpleAttributeSet negativeStyle = createComparisonStyle(14, true, new Color(248, 113, 113));

        SimpleAttributeSet neutralStyle = createComparisonStyle(14, true, new Color(250, 204, 21));

        SimpleAttributeSet noteStyle = createComparisonStyle(12, false, new Color(148, 163, 184));

        boolean customPCSection = true;

        try {
            //clear the previous comparison
            document.remove(0, document.getLength());

            //read the comparison one line at a time
            String[] lines = comparisonText.split("\\R");

            for (String line : lines) {

                SimpleAttributeSet selectedStyle = normalStyle;

                //main section headings
                if (line.equals("CUSTOM PC")) {
                    selectedStyle = titleStyle;
                }
                else if (line.equals("CONSOLE COMPARISONS")) {
                    selectedStyle = headingStyle;
                    customPCSection = false;
                }

                //style the price separately from the score
                else if (line.startsWith("Score:")) {

                    int pricePosition = line.indexOf(" | Price: ");

                    if (pricePosition >= 0) {

                        String score = line.substring(0, pricePosition);
                        String price = line.substring(pricePosition + 3);

                        document.insertString(document.getLength(), score, customPCSection ? titleStyle : normalStyle);

                        document.insertString(document.getLength(), "  |  ", normalStyle);

                        document.insertString(document.getLength(), price + "\n", priceStyle);

                        continue;
                    }
                }

                //PC has a higher numerical score
                else if (line.startsWith("PC score +")) {
                    selectedStyle = positiveStyle;
                }

                //PC has a lower numerical score
                else if (line.startsWith("PC score -")) {
                    selectedStyle = negativeStyle;
                }

                //dqual scores
                else if (line.equals("Same reference score")) {
                    selectedStyle = neutralStyle;
                }

                //disclaimer
                else if (line.startsWith("Scores are estimates")) {
                    selectedStyle = noteStyle;
                }

                //console names
                else if (!line.trim().isEmpty()) {
                    selectedStyle = headingStyle;
                }

                //insert the styled line
                document.insertString(
                    document.getLength(),
                    line + "\n",
                    selectedStyle
                );
            }
        } 
        catch (BadLocationException ex) {
            throw new IllegalStateException("Unable to display console comparison.", ex);
        }

        //show the beginning of the comparison
        consoleComparisonText.setCaretPosition(0);
    }
}
