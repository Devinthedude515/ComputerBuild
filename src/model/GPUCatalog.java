package model;

import java.util.List;


public class GPUCatalog {
    
    public static void loadInto(List<Component> components) {

        // =====================================
        // NVIDIA GTX 10 Series - 2016/2017
        // =====================================

        addGPU(components, "GeForce GTX 1050 Ti 4GB",
            "NVIDIA", 68.00, 52.0, 2016);

        addGPU(components, "GeForce GTX 1060 6GB",
            "NVIDIA", 71.00, 68.0, 2016);

        addGPU(components, "GeForce GTX 1070 8GB",
            "NVIDIA", 92.00, 80.0, 2016);

        addGPU(components, "GeForce GTX 1070 Ti 8GB",
            "NVIDIA", 105.00, 85.0, 2017);

        addGPU(components, "GeForce GTX 1080 8GB",
            "NVIDIA", 113.00, 90.0, 2016);

        addGPU(components, "GeForce GTX 1080 Ti 11GB",
            "NVIDIA", 180.00, 98.0, 2017);


        // =====================================
        // NVIDIA GTX 16 Series - 2019
        // =====================================

        addGPU(components, "GeForce GTX 1660 Super 6GB",
            "NVIDIA", 119.00, 78.0, 2019);


        // =====================================
        // NVIDIA RTX 20 Series - 2019
        // =====================================

        addGPU(components, "GeForce RTX 2060 6GB",
            "NVIDIA", 151.00, 87.0, 2019);


        // =====================================
        // NVIDIA RTX 30 Series - 2021
        // =====================================

        addGPU(components, "GeForce RTX 3060 12GB",
            "NVIDIA", 344.00, 85.0, 2021);

        addGPU(components, "GeForce RTX 3080 10GB",
            "NVIDIA", 450.00, 99.0, 2020);


        // =====================================
        // NVIDIA RTX 40 Series - 2023
        // =====================================

        addGPU(components, "GeForce RTX 4060 8GB",
            "NVIDIA", 350.00, 88.0, 2023);

        addGPU(components, "GeForce RTX 4070 12GB",
            "NVIDIA", 635.00, 98.0, 2023);

        addGPU(components, "GeForce RTX 4080 16GB",
            "NVIDIA", 1180.00, 100.0, 2022);


        // =====================================
        // NVIDIA RTX 50 Series - 2025
        // =====================================

        addGPU(components, "GeForce RTX 5060 8GB",
            "NVIDIA", 420.00, 94.0, 2025);


        // =====================================
        // AMD Radeon RX 400 Series - 2016
        // =====================================

        addGPU(components, "Radeon RX 480 8GB",
            "AMD", 49.00, 66.0, 2016);


        // =====================================
        // AMD Radeon RX 500 Series - 2017
        // =====================================

        addGPU(components, "Radeon RX 560 2GB",
            "AMD", 35.00, 46.0, 2017);

        addGPU(components, "Radeon RX 570 4GB",
            "AMD", 50.00, 65.0, 2017);

        addGPU(components, "Radeon RX 580 8GB",
            "AMD", 75.00, 72.0, 2017);


        // =====================================
        // AMD Radeon RX Vega Series - 2017
        // =====================================

        addGPU(components, "Radeon RX Vega 56 8GB",
            "AMD", 85.00, 84.0, 2017);


        // =====================================
        // AMD Radeon RX 5000 Series - 2019
        // =====================================

        addGPU(components, "Radeon RX 5700 XT 8GB",
            "AMD", 167.00, 95.0, 2019);


        // =====================================
        // AMD Radeon RX 6000 Series - 2021
        // =====================================

        addGPU(components, "Radeon RX 6600 8GB",
            "AMD", 221.00, 80.0, 2021);

        addGPU(components, "Radeon RX 6700 XT 12GB",
            "AMD", 352.00, 92.0, 2021);

        addGPU(components, "Radeon RX 6800 XT 16GB",
            "AMD", 476.00, 99.0, 2020);


        // =====================================
        // AMD Radeon RX 7000 Series - 2023
        // =====================================

        addGPU(components, "Radeon RX 7600 8GB",
            "AMD", 277.00, 86.0, 2023);


        // =====================================
        // AMD Radeon RX 9000 Series - 2025
        // =====================================

        addGPU(components, "Radeon RX 9070 XT 16GB",
            "AMD", 886.00, 100.0, 2025);
    }

    private static void addGPU(List<Component> components, String name, String manufacturer, double price, double score, int year) {

        components.add(new Component( name, ComponentType.GPU, manufacturer, price, score, year));
    }
    
}
