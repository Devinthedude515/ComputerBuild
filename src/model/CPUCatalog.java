package model;

import java.util.List;

public class CPUCatalog {

    //populate the existing component list with CPUs
    public static void loadInto(List<Component> components) {

        //AMD
        Component ryzen1200 = new Component(
            "AMD Ryzen 3 1200",
            ComponentType.CPU,
            "AMD",
            17.00,
            54.0,
            2017
        );

        //assign the compatible motherboard platform
        ryzen1200.setCpuPlatform(CPUPlatform.AMD_AM4);

        //add the CPU to the library
        components.add(ryzen1200);

        Component ryzen1400 = new Component(
            "AMD Ryzen 5 1400",
            ComponentType.CPU,
            "AMD",
            18.00,
            62.0,
            2017
        );

        //assign the compatible motherboard platform
        ryzen1400.setCpuPlatform(CPUPlatform.AMD_AM4);

        //add the CPU to the library
        components.add(ryzen1400);

        Component ryzen1600 = new Component(
            "AMD Ryzen 5 1600",
            ComponentType.CPU,
            "AMD",
            219.00,
            67.0,
            2017
        );

        //assign the compatible motherboard platform
        ryzen1600.setCpuPlatform(CPUPlatform.AMD_AM4);

        //add the CPU to the library
        components.add(ryzen1600);

        Component ryzen1600X = new Component(
            "AMD Ryzen 5 1600X", 
            ComponentType.CPU,
            "AMD", 
            249.00, 
            72.0, 
            2017
        );

        //assign the compatible motherboard platform
        ryzen1600X.setCpuPlatform(CPUPlatform.AMD_AM4);

        //add the CPU to the library
        components.add(ryzen1600X);

        Component ryzen1700 = new Component(
            "AMD Ryzen 7 1700",
            ComponentType.CPU,
            "AMD",
            33.00,
            70.0,
            2017
        );

        //assign the compatible motherboard platform
        ryzen1700.setCpuPlatform(CPUPlatform.AMD_AM4);

        //add the CPU to the library
        components.add(ryzen1700);

        //Intel
        Component intel7100 = new Component(
            "Intel Core i3-7100",
            ComponentType.CPU,
            "Intel",
            8.00,
            50.0,
            2017
        );

        //assign the compatible motherboard platform
        intel7100.setCpuPlatform(CPUPlatform.INTEL_LGA1151_8_9);

        //add the CPU to the library
        components.add(intel7100);

        Component intel8400 = new Component(
            "Intel Core i5-8400",
            ComponentType.CPU,
            "Intel",
            31.00,
            78.0,
            2017
        );

        //assign the compatible motherboard platform
        intel8400.setCpuPlatform(CPUPlatform.INTEL_LGA1151_8_9);

        //add the CPU to the library
        components.add(intel8400);

        Component intel8700K = new Component(
            "Intel Core i7-8700K",
            ComponentType.CPU,
            "Intel",
            67.00,
            88.0,
            2017
        );

        //assign the compatible motherboard platform
        intel8700K.setCpuPlatform(CPUPlatform.INTEL_LGA1151_8_9);

        //add the CPU to the library
        components.add(intel8700K);

        Component intel7500 = new Component(
            "Intel Core i5-7500", 
            ComponentType.CPU,
            "Intel", 
            202.00, 
            63.0, 
            2017
        );

        //assign the compatible motherboard platform
        intel7500.setCpuPlatform(CPUPlatform.INTEL_LGA1151_8_9);

        //add the CPU to the library
        components.add(intel7500);

        Component intel7600K = new Component(
            "Intel Core i5-7600K", 
            ComponentType.CPU,
            "Intel", 
            242.00, 
            70.0, 
            2017
        );

        //assign the compatible motherboard platform
        intel7600K.setCpuPlatform(CPUPlatform.INTEL_LGA1151_8_9);

        //add the CPU to the library
        components.add(intel7600K);

        Component intel7700K = new Component(
            "Intel Core i7-7700K", 
            ComponentType.CPU,
            "Intel", 
            339.00, 
            82.0, 
            2017
        );

        //assign the compatible motherboard platform
        intel7700K.setCpuPlatform(CPUPlatform.INTEL_LGA1151_8_9);

        //add the CPU to the library
        components.add(intel7700K);
    }
}
