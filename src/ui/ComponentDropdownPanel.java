package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Locale;
import javax.swing.*;
import model.BuildRequest;
import model.Component;
import model.ComponentLibrary;
import model.ComponentType;
import model.PCBuild;
import service.PSURecommendationService;
import service.RecommendedPair;


public class ComponentDropdownPanel extends JPanel{
    
    //existing build information
    private BuildRequest request;
    private ComponentLibrary library;
    private PCBuild build;
    private BuildSummaryPanel summaryPanel;

    //component dropdowns
    private EnumMap<ComponentType, JComboBox<Component>> componentDropdowns;

    //PSU recommendation service
    private PSURecommendationService powerService = new PSURecommendationService();

    //currency formatting
    private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    public ComponentDropdownPanel(BuildRequest request, ComponentLibrary library, PCBuild build, BuildSummaryPanel summaryPanel) {

        this.request = request;
        this.library = library;
        this.build = build;
        this.summaryPanel = summaryPanel;

        componentDropdowns = new EnumMap<>(ComponentType.class);

        //layout for eight component categories
        setLayout(new GridLayout(8, 2, 15, 15));
        setBackground(new Color(15, 23, 42));

        //create each component dropdown
        addComponentRow(ComponentType.CPU, "CPU");
        addComponentRow(ComponentType.GPU, "Graphics Card");
        addComponentRow(ComponentType.RAM, "Memory");
        addComponentRow(ComponentType.STORAGE, "Storage");
        addComponentRow(ComponentType.MOTHERBOARD, "Motherboard");
        addComponentRow(ComponentType.POWER_SUPPLY, "Power Supply");
        addComponentRow(ComponentType.CASE, "Case");
        addComponentRow(ComponentType.CPU_COOLER, "CPU Cooler");
    }

