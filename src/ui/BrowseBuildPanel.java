package ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.*;
import model.BuildRequest;
import model.PCBuild;

public class BrowseBuildPanel extends JPanel {

    //create local variables for the browse build panel
    private JPanel buildGrid;
    private Consumer<BuildCard> onBuildSelected;

    //create constructor
    public BrowseBuildPanel(Runnable onBack, Consumer<BuildCard> onBuildSelected) {
        
        //assign the onBuildSelected consumer
        this.onBuildSelected = onBuildSelected;

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
        buildGrid = new JPanel(new GridLayout(0, 3, 18, 18));
        buildGrid.setBackground(new Color (15, 23, 42));
        buildGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
                onBuildSelected
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

    //add build method
    public void addBuild(BuildRequest request, PCBuild build) {

        BuildCard newCard = new BuildCard(request, build, onBuildSelected);
        
        buildGrid.add(newCard, 0);

        buildGrid.revalidate();
        buildGrid.repaint();
    }

    //grabs all of the builds in the browser
    public ArrayList<BuildCard> getSavedBuildCards() {

        ArrayList<BuildCard> savedBuilds = new ArrayList<>();

        //examine each card in the Browse Builds gallery
        for (java.awt.Component item : buildGrid.getComponents()) {

            if (item instanceof BuildCard) {

                BuildCard card = (BuildCard) item;

                //ignore sample cards without real build data
                if (card.getBuildRequest() != null && card.getPCBuild() != null) {

                    savedBuilds.add(card);
                }
            }
        }

        return savedBuilds;
    }

    //replaces a build after creating edits
    public boolean replaceBuild(BuildCard originalCard, BuildRequest request, PCBuild updatedBuild) {

        //find the original card in the gallery
        int index = buildGrid.getComponentZOrder(originalCard);

        if (index < 0) {
            return false;
        }

        // Create a replacement card using the edited build
        BuildCard updatedCard = new BuildCard(
            request,
            updatedBuild,
            onBuildSelected
        );

        // Replace the original card at the same position
        buildGrid.remove(index);
        buildGrid.add(updatedCard, index);

        // Refresh the gallery
        buildGrid.revalidate();
        buildGrid.repaint();

        return true;
    }

}
