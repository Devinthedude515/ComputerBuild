package model;

import java.util.List;


public class RAMCatalog {
    
    public static void loadInto(List<Component> components) {

        addRAM(components,
            "16GB DDR4 3200MHz Kit (2x8GB)",
            78.00, 2017, MemoryType.DDR4);

        addRAM(components,
            "32GB DDR4 3200MHz Kit (2x16GB)",
            173.00, 2017, MemoryType.DDR4);

        addRAM(components,
            "32GB DDR5 Kit (2x16GB)",
            429.00, 2021, MemoryType.DDR5);

        addRAM(components,
            "64GB DDR5 Kit (2x32GB)",
            800.00, 2021, MemoryType.DDR5);
    }

    private static void addRAM(List<Component> components, String name, double price, int year, MemoryType memoryType) {

        Component ram = new Component(name, ComponentType.RAM, "Generic", price, 0.0, year);

        ram.setMemoryType(memoryType);
        components.add(ram);
    }
}
