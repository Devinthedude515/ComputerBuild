package ui;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class BrowseBuildPanel extends JPanel {

    //create constructor
    public BrowseBuildPanel(Runnable onBack, Consumer<BuildCard> onBuildSelected) {
        
        //set the layout for the panel
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(15, 23, 42));

        //create the header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20));

        //create the title
        JLabel title = new JLabel("Browse Builds");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));

        //create the back button
        JButton backButton = new JButton("Back");
        UIStyles.styleMenuButton(backButton);
        backButton.addActionListener(e -> onBack.run());

        //add the title and back button to the header
        header.add(title, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        //create grid containing sample build cards
        JPanel buildGrid = new JPanel(new GridLayout(0, 3, 18, 18));
        buildGrid.setBackground(new Color (15, 23, 42));
        buildGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(buildGrid, BorderLayout.CENTER);

        // Temporary sample builds
        String[][] sampleBuilds = {
            {"Starforge", "Alex"},
            {"Frostbite", "Jordan"},
            {"Nightfall", "Alex"},
            {"Nebula", "Taylor"},
            {"Stormbreaker", "Jordan"},
            {"Redshift", "Taylor"},
            {"Overclock", "Alex"},
            {"Blue Nova", "Jordan"},
            {"Titan", "Taylor"}
        };

        for (String[] build : sampleBuilds) {

            BuildCard card = new BuildCard(
                build[0],
                build[1],
                () -> onBuildSelected.accept(new BuildCard(build[0], build[1], () -> {}))
            );
            buildGrid.add(card);
        }

        // Make the gallery scrollable
        JScrollPane scrollPane = new JScrollPane(buildGrid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(15, 23, 42));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

}
