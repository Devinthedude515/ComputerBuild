package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import model.BuildRequest;
import model.PCBuild;

public class BuildSummaryPanel extends JPanel {
    
    //build information
    private BuildRequest request;
    private PCBuild build;

    //summary labels
    private JLabel totalCostLabel;
    private JLabel remainingBudgetLabel;
    private JLabel budgetStatusLabel;
    private JLabel psuRecommendationLabel;

    //currency formatting
    private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    public BuildSummaryPanel(BuildRequest request, PCBuild build) {

        this.request = request;
        this.build = build;

        //set up panel layout
        setLayout(new GridLayout(0, 1, 5, 5));
        setBackground(new Color(15, 23, 42));

        //create budget labels
        totalCostLabel = new JLabel();
        remainingBudgetLabel = new JLabel();
        budgetStatusLabel = new JLabel();

        //create PSU recommendation label
        psuRecommendationLabel = new JLabel(
            "PSU: Select a CPU and GPU first."
        );

        //create legacy pricing notice
        JLabel legacyPriceNotice = new JLabel(
            "<html>Note: Legacy hardware prices are estimates based on " +
            "secondhand market values and may vary by seller and condition.</html>"
        );

        //style the labels
        UIStyles.styleLabel(totalCostLabel);
        UIStyles.styleLabel(remainingBudgetLabel);
        UIStyles.styleLabel(budgetStatusLabel);
        UIStyles.styleLabel(psuRecommendationLabel);
        UIStyles.styleLabel(legacyPriceNotice);

        //add labels to the panel
        add(totalCostLabel);
        add(remainingBudgetLabel);
        add(budgetStatusLabel);
        add(psuRecommendationLabel);
        add(legacyPriceNotice);

        //display the initial budget
        refreshBudget();
    }

    //update budget information
    public void refreshBudget() {

        double total = build.calculateTotalPrice();
        double remaining = request.getBudget() - total;

        totalCostLabel.setText("Selected Components: " + currency.format(total));

        remainingBudgetLabel.setText("Remaining Budget: " + currency.format(remaining));

        if (remaining < 0) {

            budgetStatusLabel.setText("Warning: You are over budget!");

            budgetStatusLabel.setForeground(Color.RED);

        } 
        else {

            budgetStatusLabel.setText("You are within your budget.");

            budgetStatusLabel.setForeground(Color.GREEN);
        }
    }

    //update PSU recommendation message
    public void setPsuRecommendation(String message) {

        psuRecommendationLabel.setText(message);
    }
}