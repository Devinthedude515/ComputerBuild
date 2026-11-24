package service;

import java.util.ArrayList;
import model.Component;
import model.ComponentLibrary;
import model.ComponentType;


public class BuildBudgetService {

    //estimate the minimum cost of components other than the CPU and GPU
    public double estimateOtherPartsCost(ComponentLibrary library) {

        double estimatedCost = 0.0;

        //go through every component category
        for (ComponentType type : ComponentType.values()) {

            //CPU and GPU are handled by RecommendationService
            if (type == ComponentType.CPU || type == ComponentType.GPU) {
                continue;
            }

            ArrayList<Component> options = library.getComponentsByType(type);

            //every remaining category needs at least one option
            if (options.isEmpty()) {
            
                throw new IllegalStateException("No components available for: " + type);
            }

            //find the cheapest component in this category
            double lowestPrice = Double.MAX_VALUE;

            for (Component component : options) {

                if (component.getPrice() < lowestPrice) {
                    lowestPrice = component.getPrice();
                }
            }

            //add that category's minimum price
            estimatedCost += lowestPrice;
        }

        return estimatedCost;
    }

    public double estimateOtherPartsCost(ComponentLibrary library, Component cpu, Component gpu, int targetYear) {

        PSURecommendationService powerService = new PSURecommendationService();

        //find the PSU requirement for this CPU/GPU pair
        int minimumWatts = powerService.getMinimumSystemWatts(cpu, gpu);

        if (minimumWatts < 0) {
            return Double.POSITIVE_INFINITY;
        }

        //find the cheapest compatible motherboard/RAM pair
        double cheapestBoardAndRAM = Double.POSITIVE_INFINITY;

        for (Component motherboard : library.getComponentsByType(ComponentType.MOTHERBOARD)) {

            //motherboard must support the selected CPU
            if (motherboard.getCpuPlatform() != cpu.getCpuPlatform()) {
                continue;
            }

            //hardware must exist by target year
            if (motherboard.getReleaseYear() > targetYear) {
                continue;
            }

            //motherboard must have a defined memory type
            if (motherboard.getMemoryType() == null) {
                continue;
            }

            for (Component ram : library.getComponentsByType(ComponentType.RAM)) {

                if (ram.getReleaseYear() > targetYear) {
                    continue;
                }

                //match motherboard and RAM type
                if (ram.getMemoryType() != motherboard.getMemoryType()) {
                    continue;
                }

                double combinedCost = motherboard.getPrice() + ram.getPrice();

                cheapestBoardAndRAM = Math.min(cheapestBoardAndRAM, combinedCost);
            }
        }

        //no compatible motherboard/RAM combination
        if (!Double.isFinite(cheapestBoardAndRAM)) {
            return Double.POSITIVE_INFINITY;
        }

        double estimatedCost = cheapestBoardAndRAM;

        //estimate remaining supporting components
        for (ComponentType type : ComponentType.values()) {

            if (type == ComponentType.CPU ||
                type == ComponentType.GPU ||
                type == ComponentType.MOTHERBOARD ||
                type == ComponentType.RAM) {
                continue;
            }

            double lowestPrice = Double.POSITIVE_INFINITY;

            for (Component component : library.getComponentsByType(type)) {

                //PSUs are not restricted by release year
                if (type != ComponentType.POWER_SUPPLY && component.getReleaseYear() > targetYear) {
                    continue;
                }

                //PSU must meet the required wattage
                if (type == ComponentType.POWER_SUPPLY && component.getPsuCapacityWatts() < minimumWatts) {
                    continue;
                }

                lowestPrice = Math.min(lowestPrice, component.getPrice());
            }

            //this pair cannot support a complete build
            if (!Double.isFinite(lowestPrice)) {
                return Double.POSITIVE_INFINITY;
            }

            estimatedCost += lowestPrice;
        }

        return estimatedCost;
    }
    
}
