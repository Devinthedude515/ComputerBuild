package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class BuildCard extends JPanel {

    //create local variables for the build card
    private String buildName;
    private String creator;

    //create constructor for the build card
    public BuildCard(String buildName, String creator, Runnable onClick) {

        this.buildName = buildName;
        this.creator = creator;

        //create the layout
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(220, 190));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);

        //Temporary default image for the build card
        JPanel imagePanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                //inherit the paintComponent method from the parent class
                super.paintComponent(g);
                
                //use Graphics2D to draw the image
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(

                    RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                //background for the image area
                g2.setColor(new Color(51, 65, 85));
                g2.fillRoundRect(
                    0, 0, getWidth(), getHeight() + 15, 15, 15);

                // Draw a simple placeholder PC tower
                int towerWidth = 55;
                int towerHeight = 85;
                int towerX = (getWidth() - towerWidth) / 2;
                int towerY = (getHeight() - towerHeight) / 2 - 5;

                g2.setColor(new Color(15, 23, 42));
                g2.fillRoundRect(
                    towerX, towerY,
                    towerWidth, towerHeight, 10, 10
                );

                g2.setColor(new Color(30, 144, 255));
                g2.fillOval(
                    towerX + 12, towerY + 12, 30, 30
                );

                g2.setColor(new Color(148, 163, 184));
                g2.fillOval(
                    towerX + 23, towerY + 23, 8, 8
                );

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                String text = "PC BUILD";

                g2.drawString(
                    text,
                    (getWidth() - fm.stringWidth(text)) / 2,
                    getHeight() - 12
                );

                g2.dispose();
            }
        };

        //set the size and opacity of the image panel
        imagePanel.setPreferredSize(new Dimension(220, 135));
        imagePanel.setOpaque(false);

        //create the bottom banner for the build card
        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setBackground(new Color(30, 41, 59));
        banner.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        //create the labels for the build name and creator
        JLabel nameLabel = new JLabel("Build Name: " + buildName);
        JLabel creatorLabel = new JLabel("Created by: " + creator);

        //set the font and color of the labels
        nameLabel.setForeground(Color.WHITE);
        creatorLabel.setForeground(new Color(203, 213, 225));
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        creatorLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        //add the labels to the banner
        banner.add(nameLabel);
        banner.add(Box.createVerticalStrut(5));
        banner.add(creatorLabel);

        //add the image panel and banner to the build card
        add(imagePanel, BorderLayout.CENTER);
        add(banner, BorderLayout.SOUTH);

        //Round the edges of the build card
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        //let the whole card be clickable by adding a mouse listener to the entire panel
        MouseAdapter clickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }
        };

        //add the mouse listener to the entire build card
        addMouseListener(clickListener);
        imagePanel.addMouseListener(clickListener);
        banner.addMouseListener(clickListener);
        nameLabel.addMouseListener(clickListener);
        creatorLabel.addMouseListener(clickListener);
    }

    //create getters for the build name and creator
    public String getBuildName() {
        return buildName;
    }

    public String getCreator() {
        return creator;
    }  
}
