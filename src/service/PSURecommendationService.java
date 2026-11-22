package service;

import java.util.HashMap;
import java.util.Map;

import model.Component;

public class PSURecommendationService {
    
    //manufacturer/reference PSU minimum for each GPU
    private Map<String, Integer> gpuMinimumPSU = new HashMap<>();

    //approximate reference GPU board power
    private Map<String, Integer> gpuBoardPower = new HashMap<>();

    //CPU rated power (TDP), used as a planning estimate
    private Map<String, Integer> cpuRatedPower = new HashMap<>();

    public PSURecommendationService() {

        //GPU name, board power, minimum system PSU
        addGPU("GeForce GTX 1050 Ti 4GB", 75, 300);
        addGPU("GeForce GTX 1060 6GB", 120, 400);
        addGPU("GeForce GTX 1070 8GB", 150, 500);
        addGPU("GeForce GTX 1070 Ti 8GB", 180, 500);
        addGPU("GeForce GTX 1080 8GB", 180, 500);
        addGPU("GeForce GTX 1080 Ti 11GB", 250, 600);

        addGPU("Radeon RX 560 2GB", 80, 450);
        addGPU("Radeon RX 570 4GB", 150, 450);
        addGPU("Radeon RX 580 8GB", 185, 500);
        addGPU("Radeon RX 480 8GB", 150, 500);
        addGPU("Radeon RX Vega 56 8GB", 210, 650);

        //Historical AMD CPUs
        addCPU("AMD Ryzen 3 1200", 65);
        addCPU("AMD Ryzen 5 1400", 65);
        addCPU("AMD Ryzen 5 1600", 65);
        addCPU("AMD Ryzen 5 1600X", 95);
        addCPU("AMD Ryzen 7 1700", 65);

        //Historical Intel CPUs
        addCPU("Intel Core i3-7100", 51);
        addCPU("Intel Core i5-7500", 65);
        addCPU("Intel Core i5-7600K", 91);
        addCPU("Intel Core i7-7700K", 91);
        addCPU("Intel Core i5-8400", 65);
        addCPU("Intel Core i7-8700K", 95);
    }

    //adds the GPU and its PSU specs to the constructor
    private void addGPU(String name, int boardPower, int minimumPSU) {
        gpuBoardPower.put(name, boardPower);
        gpuMinimumPSU.put(name, minimumPSU);
    }

    //adds the CPU and its PSU specs to the constructor
    private void addCPU(String name, int ratedPower) {
        cpuRatedPower.put(name, ratedPower);
    }

    //return the minimum recommended system PSU wattage
    public int getMinimumSystemWatts(Component cpu, Component gpu) {

        //if unknown component return -1
        if (cpu == null || gpu == null) {
            return -1;
        }

        Integer gpuMinimum = gpuMinimumPSU.get(gpu.getName());
        Integer gpuPower = gpuBoardPower.get(gpu.getName());
        Integer cpuPower = cpuRatedPower.get(cpu.getName());

        //unknown hardware must not receive a guessed rating
        if (gpuMinimum == null || gpuPower == null || cpuPower == null) {
            return -1;
        }

        //provisional full-system power estimate:
        //GPU + CPU + 100W for supporting components
        double estimatedPower = gpuPower + cpuPower + 100.0;

        //add 25% planning headroom
        estimatedPower *= 1.25;

        //never go below the GPU's system PSU minimum
        double requiredPower = Math.max(gpuMinimum, estimatedPower);

        //round upward to the next 50 watts
        return (int) (Math.ceil(requiredPower / 50.0) * 50);
    }
}
