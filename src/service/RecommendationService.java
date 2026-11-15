package service;

import java.util.*;
import model.BuildRequest;
import model.Component;
import model.ComponentLibrary;
import model.ComponentType;

public class RecommendationService {

    //return the top GPU options
    public ArrayList<Component> recommendGPUs(BuildRequest request, ComponentLibrary library) {

        return findCandidates(request, library, ComponentType.GPU, getMinimumGpuScore(request));
    }

    // Return the top CPU candidates
    public ArrayList<Component> recommendCPUs(BuildRequest request, ComponentLibrary library) {
        
        return findCandidates(request, library, ComponentType.CPU, getMinimumCpuScore(request));
    }
    
    // Shared filtering and ranking method
    private ArrayList<Component> findCandidates(BuildRequest request, ComponentLibrary library, ComponentType type, double minimumScore) {

        //list to hold the components
        ArrayList<Component> candidates = new ArrayList<>();

        //for each loop to search through the list
        for (Component component : library.getComponentsByType(type)) {

            // Cannot recommend hardware from the future
            if (component.getReleaseYear() > request.getTargetYear()) {
                continue;
            }

            // Reject a single part costing more than the full budget
            if (component.getPrice() > request.getBudget()) {
                continue;
            }

            // Provisional performance requirement
            if (component.getPerformanceScore() < minimumScore) {
                continue;
            }

            candidates.add(component);
        }

        // Find the least excessive qualifying performance first
            candidates.sort(Comparator.comparingDouble( Component::getPerformanceScore));

        // Return up to three candidates
        return new ArrayList<>(candidates.subList( 0, Math.min(3, candidates.size())));

    }

    // Provisional GPU performance requirement
    private double getMinimumGpuScore(BuildRequest request) {

        String resolution = request.getScreenResolution().name().toUpperCase();

        int fps = getTargetFPS(request);

        double score;

        if (resolution.contains("2160") || resolution.contains("4K") || resolution.contains("UHD")) {

            score = 95.0;
        } 
        else if (resolution.contains("1440") ||resolution.contains("QHD")) {

            score = 78.0;
        } 
        else if (resolution.contains("1080") || resolution.contains("FHD")) {

            score = 63.0;
        } 
        else {
            
            throw new IllegalArgumentException("Unsupported resolution: " + resolution);
        }

        if (fps >= 120) {
            score += 23.0;
        } 
        else if (fps >= 90) {
            score += 12.0;
        }

        // Prototype allowance for newer gaming years
        score += getYearAdjustment(request);

        return score;
    }

    // Provisional CPU performance requirement
    private double getMinimumCpuScore(BuildRequest request) {

        int fps = getTargetFPS(request);

        double score = 60.0;

        if (fps >= 120) {
            score += 18.0;
        } else if (fps >= 90) {
            score += 10.0;
        }

        score += getYearAdjustment(request) * 0.8;

        return score;
    }

    // Interpret the FrameRate enum
    private int getTargetFPS(BuildRequest request) {

        String digits = request.getFrameRate().name().replaceAll("[^0-9]", "");

        if (digits.isEmpty()) {
            
            throw new IllegalArgumentException("FrameRate must contain a numeric FPS target.");
        }

        return Integer.parseInt(digits);
    }

    // Provisional additional performance headroom
    private double getYearAdjustment(BuildRequest request) {

        int yearsAfter2017 = Math.max(0, request.getTargetYear() - 2017);

        return yearsAfter2017 * 2.5;
    }
}

