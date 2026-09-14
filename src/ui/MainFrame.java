package ui;

import javax.swing.*;

public class MainFrame extends JFrame{
    
    public void initialize() {
        setTitle("Computer Build Request");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create a panel to hold the components
        JPanel panel = new JPanel();
        add(panel);
        
        // Create and add components to the panel
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        panel.add(nameLabel);
        panel.add(nameField);
        
        JLabel buildNameLabel = new JLabel("Build Name:");
        JTextField buildNameField = new JTextField(20);
        panel.add(buildNameLabel);
        panel.add(buildNameField);
        
        // Add more components as needed for screen resolution, budget, target year, gaming genre, and frame rate
        
        // Create a submit button
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton);
        
        // Add action listener to the submit button to handle user input and create a BuildRequest object
    }
}
