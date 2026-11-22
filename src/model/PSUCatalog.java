package model;

import java.util.List;

public class PSUCatalog {
    
    public static void loadInto(List<Component> components) {

        // 450W Power Supply
        Component psu450 = new Component(
            "Sample 450W Power Supply",
            ComponentType.POWER_SUPPLY,
            "Sample",
            45.00,
            0.0,
            2025
        );

        psu450.setPsuCapacityWatts(450);
        components.add(psu450);

        // 550W Power Supply
        Component psu550 = new Component(
            "Sample 550W Power Supply",
            ComponentType.POWER_SUPPLY,
            "Sample",
            60.00,
            0.0,
            2025
        );

        psu550.setPsuCapacityWatts(550);
        components.add(psu550);

        // 650W PSU
        Component psu650 = new Component(
            "Sample 650W Power Supply",
            ComponentType.POWER_SUPPLY,
            "Sample",
            75.00,
            0.0,
            2025
        );

        psu650.setPsuCapacityWatts(650);
        components.add(psu650);

        // 750W PSU
        Component psu750 = new Component(
            "Sample 750W Power Supply",
            ComponentType.POWER_SUPPLY,
            "Sample",
            90.00,
            0.0,
            2025
        );

        psu750.setPsuCapacityWatts(750);
        components.add(psu750);

        // 850W PSU
        Component psu850 = new Component(
            "Sample 850W Power Supply",
            ComponentType.POWER_SUPPLY,
            "Sample",
            110.00,
            0.0,
            2025
        );

        psu850.setPsuCapacityWatts(850);
        components.add(psu850);
    }
}