    //create a dropdown for one component category
    private void addComponentRow(ComponentType type, String labelText) {

        JLabel label = new JLabel(labelText);
        UIStyles.styleLabel(label);

        JComboBox<Component> dropdown = new JComboBox<>();

        //empty selection
        dropdown.addItem(null);

        //motherboards and PSUs depend on other selections
        if (type != ComponentType.POWER_SUPPLY && type != ComponentType.MOTHERBOARD  && type != ComponentType.RAM) {

            ArrayList<Component> availableComponents = library.getComponentsByType(type);

            for (Component component : availableComponents) {

                // Preserve existing CPU/GPU year filtering
                if (type == ComponentType.CPU || type == ComponentType.GPU) {

                    if (component.getReleaseYear() > request.getTargetYear()) {

                        continue;
                    }
                }

                dropdown.addItem(component);
            }

        } 
        else {
                dropdown.setEnabled(false);
        }

        //display component name and price
        dropdown.setRenderer(new DefaultListCellRenderer() {

            @Override
            public java.awt.Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
                );

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

        //update build whenever selection changes
        dropdown.addActionListener(e -> {

            Component selected = (Component) dropdown.getSelectedItem();

            if (selected == null) {
                
                build.removeComponent(type);
            } 
            else {
                build.selectComponent(selected);
            }

            //update motherboard choices
            if (type == ComponentType.CPU) {
                updateMotherboardOptions();
            }

            //update PSU choices
            if (type == ComponentType.CPU || type == ComponentType.GPU) {
                updatePowerSupplyOptions();
            }

            //update RAM choices whenever motherboard changes
            if (type == ComponentType.MOTHERBOARD) {
                updateRamOptions();
            }

            //refresh budget using our existing summary panel
            summaryPanel.refreshBudget();
        });

        componentDropdowns.put(type, dropdown);

        add(label);
        add(dropdown);
    }

    //filter motherboards based on selected CPU
    private void updateMotherboardOptions() {

        JComboBox<Component> motherboardDropdown = componentDropdowns.get(ComponentType.MOTHERBOARD);

        if (motherboardDropdown == null) {
            return;
        }

        Component previousMotherboard = (Component) motherboardDropdown.getSelectedItem();

        Component cpu = build.getSelectedComponent(ComponentType.CPU);

        motherboardDropdown.removeAllItems();
        motherboardDropdown.addItem(null);
        motherboardDropdown.setEnabled(false);

        build.removeComponent(ComponentType.MOTHERBOARD);

        if (cpu == null || cpu.getCpuPlatform() == null) {
            return;
        }

        for (Component motherboard : library.getComponentsByType(ComponentType.MOTHERBOARD)) {

            if (motherboard.getCpuPlatform() !=
                cpu.getCpuPlatform()) {

                continue;
            }

            if (motherboard.getReleaseYear() > request.getTargetYear()) {

                continue;
            }

            motherboardDropdown.addItem(motherboard);
        }

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


     //filter PSUs based on CPU/GPU combination
    private void updatePowerSupplyOptions() {

        JComboBox<Component> psuDropdown = componentDropdowns.get(ComponentType.POWER_SUPPLY);

        if (psuDropdown == null) {
            return;
        }

        Component previousPSU = (Component) psuDropdown.getSelectedItem();

        Component cpu = build.getSelectedComponent(ComponentType.CPU);

        Component gpu = build.getSelectedComponent(ComponentType.GPU);

        psuDropdown.removeAllItems();
        psuDropdown.addItem(null);
        psuDropdown.setEnabled(false);

        build.removeComponent(ComponentType.POWER_SUPPLY);

        if (cpu == null || gpu == null) {

            summaryPanel.setPsuRecommendation("PSU: Select a CPU and GPU first.");

            return;
        }

        int minimumWatts = powerService.getMinimumSystemWatts(cpu, gpu);

        if (minimumWatts < 0) {

            summaryPanel.setPsuRecommendation("PSU: Power requirement unavailable.");

            return;
        }

        summaryPanel.setPsuRecommendation("Recommended PSU: " + minimumWatts + "W or higher");

        //add only sufficiently rated power supplies
        for (Component psu : library.getComponentsByType(ComponentType.POWER_SUPPLY)) {

            if (psu.getPsuCapacityWatts() >= minimumWatts) {
                psuDropdown.addItem(psu);
            }
        }

        boolean hasOptions = psuDropdown.getItemCount() > 1;

        psuDropdown.setEnabled(hasOptions);

        //restore previous PSU if it still qualifies
        if (previousPSU != null) {

            for (int i = 0; i < psuDropdown.getItemCount(); i++) {

                if (psuDropdown.getItemAt(i) == previousPSU) {

                    psuDropdown.setSelectedItem(previousPSU);
                    break;
                }
            }
        }

        if (!hasOptions) {

            summaryPanel.setPsuRecommendation("No available PSU meets the " + minimumWatts + "W requirement.");
        }
    }

    //apply a recommended CPU/GPU combination
    public void applyRecommendedPair(RecommendedPair pair) {

        if (pair == null) {
            return;
        }

        JComboBox<Component> cpuDropdown = componentDropdowns.get(ComponentType.CPU);

        JComboBox<Component> gpuDropdown = componentDropdowns.get(ComponentType.GPU);

        if (cpuDropdown == null || gpuDropdown == null) {
            return;
        }

        //these selections trigger their action listeners
        cpuDropdown.setSelectedItem(pair.getCpu());
        gpuDropdown.setSelectedItem(pair.getGpu());

        summaryPanel.refreshBudget();
    }

    //method loads an already created build for editting
    public void loadExistingBuild(PCBuild existingBuild) {

        //check for a valid saved build
        if (existingBuild == null) {
            return;
        }

        //CPU and GPU must load first so their
        //motherboard and PSU filters can update
        ComponentType[] loadingOrder = {
            ComponentType.CPU,
            ComponentType.GPU,
            ComponentType.MOTHERBOARD,
            ComponentType.RAM,
            ComponentType.POWER_SUPPLY,
            ComponentType.STORAGE,
            ComponentType.CASE,
            ComponentType.CPU_COOLER
        };

        //go through each saved component
        for (ComponentType type : loadingOrder) {

            Component savedComponent = existingBuild.getSelectedComponent(type);

            JComboBox<Component> dropdown = componentDropdowns.get(type);

            //skip unavailable selections
            if (savedComponent == null || dropdown == null) {
                continue;
            }

            boolean found = false;

            //find the saved component in the dropdown
            for (int i = 0; i < dropdown.getItemCount(); i++) {

                Component option = dropdown.getItemAt(i);

                if (option != null && option.getName().equals(savedComponent.getName())) {

                    //selecting the item automatically triggers our existing listeners
                    dropdown.setSelectedIndex(i);

                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Could not restore component: " + savedComponent.getName());
            }
        }

        //refresh budget after loading all components
        summaryPanel.refreshBudget();
    }

    private void updateRamOptions() {

        JComboBox<Component> ramDropdown = componentDropdowns.get(ComponentType.RAM);

        if (ramDropdown == null) {
            return;
        }

        Component previousRAM = (Component) ramDropdown.getSelectedItem();

        Component motherboard = build.getSelectedComponent(ComponentType.MOTHERBOARD);

        //clear old options
        ramDropdown.removeAllItems();
        ramDropdown.addItem(null);
        ramDropdown.setEnabled(false);

        build.removeComponent(ComponentType.RAM);

        //wait for a classified motherboard
        if (motherboard == null || motherboard.getMemoryType() == null) {
            return;
        }

        // Add compatible RAM only
        for (Component ram : library.getComponentsByType(ComponentType.RAM)) {

            if (ram.getMemoryType() == motherboard.getMemoryType()) {

                ramDropdown.addItem(ram);
            }
        }

        ramDropdown.setEnabled(ramDropdown.getItemCount() > 1);

        // Restore previous RAM if still compatible
        if (previousRAM != null) {

            for (int i = 0; i < ramDropdown.getItemCount(); i++) {

                if (ramDropdown.getItemAt(i) == previousRAM) {
                    ramDropdown.setSelectedItem(previousRAM);
                    break;
                }
            }
        }
    }
}
