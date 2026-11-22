package model;

import java.util.List;


public class GPUCatalog {
    
     public static void loadInto(List<Component> components) {

        //Nvidia
        components.add(new Component(
            "GeForce GTX 1050 Ti 4GB",
            ComponentType.GPU,
            "NVIDIA",
            59.00,
            52.0,
            2016
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
            "GeForce GTX 1070 Ti 8GB",
            ComponentType.GPU,
            "NVIDIA",
            92.00,
            85.0,
            2017
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

        //AMD
        components.add(new Component(
            "Radeon RX Vega 56 8GB",
            ComponentType.GPU,
            "AMD",
            85.00,
            84.0,
            2017
        ));

        components.add(new Component(
            "Radeon RX 480 8GB",
            ComponentType.GPU,
            "AMD",
            49.00,
            66.0,
            2016
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
    }
}
