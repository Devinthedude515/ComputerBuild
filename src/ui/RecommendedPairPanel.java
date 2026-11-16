package ui;

import java.awt.*;
import java.text.NumberFormat;
import java.util.EnumMap;
import java.util.Locale;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import service.RecommendationTier;
import service.RecommendedPair;

public class RecommendedPairPanel extends JPanel {
    
    //create fields
    private Consumer<RecommendedPair> onPairSelected;

    private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    //constructor
     public RecommendedPairPanel(EnumMap<RecommendationTier, RecommendedPair> pairTiers, Consumer<RecommendedPair> onPairSelected) {

        this.onPairSelected = onPairSelected;

        setLayout(new BorderLayout(0, 15));
        setBackground(new Color(30, 41, 59));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Title
        JLabel title = new JLabel("Recommended CPU/GPU Combinations");

        UIStyles.styleTitleLabel(title);
        add(title, BorderLayout.NORTH);

        // Container for all recommended pairs
        JPanel pairList = new JPanel(new GridLayout(0, 1, 0, 10));

        pairList.setOpaque(false);

        if (pairTiers.isEmpty()) {

            JLabel message = new JLabel("No qualifying CPU/GPU combinations found.");

            UIStyles.styleLabel(message);
            pairList.add(message);

        } 
        else {

            //display recommendations by tier
            for (RecommendationTier tier : RecommendationTier.values()) {

                RecommendedPair pair = pairTiers.get(tier);

                //skip tiers without an eligible pair
                if (pair == null) {
                    continue;
                }

                //create a container for the tier and its pair
                JPanel tierPanel = new JPanel(new BorderLayout(0, 5));
                tierPanel.setOpaque(false);

                //display the recommendation tier
                JLabel tierLabel = new JLabel(tier.getDisplayName());

                tierLabel.setForeground(Color.WHITE);
                tierLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

                //reuse the existing pair display
                JPanel pairRow = createPairRow(pair);

                tierPanel.add(tierLabel, BorderLayout.NORTH);
                tierPanel.add(pairRow, BorderLayout.CENTER);

                pairList.add(tierPanel);
            }
        }

        add(pairList, BorderLayout.CENTER);
    }

    // Display one recommended pair
    private JPanel createPairRow(RecommendedPair pair) {

        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(new Color(40, 52, 70));
        row.setBorder(new EmptyBorder(10, 15, 10, 15));

        JPanel details = new JPanel(new GridLayout(3, 1, 0, 5));

        details.setOpaque(false);

        JLabel cpuLabel = new JLabel("CPU: " + pair.getCpu().getName());

        JLabel gpuLabel = new JLabel("GPU: " + pair.getGpu().getName());

        JLabel scoreLabel = new JLabel(
            "Cost: " + currency.format(pair.getCombinedPrice()) +
            " | Score: " +
            String.format(Locale.US, "%.1f", pair.getGamingScore())
        );

        UIStyles.styleLabel(cpuLabel);
        UIStyles.styleLabel(gpuLabel);
        UIStyles.styleLabel(scoreLabel);

        details.add(cpuLabel);
        details.add(gpuLabel);
        details.add(scoreLabel);

        JButton usePairButton = new JButton("Use Pair");
        UIStyles.styleMenuButton(usePairButton);

        // Notify the parent panel of the selected pair
        usePairButton.addActionListener(e -> onPairSelected.accept(pair));

        row.add(details, BorderLayout.CENTER);
        row.add(usePairButton, BorderLayout.EAST);

        return row;
    }
}