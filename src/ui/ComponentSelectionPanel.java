package ui;
import java.awt.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.BuildRequest;
import model.Component;
import model.ComponentLibrary;
import model.ComponentType;
import model.PCBuild;
import service.RecommendationService;
import service.RecommendationTier;
import service.RecommendedPair;
import service.RecommendedPairTierService;


public class ComponentSelectionPanel extends JPanel {
    
    //create attributes
    private BuildRequest request;
    private ComponentLibrary library;
    private PCBuild build;

    //create Enum map for components and ComboBox
    private EnumMap<ComponentType, JComboBox<Component>> componentDropdowns;

    //create Jlabels for total, remaining, and budget
    private JLabel totalCostLabel;
    private JLabel remainingBudgetLabel;
    private JLabel budgetStatusLabel;

    //create a Consumer of PCBuild to handle submission
    private Consumer<PCBuild> onSubmit;

    //create number format for currency
    private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    public ComponentSelectionPanel(BuildRequest request, ComponentLibrary library, Runnable onBack, Consumer<PCBuild> onSubmit) {
        this.request = request;
        this.library = library;
        this.build = new PCBuild();
        this.onSubmit = onSubmit;

        //initialize the componentDropdowns map
        componentDropdowns = new EnumMap<>(ComponentType.class);

        //set the layout of the panel
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(15, 23, 42));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        //create the title label
        JLabel titleLabel = new JLabel("Step 2: Select Your Components");
        UIStyles.styleTitleLabel(titleLabel);
        add(titleLabel, BorderLayout.NORTH);

        // Component selection area
        JPanel selectionPanel = new JPanel(new GridLayout(8, 2, 15, 15));
        selectionPanel.setBackground(new java.awt.Color(15, 23, 42));
        
        //add the components
        addComponentRow(selectionPanel, ComponentType.CPU, "CPU");
        addComponentRow(selectionPanel, ComponentType.GPU, "Graphics Card");
        addComponentRow(selectionPanel, ComponentType.RAM, "Memory");
        addComponentRow(selectionPanel, ComponentType.STORAGE, "Storage");
        addComponentRow(selectionPanel, ComponentType.MOTHERBOARD, "Motherboard");
        addComponentRow(selectionPanel, ComponentType.POWER_SUPPLY, "Power Supply");
        addComponentRow(selectionPanel, ComponentType.CASE, "Case");
        addComponentRow(selectionPanel, ComponentType.CPU_COOLER, "CPU Cooler");

        // ============================
        // RECOMMENDED COMPONENTS
        // ============================

        //create the recommendation service
        RecommendationService recommendationService = new RecommendationService();

        //get recommended CPUs and GPUs
        EnumMap<RecommendationTier, Component> recommendedCPUs = recommendationService.recommendCPUTiers(request, library);

        EnumMap<RecommendationTier, Component> recommendedGPUs = recommendationService.recommendGPUTiers(request, library);

        //create the recommendations panel
        JPanel recommendationsPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        recommendationsPanel.setOpaque(false);

        //add recommended CPUs and GPUs
        recommendationsPanel.add(createRecommendationSection("Recommended CPUs",recommendedCPUs));

        recommendationsPanel.add(createRecommendationSection("Recommended GPUs", recommendedGPUs));

        //create the tier service
        RecommendedPairTierService tierService = new RecommendedPairTierService();

        //retrieve every eligible CPU/GPU combination
        ArrayList<RecommendedPair> eligiblePairs = recommendationService.findEligiblePairs(request, library);

        //categorize the eligible pairs
        EnumMap<RecommendationTier, RecommendedPair> pairTiers = tierService.calculateTiers(eligiblePairs);

        //create the recommended pair panel
        RecommendedPairPanel recommendedPairPanel = new RecommendedPairPanel(pairTiers, pair -> applyRecommendedPair(pair));

        //create a panel to organize both recommendation sections
        JPanel combinedRecommendationsPanel = new JPanel(new BorderLayout(0, 15));

        combinedRecommendationsPanel.setOpaque(false);

        //add recommended pairs to the top
        combinedRecommendationsPanel.add(recommendedPairPanel, BorderLayout.NORTH);

        //add individual CPU/GPU recommendations below
        combinedRecommendationsPanel.add(recommendationsPanel, BorderLayout.CENTER);

