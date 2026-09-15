package ui;

//imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUIDemo {

    private JFrame frame;
    private int width;
    private int height;
    private JButton button;


    //create constructor
    public GUIDemo(int w, int h) {
        frame = new JFrame();
        button = new JButton("Click Me");
        width = w;
        height = h;
    }

    public void setUpGUI() {
        //set the frame up
        frame.setSize(width, height);
        frame.setTitle("GUI Demo");
        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public void setUpButtonListeners() {

        ActionListener buttonListener = new ActionListener() {

            @Override 
            public void actionPerformed(ActionEvent ae) {
                System.out.println("click!");
            }
        };

        //attach our button to the listener
        button.addActionListener(buttonListener);
    }
}
