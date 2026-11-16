package service;

public enum RecommendationTier {
    
    //classifications that help with determinations on components
    LOWEST_COST("Lowest Cost"),
    BALANCED("Balanced"),
    HIGHEST_PERFORMANCE("Highest Performance");

    private final String displayName;

    RecommendationTier(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

