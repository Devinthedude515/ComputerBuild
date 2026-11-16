package service;

import java.util.*;
import model.BuildRequest;
import model.Component;
import model.ComponentLibrary;
import model.ComponentType;
import model.PCBuild;

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

    // Return GPU recommendations organized by tier
    public EnumMap<RecommendationTier, Component> recommendGPUTiers(BuildRequest request, ComponentLibrary library) {

        return calculateTiers(request, library, ComponentType.GPU, getMinimumGpuScore(request));
    }

    // Return CPU recommendations organized by tier
    public EnumMap<RecommendationTier, Component> recommendCPUTiers(BuildRequest request, ComponentLibrary library) {

        return calculateTiers(request, library, ComponentType.CPU, getMinimumCpuScore(request));
    }

    // Find eligible components and assign recommendation tiers
    private EnumMap<RecommendationTier, Component> calculateTiers(BuildRequest request, ComponentLibrary library, ComponentType type, double minimumScore) {

        //create Map to store results
        EnumMap<RecommendationTier, Component> results = new EnumMap<>(RecommendationTier.class);

        //create a list to determine eligibility
        ArrayList<Component> eligible = new ArrayList<>();

        //Filter components
        for (Component component : library.getComponentsByType(type)) {

            //Reject hardware released after the target year
            if (component.getReleaseYear() > request.getTargetYear()) {
                continue;
            }

            //Preliminary budget restriction
            if (component.getPrice() > request.getBudget()) {
                continue;
            }

            //Reject hardware below the minimum performance score
            if (component.getPerformanceScore() < minimumScore) {
                continue;
            }

            //add eligible components to the list
            eligible.add(component);
        }

        //No qualifying components
        if (eligible.isEmpty()) {
            return results;
        }

        //Identify cheapest and strongest options
        Component cheapest = null;
        Component strongest = null;

        //compare components with eligible components
        for (Component component : eligible) {

            //find cheapest of eligible components
            if (cheapest == null || component.getPrice() < cheapest.getPrice() ||
                (component.getPrice() == cheapest.getPrice() &&
                component.getPerformanceScore() >
                cheapest.getPerformanceScore())) {

                cheapest = component;
            }

            //find strongest of eligible components
            if (strongest == null || component.getPerformanceScore() >
                strongest.getPerformanceScore() ||
                (component.getPerformanceScore() ==
                strongest.getPerformanceScore() &&
                component.getPrice() < strongest.getPrice())) {

                strongest = component;
            }
        }

        //set the recommendation for cheapest
        results.put(RecommendationTier.LOWEST_COST, cheapest);

        if (strongest != cheapest) {

            results.put(RecommendationTier.HIGHEST_PERFORMANCE,strongest);
        }

        //Find a balanced option
        //Aim for modest performance headroom above the minimum
        double balancedTarget = minimumScore + 8.0;

        Component balanced = null;

        //search through eligible components
        for (Component component : eligible) {

            // Avoid recommending the same component twice
            if (component == cheapest || component == strongest) {
                continue;
            }

            double distance = Math.abs(component.getPerformanceScore() - balancedTarget);

            if (balanced == null) {
                balanced = component;
                continue;
            }

            double bestDistance = Math.abs(balanced.getPerformanceScore() - balancedTarget);

            if (distance < bestDistance || (distance == bestDistance && component.getPrice() < balanced.getPrice())) {

                balanced = component;
            }
        }

        if (balanced != null) {
            results.put(RecommendationTier.BALANCED, balanced);
        }

        return results;
    }

    private double getGenreGpuAdjustment(BuildRequest request) {

        String genre = request.getGamingGenre().name();

        // RPGs receive a provisional GPU-demand adjustment
        if (genre.contains("RPG")) {
            return 6.0;
        }

        // No additional GPU demand assumed for shooters
        if (genre.contains("SHOOTER") || genre.equals("FPS")) {
            return 0.0;
        }

        return 0.0;
    }

    private double getGenreCpuAdjustment(BuildRequest request) {

        String genre = request.getGamingGenre().name();

        // Provisional adjustment for shooter workloads
        if (genre.contains("SHOOTER") || genre.equals("FPS")) {
            return 3.0;
        }

        if (genre.contains("RPG")) {
            return 2.0;
        }

        return 0.0;
    }

    public ArrayList<RecommendedPair> findEligiblePairs(BuildRequest request, ComponentLibrary library) {

        //create a list to hold the pairs
        ArrayList<RecommendedPair> pairs = new ArrayList<>();

        //calculate the minimum performance requirements
        double minimumCpuScore = getMinimumCpuScore(request);
        double minimumGpuScore = getMinimumGpuScore(request);

        //reserve 40% of the total budget for other components
        double maxPairBudget = request.getBudget() * 0.60;

        BuildScoringService scoringService = new BuildScoringService();

        //retrieve available CPUs and GPUs
        ArrayList<Component> cpus = library.getComponentsByType(ComponentType.CPU);

        ArrayList<Component> gpus = library.getComponentsByType(ComponentType.GPU);

        //check every CPU against every GPU
        for (Component cpu : cpus) {

            //exclude CPUs released after the target year
            if (cpu.getReleaseYear() > request.getTargetYear()) {
                continue;
            }

            //exclude CPUs below our performance threshold
            if (cpu.getPerformanceScore() < minimumCpuScore) {
                continue;
            }

            for (Component gpu : gpus) {

                //exclude GPUs released after the target year
                if (gpu.getReleaseYear() > request.getTargetYear()) {
                    continue;
                }

                //exclude GPUs below the required score
                if (gpu.getPerformanceScore() < minimumGpuScore) {
                    continue;
                }

                //check the combined CPU/GPU price
                double combinedPrice = cpu.getPrice() + gpu.getPrice();

                if (combinedPrice > maxPairBudget) {
                    continue;
                }

                //create a temporary build for scoring
                PCBuild testBuild = new PCBuild();

                testBuild.selectComponent(cpu);
                testBuild.selectComponent(gpu);

                //calculate the existing gaming score
                double score = scoringService.calculateGamingScore(testBuild);

                //save the eligible pair
                pairs.add(new RecommendedPair(cpu, gpu, score));
            }
        }

        //sort combinations from lowest to highest cost
        pairs.sort(Comparator.comparingDouble(RecommendedPair::getCombinedPrice)
                  .thenComparing(Comparator.comparingDouble(RecommendedPair::getGamingScore).reversed()));

        //return up to five candidate combinations
        return pairs;
    }

    public ArrayList<RecommendedPair> recommendPairs(BuildRequest request, ComponentLibrary library) {

        //create list to hold eligible pairs
        ArrayList<RecommendedPair> pairs = findEligiblePairs(request, library);

        return new ArrayList<>(pairs.subList(0, Math.min(5, pairs.size())));
    }

}

