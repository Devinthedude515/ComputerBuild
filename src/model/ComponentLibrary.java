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
        components.add(new Component(
            "Ryzen Sample 7",
            ComponentType.CPU,
            "AMD",
            299.99,
            85.0,
            2025
        ));

        components.add(new Component(
            "Intel Sample i7",
            ComponentType.CPU,
            "Intel",
            329.99,
            88.0,
            2025
        ));

        components.add(new Component(
            "Intel Core i5-7500", 
            ComponentType.CPU,
            "Intel", 
            202.00, 
            63.0, 
            2017
        ));

        components.add(new Component(
            "Intel Core i5-7600K", 
            ComponentType.CPU,
            "Intel", 
            242.00, 
            70.0, 
            2017
        ));

        components.add(new Component(
            "Intel Core i7-7700K", 
            ComponentType.CPU,
            "Intel", 
            339.00, 
            82.0, 
            2017
        ));

        components.add(new Component(
            "AMD Ryzen 5 1600", ComponentType.CPU,
            "AMD", 219.00, 67.0, 2017
        ));

        components.add(new Component(
            "AMD Ryzen 5 1600X", ComponentType.CPU,
            "AMD", 249.00, 72.0, 2017
        ));

        // ============================
        // GPU
        // ============================
        components.add(new Component(
            "RTX Sample 5070",
            ComponentType.GPU,
            "NVIDIA",
            549.99,
            85.0,
            2025
        ));

        components.add(new Component(
            "Radeon Sample 9070",
            ComponentType.GPU,
            "AMD",
            599.99,
            90.0,
            2025
        ));

        components.add(new Component(
            "GeForce GTX 1060 6GB", 
            ComponentType.GPU,
            "NVIDIA", 
            249.00, 
            68.0, 
            2016
        ));

        components.add(new Component(
            "GeForce GTX 1070 8GB", 
            ComponentType.GPU,
            "NVIDIA", 
            379.00, 
            80.0, 
            2016
        ));

        components.add(new Component(
            "GeForce GTX 1080 8GB", 
            ComponentType.GPU,
            "NVIDIA", 
            599.00, 
            90.0, 
            2016
        ));

        components.add(new Component(
            "GeForce GTX 1080 Ti 11GB", 
            ComponentType.GPU,
            "NVIDIA", 
            699.00, 
            98.0, 
            2017
        ));

        components.add(new Component(
            "Radeon RX 560 2GB", 
            ComponentType.GPU,
            "AMD", 
            99.00, 
            46.0, 
            2017
        ));

        components.add(new Component(
            "Radeon RX 570 4GB", 
            ComponentType.GPU,
            "AMD", 
            169.00, 
            65.0, 
            2017
        ));

        components.add(new Component(
            "Radeon RX 580 8GB", 
            ComponentType.GPU,
            "AMD", 
            229.00, 
            72.0, 
            2017
        ));

        // ============================
        // RAM
        // ============================
        components.add(new Component(
            "DDR5 32GB Kit",
            ComponentType.RAM,
            "SampleTech",
            99.99,
            80.0,
            2025
        ));

        components.add(new Component(
            "DDR5 64GB Kit",
            ComponentType.RAM,
            "SampleTech",
            179.99,
            90.0,
            2025
        ));

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
        components.add(new Component(
            "AM5 Sample Motherboard",
            ComponentType.MOTHERBOARD,
            "SampleTech",
            159.99,
            80.0,
            2025
        ));

        components.add(new Component(
            "LGA Sample Motherboard",
            ComponentType.MOTHERBOARD,
            "SampleTech",
            169.99,
            82.0,
            2025
        ));

        // ============================
        // Power Supplies
        // ============================
        components.add(new Component(
            "750W Gold PSU",
            ComponentType.POWER_SUPPLY,
            "SampleTech",
            99.99,
            85.0,
            2025
        ));

        components.add(new Component(
            "850W Gold PSU",
            ComponentType.POWER_SUPPLY,
            "SampleTech",
            129.99,
            90.0,
            2025
        ));

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
