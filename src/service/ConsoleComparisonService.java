package service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import model.PCBuild;

public class ConsoleComparisonService {
    
    private BuildScoringService scoringService = new BuildScoringService();

    //stores console reference information
    private static class ConsoleReference {

        String name;
        double score;
        double price;

        ConsoleReference(String name, double score, double price) {
            this.name = name;
            this.score = score;
            this.price = price;
        }
    }

    //manually maintained console scores and US retail prices
    private final List<ConsoleReference> consoles = Arrays.asList(
        new ConsoleReference("Xbox Series S (512GB)", 55.0, 499.99),
        new ConsoleReference("PlayStation 5", 80.0, 649.99),
        new ConsoleReference("Xbox Series X", 85.0, 799.99),
        new ConsoleReference("PlayStation 5 Pro", 95.0, 899.99)
    );

    //generate the comparison information
    public String getComparisonText(PCBuild build) {

        if (!scoringService.canScore(build)) {
            return "Gaming performance score unavailable.";
        }

        double pcScore = scoringService.calculateGamingScore(build);
        double pcPrice = build.calculateTotalPrice();

        StringBuilder result = new StringBuilder();

        result.append(String.format(Locale.US, "CUSTOM PC\nScore: %.1f/100 | Price: $%,.2f\n\n", pcScore, pcPrice));

        result.append("CONSOLE COMPARISONS\n\n");

        for (ConsoleReference console : consoles) {

            double difference = pcScore - console.score;

            String comparison;

            if (difference > 0) {
                comparison = String.format(Locale.US, "PC score +%.1f", difference);
            } 
            else if (difference < 0) {
                comparison = String.format(Locale.US, "PC score %.1f", difference);
            } 
            else {
                comparison = "Same reference score";
            }

            result.append(String.format(Locale.US,
                "%s\nScore: %.1f/100 | Price: $%,.2f\n%s\n\n",
                console.name,
                console.score,
                console.price,
                comparison
            ));
        }

        result.append(
            "Scores are estimates from the application's " +
            "simplified model, not measured FPS."
        );

        return result.toString();
    }
}
