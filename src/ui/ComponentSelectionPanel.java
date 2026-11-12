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
        
        addComponentRow(selectionPanel, ComponentType.CPU, "CPU");
        addComponentRow(selectionPanel, ComponentType.GPU, "Graphics Card");
        addComponentRow(selectionPanel, ComponentType.RAM, "Memory");
        addComponentRow(selectionPanel, ComponentType.STORAGE, "Storage");
        addComponentRow(selectionPanel, ComponentType.MOTHERBOARD, "Motherboard");
        addComponentRow(selectionPanel, ComponentType.POWER_SUPPLY, "Power Supply");
        addComponentRow(selectionPanel, ComponentType.CASE, "Case");
        addComponentRow(selectionPanel, ComponentType.CPU_COOLER, "CPU Cooler");

        add(selectionPanel, BorderLayout.CENTER);

          // Budget information
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

            // Test whether the button is responding
            JOptionPane.showMessageDialog(
                this,
                "Submit button was clicked!"
            );

            // Test the build's contents
            System.out.println("===== PC BUILD TEST =====");

            for (Component component : build.getSelectedComponents()) {
                System.out.println(component.getName());
            }

            System.out.println("Total: $" + build.calculateTotalPrice());

            // Submit the build
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

    // Creates a dropdown for one component category
    private void addComponentRow(JPanel panel, ComponentType type, String labelText) {
        JLabel label = new JLabel(labelText);
        UIStyles.styleLabel(label);

        JComboBox<Component> dropdown = new JComboBox<>();

        // Add an empty selection
        dropdown.addItem(null);

        // Retrieve components belonging to this category
        ArrayList<Component> availableComponents = library.getComponentsByType(type);

        for (Component component : availableComponents) {
            dropdown.addItem(component);
        }

        // Display component names and prices
        dropdown.setRenderer(new DefaultListCellRenderer() {

            @Override
            public java.awt.Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
            ) {

                super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
                );

                if (value == null) {
                    setText("Select a component...");
                } 
                else {
                    Component component = (Component) value;

                    setText(
                        component.getName() + " - " +
                        currency.format(component.getPrice())
                    );
                }

                return this;
            }
        });

        UIStyles.styleComboBox(dropdown);

        // Update the build whenever the selection changes
        dropdown.addActionListener(e -> {

            Component selected = (Component) dropdown.getSelectedItem();

            if (selected == null) {
                build.removeComponent(type);
            } else {
                build.selectComponent(selected);
            }

            updateBudgetDisplay();
        });

        componentDropdowns.put(type, dropdown);

        panel.add(label);
        panel.add(dropdown);
    }

     // Updates the total and remaining budget
    private void updateBudgetDisplay() {

        double total = build.calculateTotalPrice();
        double remaining = request.getBudget() - total;

        totalCostLabel.setText(
            "Selected Components: " + currency.format(total)
        );

        remainingBudgetLabel.setText(
            "Remaining Budget: " + currency.format(remaining)
        );

        if (remaining < 0) {
            budgetStatusLabel.setText("Warning: You are over budget!");
            budgetStatusLabel.setForeground(java.awt.Color.RED);
        } else {
            budgetStatusLabel.setText("You are within your budget.");
            budgetStatusLabel.setForeground(java.awt.Color.GREEN);
        }
    }
}
