package model;

import java.util.List;

public class MotherboardCatalog {
    
    public static void loadInto(List<Component> components) {

        // =====================================
        // AMD AM4 - Ryzen 1000/2000/3000
        // =====================================

        addBoard(components, "AMD B350-Class Motherboard",
            "AMD Platform", 65.00, 2017,
            CPUPlatform.AMD_AM4);

        addBoard(components, "AMD B450-Class Motherboard",
            "AMD Platform", 76.00, 2018,
            CPUPlatform.AMD_AM4);


        // =====================================
        // AMD AM4 - Ryzen 5000
        // =====================================

        addBoard(components, "AMD B550-Class Motherboard",
            "AMD Platform", 87.00, 2020,
            CPUPlatform.AMD_AM4_RYZEN_5000);


        // =====================================
        // AMD AM5 - Ryzen 7000/9000
        // =====================================

        addBoard(components, "AMD B650-Class Motherboard",
            "AMD Platform", 120.00, 2022,
            CPUPlatform.AMD_AM5);

        addBoard(components, "AMD B850-Class Motherboard",
            "AMD Platform", 112.00, 2025,
            CPUPlatform.AMD_AM5);


        // =====================================
        // Intel 6th / 7th Generation
        // =====================================

        addBoard(components, "Intel B250-Class Motherboard",
            "Intel Platform", 45.00, 2017,
            CPUPlatform.INTEL_LGA1151_6_7);


        // =====================================
        // Intel 8th / 9th Generation
        // =====================================

        addBoard(components, "Intel Z370-Class Motherboard",
            "Intel Platform", 75.00, 2017,
            CPUPlatform.INTEL_LGA1151_8_9);

        addBoard(components, "Intel B365-Class Motherboard",
            "Intel Platform", 60.00, 2018,
            CPUPlatform.INTEL_LGA1151_8_9);


        // =====================================
        // Intel 10th Generation
        // =====================================

        addBoard(components, "Intel B460-Class Motherboard",
            "Intel Platform", 45.00, 2020,
            CPUPlatform.INTEL_LGA1200_10);


        // =====================================
        // Intel 12th / 13th / 14th Generation
        // =====================================

        addBoard(components, "Intel B660 DDR4-Class Motherboard",
            "Intel Platform", 83.00, 2022,
            CPUPlatform.INTEL_LGA1700_12_14);

        addBoard(components, "Intel B760 DDR4-Class Motherboard",
            "Intel Platform", 66.00, 2023,
            CPUPlatform.INTEL_LGA1700_12_14);
    }

    private static void addBoard(List<Component> components, String name, String manufacturer, double price, int year, CPUPlatform platform) {

        Component board = new Component(name, ComponentType.MOTHERBOARD, manufacturer, price, 0.0, year);

        board.setCpuPlatform(platform);

        //all newly added Intel LGA1700 boards use DDR4.
        //AMD AM5 boards use DDR5.
        if (platform == CPUPlatform.AMD_AM5) {
            board.setMemoryType(MemoryType.DDR5);
        } 
        else {
            board.setMemoryType(MemoryType.DDR4);
        }
        components.add(board);
    }
}
