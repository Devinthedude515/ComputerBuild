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
    
}
