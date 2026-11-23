package model;

import java.util.List;

public class CPUCatalog {

    //populate the existing component list with CPUs
    public static void loadInto(List<Component> components) {

    // =====================================
    // AMD Ryzen 1000 Series - 2017
    // =====================================

    addCPU(components, "AMD Ryzen 3 1200",
        "AMD", 17.00, 54.0, 2017,
        CPUPlatform.AMD_AM4);

    addCPU(components, "AMD Ryzen 5 1400",
        "AMD", 18.00, 62.0, 2017,
        CPUPlatform.AMD_AM4);

    addCPU(components, "AMD Ryzen 5 1600",
        "AMD", 24.00, 67.0, 2017,
        CPUPlatform.AMD_AM4);

    addCPU(components, "AMD Ryzen 5 1600X",
        "AMD", 29.00, 72.0, 2017,
        CPUPlatform.AMD_AM4);

    addCPU(components, "AMD Ryzen 7 1700",
        "AMD", 33.00, 70.0, 2017,
        CPUPlatform.AMD_AM4);


    // =====================================
    // AMD Ryzen 2000 Series - 2018
    // =====================================

    addCPU(components, "AMD Ryzen 5 2600",
        "AMD", 36.00, 74.0, 2018,
        CPUPlatform.AMD_AM4);


    // =====================================
    // AMD Ryzen 3000 Series - 2019
    // =====================================

    addCPU(components, "AMD Ryzen 5 3600",
        "AMD", 63.00, 85.0, 2019,
        CPUPlatform.AMD_AM4);


    // =====================================
    // AMD Ryzen 5000 Series - 2022
    // =====================================

    addCPU(components, "AMD Ryzen 5 5600",
        "AMD", 118.00, 88.0, 2022,
        CPUPlatform.AMD_AM4_RYZEN_5000);


    // =====================================
    // AMD Ryzen 7000 Series - 2023
    // =====================================

    addCPU(components, "AMD Ryzen 5 7600",
        "AMD", 117.00, 94.0, 2023,
        CPUPlatform.AMD_AM5);


    // =====================================
    // AMD Ryzen 9000 Series - 2024
    // =====================================

    addCPU(components, "AMD Ryzen 5 9600X",
        "AMD", 138.00, 98.0, 2024,
        CPUPlatform.AMD_AM5);


    // =====================================
    // Intel 7th Generation - 2017
    // =====================================

    addCPU(components, "Intel Core i3-7100",
        "Intel", 8.00, 50.0, 2017,
        CPUPlatform.INTEL_LGA1151_6_7);

    addCPU(components, "Intel Core i5-7500",
        "Intel", 14.00, 63.0, 2017,
        CPUPlatform.INTEL_LGA1151_6_7);

    addCPU(components, "Intel Core i5-7600K",
        "Intel", 23.00, 70.0, 2017,
        CPUPlatform.INTEL_LGA1151_6_7);

    addCPU(components, "Intel Core i7-7700K",
        "Intel", 61.00, 82.0, 2017,
        CPUPlatform.INTEL_LGA1151_6_7);


    // =====================================
    // Intel 8th Generation - 2017
    // =====================================

    addCPU(components, "Intel Core i5-8400",
        "Intel", 31.00, 78.0, 2017,
        CPUPlatform.INTEL_LGA1151_8_9);

    addCPU(components, "Intel Core i7-8700K",
        "Intel", 66.00, 88.0, 2017,
        CPUPlatform.INTEL_LGA1151_8_9);


    // =====================================
    // Intel 9th Generation - 2019
    // =====================================

    addCPU(components, "Intel Core i5-9400F",
        "Intel", 37.00, 79.0, 2019,
        CPUPlatform.INTEL_LGA1151_8_9);


    // =====================================
    // Intel 10th Generation - 2020
    // =====================================

    addCPU(components, "Intel Core i5-10400F",
        "Intel", 71.00, 86.0, 2020,
        CPUPlatform.INTEL_LGA1200_10);


    // =====================================
    // Intel 12th Generation - 2022
    // =====================================

    addCPU(components, "Intel Core i5-12400F",
        "Intel", 116.00, 88.0, 2022,
        CPUPlatform.INTEL_LGA1700_12_14);


    // =====================================
    // Intel 13th Generation - 2023
    // =====================================

    addCPU(components, "Intel Core i5-13400F",
        "Intel", 144.00, 92.0, 2023,
        CPUPlatform.INTEL_LGA1700_12_14);


    // =====================================
    // Intel 14th Generation - 2024
    // =====================================

    addCPU(components, "Intel Core i5-14400F",
        "Intel", 132.00, 94.0, 2024,
        CPUPlatform.INTEL_LGA1700_12_14);
    }

    private static void addCPU(List<Component> components, String name, String manufacturer, double price, double score, int year, CPUPlatform platform) {

        Component cpu = new Component( name, ComponentType.CPU, manufacturer, price, score, year);

        cpu.setCpuPlatform(platform);
        components.add(cpu);
    }
}
