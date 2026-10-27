package ui;
import java.awt.*;
import javax.swing.*;

public class CreateBuildPanel extends JPanel {
    
    //create constructor
    public CreateBuildPanel(Runnable onBack, Runnable onSubmit) {

        //set the layout and background
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 23, 42));
    }
}
