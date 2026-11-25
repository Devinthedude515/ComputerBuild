package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Locale;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.BuildRequest;
import model.Component;
import model.ComponentLibrary;
import service.RecommendationService;
import service.RecommendationTier;
import service.RecommendedPair;
import service.RecommendedPairTierService;
public class ComponentRecommendationPanel extends JPanel {
    
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    public ComponentRecommendationPanel(BuildRequest request,ComponentLibrary library, Consumer<RecommendedPair> onPairSelected) {

        setLayout(new BorderLayout(0, 15));
        setOpaque(false);

        //create the recommendation service
        RecommendationService recommendationService = new RecommendationService();

        //retrieve individual CPU and GPU recommendations
        EnumMap<RecommendationTier, Component> recommendedCPUs = recommendationService.recommendCPUTiers(request, library);

        EnumMap<RecommendationTier, Component> recommendedGPUs = recommendationService.recommendGPUTiers(request, library);

        //create the individual recommendation sections
        JPanel recommendationsPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        recommendationsPanel.setOpaque(false);

        recommendationsPanel.add(createRecommendationSection("Recommended CPUs", recommendedCPUs));

        recommendationsPanel.add(createRecommendationSection("Recommended GPUs", recommendedGPUs));

        //retrieve eligible CPU/GPU pairs
        ArrayList<RecommendedPair> eligiblePairs = recommendationService.findEligiblePairs(request, library);

        //calculate pair recommendation tiers
        RecommendedPairTierService tierService = new RecommendedPairTierService();

        EnumMap<RecommendationTier, RecommendedPair> pairTiers = tierService.calculateTiers(eligiblePairs);

        //reuse our existing RecommendedPairPanel
        RecommendedPairPanel recommendedPairPanel = new RecommendedPairPanel(pairTiers, onPairSelected);

        //display pairs above individual recommendations
        add(recommendedPairPanel, BorderLayout.NORTH);
        add(recommendationsPanel, BorderLayout.CENTER);
    }

    //display individual hardware recommendation tiers
    private JPanel createRecommendationSection(String title, EnumMap<RecommendationTier, Component> recommendations) {

        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));

        panel.setBackground(new Color(30, 41, 59));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        //section title
        JLabel titleLabel = new JLabel(title);

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        panel.add(titleLabel);

        //handle empty recommendation lists
        if (recommendations.isEmpty()) {

            JLabel message = new JLabel("No qualifying components found.");

            message.setForeground(new Color(248, 113, 113));
            panel.add(message);

        } 
        else {

            // Display available recommendation tiers
            for (RecommendationTier tier : RecommendationTier.values()) {

                Component component = recommendations.get(tier);

                if (component == null) {
                    continue;
                }

                JLabel componentLabel = new JLabel(
                    "<html><b>" + tier.getDisplayName() +
                    "</b><br>" +
                    component.getName() + " - " +
                    currency.format(component.getPrice()) +
                    "</html>"
                );

                componentLabel.setForeground(new Color(203, 213, 225));

                componentLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

                panel.add(componentLabel);
            }
        }

        return panel;
    }
}