        // ============================
        // ORGANIZE CENTER CONTENT
        // ============================

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));

        centerPanel.setBackground(new Color(15, 23, 42));

        //recommendations appear above the dropdowns
        centerPanel.add(combinedRecommendationsPanel, BorderLayout.NORTH);

        //existing component selection area
        centerPanel.add(selectionPanel, BorderLayout.CENTER);

        //add scrolling so the window remains usable
        JScrollPane scrollPane = new JScrollPane(centerPanel);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        scrollPane.getViewport().setBackground(new Color(15, 23, 42));

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        //budget information
        JPanel budgetPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        budgetPanel.setBackground(new java.awt.Color(15, 23, 42));

        totalCostLabel = new JLabel();
        remainingBudgetLabel = new JLabel();
        budgetStatusLabel = new JLabel();

        UIStyles.styleLabel(totalCostLabel);
        UIStyles.styleLabel(remainingBudgetLabel);
        UIStyles.styleLabel(budgetStatusLabel);

        budgetPanel.add(totalCostLabel);
        budgetPanel.add(remainingBudgetLabel);
        budgetPanel.add(budgetStatusLabel);

        // Buttons
        JButton backButton = new JButton("Back");
        JButton submitButton = new JButton("Submit Build");

        UIStyles.styleMenuButton(backButton);
        UIStyles.styleMenuButton(submitButton);

        backButton.addActionListener(e -> onBack.run());
        submitButton.addActionListener(e -> { 

            //use for each method to go through list of components
            for (Component component : build.getSelectedComponents()) {
                System.out.println(component.getName());
            }

            System.out.println("Total: $" + build.calculateTotalPrice());

            //Submit the build
            onSubmit.accept(build);
        });

        //create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(15, 23, 42));   

        buttonPanel.add(backButton);
        buttonPanel.add(submitButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(15, 23, 42));

        bottomPanel.add(budgetPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        updateBudgetDisplay();
    }

    //creates a dropdown for one component category
    private void addComponentRow(JPanel panel, ComponentType type, String labelText) {
        
        JLabel label = new JLabel(labelText);
        UIStyles.styleLabel(label);

        JComboBox<Component> dropdown = new JComboBox<>();

        //add an empty selection
        dropdown.addItem(null);

        //retrieve components belonging to this category
        ArrayList<Component> availableComponents = library.getComponentsByType(type);

        for (Component component : availableComponents) {
            
            //apply year filtering to the CPUs and GPUs
            if(type == ComponentType.CPU || type == ComponentType.GPU) {

                //skip components released after the target year
                if (component.getReleaseYear() > request.getTargetYear()) {
                    continue;
                }
            }

            //add eligible components
            dropdown.addItem(component);
        }

        //display component names and prices
        dropdown.setRenderer(new DefaultListCellRenderer() {

            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                //make call to super and insert values to display components
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value == null) {
                    setText("Select a component...");
                } 
                else {
                    
                    Component component = (Component) value;

                    setText(component.getName() + " - " + currency.format(component.getPrice()));
                }

                return this;
            }
        });

        UIStyles.styleComboBox(dropdown);

        //update the build whenever the selection changes
        dropdown.addActionListener(e -> {

            Component selected = (Component) dropdown.getSelectedItem();

            if (selected == null) {
                build.removeComponent(type);
            } 
            else {
                build.selectComponent(selected);
            }

            updateBudgetDisplay();
        });

        componentDropdowns.put(type, dropdown);

        panel.add(label);
        panel.add(dropdown);
    }

    //updates the total and remaining budget
    private void updateBudgetDisplay() {

        double total = build.calculateTotalPrice();
        double remaining = request.getBudget() - total;

        totalCostLabel.setText("Selected Components: " + currency.format(total));

        remainingBudgetLabel.setText("Remaining Budget: " + currency.format(remaining));

        if (remaining < 0) {
            budgetStatusLabel.setText("Warning: You are over budget!");
            budgetStatusLabel.setForeground(java.awt.Color.RED);
        } 
        else {
            budgetStatusLabel.setText("You are within your budget.");
            budgetStatusLabel.setForeground(java.awt.Color.GREEN);
        }
    }

    //creates a panel displaying recommended hardware
    private JPanel createRecommendationSection(String title, EnumMap<RecommendationTier, Component> recommendations) {

        //create a panel that stacks items vertically
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));

        panel.setBackground(new Color(30, 41, 59));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        //add section title
        JLabel titleLabel = new JLabel(title);

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        panel.add(titleLabel);

        //handle situations where no hardware qualifies
        if (recommendations.isEmpty()) {

            JLabel message = new JLabel("No qualifying components found.");

            message.setForeground(new Color(248, 113, 113));
            panel.add(message);

        } 
        else {

            //display each recommended component
            for (RecommendationTier tier : RecommendationTier.values()) {
                
                Component component = recommendations.get(tier);

                //this tier may have no unique candidate
                if (component == null) {
                    continue;
                }

                //bold the label in the display
                JLabel componentLabel = new JLabel(
                    "<html><b>" + tier.getDisplayName() +
                    "</b><br>" +
                    component.getName() + " - " +
                    currency.format(component.getPrice()) +
                    "</html>"
                );

                componentLabel.setForeground(new Color(203, 213, 225));

                componentLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

                panel.add(componentLabel);
            }
        }
        return panel;
    }

    //apply a recommended CPU/GPU combination
    private void applyRecommendedPair(RecommendedPair pair) {

        //retrieve CPU and GPU dropdowns
        JComboBox<Component> cpuDropdown = componentDropdowns.get(ComponentType.CPU);

        JComboBox<Component> gpuDropdown = componentDropdowns.get(ComponentType.GPU);

        //check if null
        if (cpuDropdown == null || gpuDropdown == null) {
            return;
        }

        //select the recommended hardware
        cpuDropdown.setSelectedItem(pair.getCpu());
        gpuDropdown.setSelectedItem(pair.getGpu());

        //update budget display
        updateBudgetDisplay();
    }
}
