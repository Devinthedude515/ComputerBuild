package ui;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class EditBuildPanel extends JPanel{
    
    //container for saved builds
    private JPanel buildList;

    //callback for selecting a build to edit
    private Consumer<BuildCard> onBuildSelected;

    public EditBuildPanel(Runnable onBack, Consumer<BuildCard> onBuildSelected) {

        this.onBuildSelected = onBuildSelected;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(15, 23, 42));

        //header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20));

        JLabel title = new JLabel("Edit Builds");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));

        JButton backButton = new JButton("Back");
        UIStyles.styleMenuButton(backButton);

        backButton.addActionListener(e -> onBack.run());

        header.add(title, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        //vertical list of saved builds
        buildList = new JPanel();
        buildList.setLayout(new BoxLayout(buildList, BoxLayout.Y_AXIS));
        buildList.setBackground(new Color(15, 23, 42));
        buildList.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //scrollable list
        JScrollPane scrollPane = new JScrollPane(buildList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(15, 23, 42));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    //refresh the displayed saved builds
    public void showBuilds(java.util.List<BuildCard> savedBuilds) {

        buildList.removeAll();

        if (savedBuilds.isEmpty()) {

            JLabel message = new JLabel("No saved builds available to edit.");

            UIStyles.styleLabel(message);
            buildList.add(message);

        } 
        else {

            for (BuildCard card : savedBuilds) {

                //show the build name and creator
                JButton editButton = new JButton(card.getBuildName() + "  -  " + card.getCreator());

                UIStyles.styleMenuButton(editButton);

                editButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

                editButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

                //open the selected build for editing
                editButton.addActionListener(e -> onBuildSelected.accept(card));

                buildList.add(editButton);
                buildList.add(Box.createVerticalStrut(10));
            }
        }

        buildList.revalidate();
        buildList.repaint();
    }
}
