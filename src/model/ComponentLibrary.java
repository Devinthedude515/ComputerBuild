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
        // RAM
        // ============================
        
        RAMCatalog.loadInto(components);

        // ============================
        // Storage
        // ============================
        components.add(new Component(
            "NVMe 1TB SSD",
            ComponentType.STORAGE,
            "SampleTech",
            69.99,
            75.0,
            2025
        ));

        components.add(new Component(
            "NVMe 2TB SSD",
            ComponentType.STORAGE,
            "SampleTech",
            119.99,
            85.0,
            2025
        ));

        // ============================
        // Motherboard
        // ============================
       
        MotherboardCatalog.loadInto(components);

        // ============================
        // Power Supplies
        // ============================

        PSUCatalog.loadInto(components);

        // ============================
        // Cases
        // ============================
        components.add(new Component(
            "Mid-Tower Case",
            ComponentType.CASE,
            "SampleTech",
            79.99,
            80.0,
            2025
        ));

        components.add(new Component(
            "Airflow Mid-Tower Case",
            ComponentType.CASE,
            "SampleTech",
            109.99,
            90.0,
            2025
        ));

        // ============================
        // CPU Cooler
        // ============================
        components.add(new Component(
            "Air CPU Cooler",
            ComponentType.CPU_COOLER,
            "SampleTech",
            39.99,
            75.0,
            2025
        ));

        components.add(new Component(
            "240mm Liquid Cooler",
            ComponentType.CPU_COOLER,
            "SampleTech",
            89.99,
            90.0,
            2025
        ));
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
    
}
