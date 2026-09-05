import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    //create panel object
    JPanel mainPanel = new JPanel();

    public MainFrame() {

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128, 128, 255));
    }

    
    //initialize the frame
    public void initialaize() {
        setTitle("My Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}