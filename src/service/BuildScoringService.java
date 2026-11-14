import model.*;

public class BuildScoringService {

    // Weights used when calculating gaming performance
    private static final double GPU_WEIGHT = 0.70;
    private static final double CPU_WEIGHT = 0.30;

    // Check whether the build has the components
    // required for the initial scoring calculation
    public boolean canScore(PCBuild build) {

        if (build == null) {
            return false;
        }

        Component cpu = build.getSelectedComponent(ComponentType.CPU);

        Component gpu = build.getSelectedComponent(ComponentType.GPU);

        return cpu != null && gpu != null;
    }

    // Calculate the gaming performance score
    public double calculateGamingScore(PCBuild build) {

        // Make sure both required components exist
        if (!canScore(build)) {
            throw new IllegalArgumentException(
                "A CPU and GPU are required to calculate a gaming score."
            );
        }

        // Retrieve the selected components
        Component cpu = build.getSelectedComponent(ComponentType.CPU);

        Component gpu = build.getSelectedComponent(ComponentType.GPU);

        // Retrieve their performance scores
        double cpuScore = cpu.getPerformanceScore();
        double gpuScore = gpu.getPerformanceScore();

        // Calculate the weighted gaming score
        double gamingScore = (gpuScore * GPU_WEIGHT) + (cpuScore * CPU_WEIGHT);

        return gamingScore;
    }

    // Check whether the finished build is within budget
    public boolean isWithinBudget(PCBuild build,BuildRequest request) {

        return build.calculateTotalPrice() <= request.getBudget();
    }
}