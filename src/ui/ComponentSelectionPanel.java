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
import service.BuildValidationService;
import service.PSURecommendationService;
import service.RecommendationTier;
import service.RecommendedPair;


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
    private JLabel psuRecommendationLabel;

    //create a Consumer of PCBuild to handle submission
    private Consumer<PCBuild> onSubmit;

    //create number format for currency
    private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    //create vaariable for PSU recommendations
    private PSURecommendationService powerService = new PSURecommendationService();


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

        ComponentRecommendationPanel combinedRecommendationsPanel = new ComponentRecommendationPanel(request, library, this::applyRecommendedPair);

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
        JPanel budgetPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        budgetPanel.setBackground(new java.awt.Color(15, 23, 42));

        totalCostLabel = new JLabel();
        remainingBudgetLabel = new JLabel();
        budgetStatusLabel = new JLabel();

        //inform users about legacy component pricing
        JLabel legacyPriceNotice = new JLabel(
            "<html>Note: Legacy hardware prices are estimates based on " +
            "secondhand market values and may vary by seller and condition.</html>"
        );

        //notify user to select GPU and CPU before PSU
        psuRecommendationLabel = new JLabel("PSU: Select a CPU and GPU first.");

        UIStyles.styleLabel(totalCostLabel);
        UIStyles.styleLabel(remainingBudgetLabel);
        UIStyles.styleLabel(budgetStatusLabel);
        UIStyles.styleLabel(legacyPriceNotice);
        UIStyles.styleLabel(psuRecommendationLabel);

        budgetPanel.add(totalCostLabel);
        budgetPanel.add(remainingBudgetLabel);
        budgetPanel.add(budgetStatusLabel);
        budgetPanel.add(psuRecommendationLabel);
        budgetPanel.add(legacyPriceNotice);
        
        // Buttons
        JButton backButton = new JButton("Back");
        JButton submitButton = new JButton("Submit Build");

        UIStyles.styleMenuButton(backButton);
        UIStyles.styleMenuButton(submitButton);

        backButton.addActionListener(e -> onBack.run());
        submitButton.addActionListener(e -> { 

            //create the validation service
            BuildValidationService validationService = new BuildValidationService();

            //validate the current build
            ArrayList<String> errors = validationService.validateBuild(request, build);

            // Block submission if errors exist
            if (!errors.isEmpty()) {

                String message = "Please fix the following issues:\n\n- " +
                    String.join("\n- ", errors);

                JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Build Validation Failed",
                    JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Check whether the build exceeds the budget
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

                // Cancel submission if user selects No
                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
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

        //skip the power supply until the user selects a CPU and GPU
        if (type != ComponentType.POWER_SUPPLY && type != ComponentType.MOTHERBOARD) {

            //keep your existing year-filtered component loop here
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

        } 
        else {

            //wait for the user to select a CPU and GPU
            dropdown.setEnabled(false);
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

            //update motherboard options whenever CPU changes
            if (type == ComponentType.CPU) {
                updateMotherboardOptions();
            }

            //update the PSU options based on CPU and GPU selections
            if (type == ComponentType.CPU || type == ComponentType.GPU) {
                updatePowerSupplyOptions();
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

    private void updatePowerSupplyOptions() {

        //retrieve the PSU dropdown
        JComboBox<Component> psuDropdown = componentDropdowns.get(ComponentType.POWER_SUPPLY);

        if (psuDropdown == null) {
            return;
        }

        //remember the previously selected PSU
        Component previousPSU = (Component) psuDropdown.getSelectedItem();

        //retrieve the selected CPU and GPU
        Component cpu = build.getSelectedComponent(ComponentType.CPU);

        Component gpu = build.getSelectedComponent(ComponentType.GPU);

        //clear existing PSU options and selection
        psuDropdown.removeAllItems();
        psuDropdown.addItem(null);

        build.removeComponent(ComponentType.POWER_SUPPLY);

        //both components must be selected
        if (cpu == null || gpu == null) {

            psuDropdown.setEnabled(false);

            psuRecommendationLabel.setText("PSU: Select a CPU and GPU first.");

            return;
        }

        //calculate minimum PSU wattage
        int minimumWatts = powerService.getMinimumSystemWatts(cpu, gpu);

        System.out.println("===== PSU REQUIREMENT TEST =====");

        System.out.println("CPU: " + cpu.getName());
        System.out.println("GPU: " + gpu.getName());
        System.out.println("Minimum Watts: " + minimumWatts);

        if (minimumWatts < 0) {

            psuDropdown.setEnabled(false);

            psuRecommendationLabel.setText("PSU: Power requirement unavailable.");

            return;
        }

        //display recommended wattage
        psuRecommendationLabel.setText("Recommended PSU: " + minimumWatts + "W or higher");

        //add only compatible PSU wattages
        for (Component psu : library.getComponentsByType(ComponentType.POWER_SUPPLY)) {

          /*
            if (psu.getPsuCapacityWatts() >= minimumWatts) {
                    psuDropdown.addItem(psu);
            }
          */  

            int capacity = psu.getPsuCapacityWatts();

            if (capacity >= minimumWatts) {

                psuDropdown.addItem(psu);

                System.out.println(
                    "QUALIFIED: " + psu.getName() + " | " + capacity + "W"
                );

            } 
            else {

                System.out.println("EXCLUDED: " + psu.getName() + " | " + capacity + "W");
            }

            // Print the number of available PSUs
            System.out.println("Qualifying PSU count: " + (psuDropdown.getItemCount() - 1));
                        
        }

        //enable dropdown if at least one PSU qualifies
        boolean hasOptions = psuDropdown.getItemCount() > 1;

        psuDropdown.setEnabled(hasOptions);

        //restore previous PSU if it remains eligible
        if (previousPSU != null) {

            for (int i = 0; i < psuDropdown.getItemCount(); i++) {

                if (psuDropdown.getItemAt(i) == previousPSU) {

                    psuDropdown.setSelectedItem(previousPSU);
                    break;
                }
            }
        }

        if (!hasOptions) {

            psuRecommendationLabel.setText("No available PSU meets the " + minimumWatts + "W requirement.");
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

    private void updateMotherboardOptions() {

        //retrieve motherboard dropdown
        JComboBox<Component> motherboardDropdown = componentDropdowns.get(ComponentType.MOTHERBOARD);

        if (motherboardDropdown == null) {
            return;
        }

        //remember previously selected motherboard
        Component previousMotherboard = (Component) motherboardDropdown.getSelectedItem();

        // Retrieve selected CPU
        Component cpu = build.getSelectedComponent(ComponentType.CPU);

        //clear old motherboard options
        motherboardDropdown.removeAllItems();
        motherboardDropdown.addItem(null);
        motherboardDropdown.setEnabled(false);

        //remove any previous motherboard from the build
        build.removeComponent(ComponentType.MOTHERBOARD);

        //stop if CPU is missing or unclassified
        if (cpu == null || cpu.getCpuPlatform() == null) {
            return;
        }

        //check each available motherboard
        for (Component motherboard : library.getComponentsByType(ComponentType.MOTHERBOARD)) {

            //skip unclassified or mismatched platforms
            if (motherboard.getCpuPlatform() != cpu.getCpuPlatform()) {
                continue;
            }

            //skip motherboards released after target year
            if (motherboard.getReleaseYear() > request.getTargetYear()) {
                continue;
            }

            //add matching motherboard
            motherboardDropdown.addItem(motherboard);
        }

        //dnable when compatible motherboards exist
        boolean hasOptions = motherboardDropdown.getItemCount() > 1;

        motherboardDropdown.setEnabled(hasOptions);

        //restore previous motherboard if still compatible
        if (previousMotherboard != null) {

            for (int i = 0; i < motherboardDropdown.getItemCount(); i++) {

                if (motherboardDropdown.getItemAt(i) == previousMotherboard) {

                    motherboardDropdown.setSelectedItem(previousMotherboard);

                    break;
                }
            }
        }
    }
}
