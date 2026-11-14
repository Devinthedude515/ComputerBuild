package service;

public enum ConsoleReference {
    
    // Preliminary scores for testing only
    PS5("PlayStation 5", 75.0, 72.0),
    XBOX_SERIES_X("Xbox Series X", 75.0, 78.0);

    private final String name;
    private final double cpuScore;
    private final double gpuScore;

    ConsoleReference(String name, double cpuScore, double gpuScore) {
        this.name = name;
        this.cpuScore = cpuScore;
        this.gpuScore = gpuScore;
    }

    public String getName() {
        return name;
    }

    public double getCpuScore() {
        return cpuScore;
    }

    public double getGpuScore() {
        return gpuScore;
    }
}

