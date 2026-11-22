package model;

import java.util.List;

public class MotherboardCatalog {
    
    public static void loadInto(List<Component> components) {

        // AMD Ryzen 1000 series
        Component amdBoard = new Component(
            "AMD B350-Class Motherboard",
            ComponentType.MOTHERBOARD,
            "AMD Platform",
            65.00,
            0.0,
            2017
        );

        amdBoard.setCpuPlatform(CPUPlatform.AMD_AM4);
        components.add(amdBoard);

        // Intel 6th / 7th generation
        Component intel7Board = new Component(
            "Intel B250-Class Motherboard",
            ComponentType.MOTHERBOARD,
            "Intel Platform",
            45.00,
            0.0,
            2017
        );

        intel7Board.setCpuPlatform(
            CPUPlatform.INTEL_LGA1151_6_7
        );

        components.add(intel7Board);

        // Intel 8th / 9th generation
        Component intel8Board = new Component(
            "Intel Z370-Class Motherboard",
            ComponentType.MOTHERBOARD,
            "Intel Platform",
            75.00,
            0.0,
            2017
        );

        intel8Board.setCpuPlatform(
            CPUPlatform.INTEL_LGA1151_8_9
        );

        components.add(intel8Board);
    }
}
