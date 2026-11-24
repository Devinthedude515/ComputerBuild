package model;
import java.util.*;

public class ComponentLibrary {

    //create a list to hold the components
    private ArrayList<Component> components;

    //create a constructor for the component library
    public ComponentLibrary() {
        components = new ArrayList<>();
        //create some sample components to add to the library
        loadSampleComponents();
    }

    //Populate llibrary with sample components
    private void loadSampleComponents() {

        // ============================
        // CPU
        // ============================
        
        CPUCatalog.loadInto(components);

        // ============================
        // GPU
        // ============================
        
        GPUCatalog.loadInto(components);

        // ============================
        // Motherboard
        // ============================
       
        MotherboardCatalog.loadInto(components);

        // ============================
        // Power Supplies
        // ============================

        PSUCatalog.loadInto(components);

        // ============================
        // Generic RAM, CPU Cooler, Storage, and Cases
        // ============================
        loadGenericComponents();
    }

    // Retrieve all components
    public ArrayList<Component> getAllComponents() {

        return new ArrayList<>(components);
    }

    // Retrieve components by category
    public ArrayList<Component> getComponentsByType(ComponentType type) {

        ArrayList<Component> filteredComponents = new ArrayList<>();

        for (Component component : components) {

            if (component.getType() == type) {
                filteredComponents.add(component);
            }
        }

        return filteredComponents;
    }

    // Retrieve components under a specified price
    public ArrayList<Component> getComponentsWithinBudget(double budget) {

        ArrayList<Component> filteredComponents = new ArrayList<>();

        for (Component component : components) {

            if (component.getPrice() <= budget) {
                filteredComponents.add(component);
            }
        }

        return filteredComponents;
    }

    // Add a new component to the library
    public void addComponent(Component component) {
        
        if (component != null) {
            components.add(component);
        }
    }

    // Return total number of components
    public int getComponentCount() {
        return components.size();
    }

    private void loadGenericComponents() {

        // ============================
        // GENERIC RAM
        // ============================

        addGenericRAM("16GB DDR4 3200MHz Kit", 135.00, MemoryType.DDR4);

        addGenericRAM("32GB DDR4 3200MHz Kit", 210.00, MemoryType.DDR4);

        addGenericRAM("16GB DDR5 Kit", 260.00, MemoryType.DDR5);

        addGenericRAM("32GB DDR5 Kit", 430.00, MemoryType.DDR5);

        addGenericRAM("64GB DDR5 Kit", 950.00, MemoryType.DDR5);

        // ============================
        // GENERIC STORAGE
        // ============================

        addGenericComponent("1TB SSD", ComponentType.STORAGE, 150.00);

        addGenericComponent("2TB SSD", ComponentType.STORAGE, 230.00);

        addGenericComponent("2TB HDD", ComponentType.STORAGE, 85.00);

        // ============================
        // GENERIC CASES
        // ============================

        addGenericComponent("Small Tower Case", ComponentType.CASE, 65.00);

        addGenericComponent("Mid Tower Case", ComponentType.CASE, 90.00);

        addGenericComponent("Full Tower Case", ComponentType.CASE, 140.00);

        // ============================
        // GENERIC CPU COOLERS
        // ============================

        addGenericComponent("Standard Air Cooler", ComponentType.CPU_COOLER, 35.00);

        addGenericComponent("Performance Air Cooler", ComponentType.CPU_COOLER, 65.00);

        addGenericComponent("240mm Liquid Cooler", ComponentType.CPU_COOLER, 120.00);
    }

    // Helper for generic supporting components
    private void addGenericComponent(String name, ComponentType type, double price) {

        Component component = new Component(name, type, "Generic", price, 0.0, 0);

        components.add(component);
    }

    // RAM needs its memory type for motherboard compatibility
    private void addGenericRAM(String name, double price, MemoryType memoryType) {

        Component ram = new Component(name, ComponentType.RAM, "Generic", price, 0.0, 0);

        ram.setMemoryType(memoryType);

        components.add(ram);
    }
    
}
