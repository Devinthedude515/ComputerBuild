package ui;

//imports
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUIDemo {

    //fields
    private JFrame frame;
    private int width;
    private int height;
    private JButton button1, button2;
    private JTextField input;
    private JLabel label;


    //create constructor
    public GUIDemo(int w, int h) {
        frame = new JFrame();
        label = new JLabel("Hello");
        input = new JTextField(10);
        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        width = w;
        height = h;
    }

    public void setUpGUI() {

        //set the frame up
        Container cp = frame.getContentPane();
        FlowLayout flow = new FlowLayout();
        cp.setLayout(flow);
        
        frame.setSize(width, height);
        frame.setTitle("GUI Demo");
        cp.add(input);
        cp.add(label);
        cp.add(button1);
        cp.add(button2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    //button listener setup
    public void setUpButtonListeners() {

        //create action Listener 
        ActionListener buttonListener = new ActionListener() {

            //create action performed
            @Override 
            public void actionPerformed(ActionEvent ae) {
                //create object to validate button presses
                //getSource() function determines what action takes place
                Object o = ae.getSource();

                //validate both button1 and button2 using the Object o variable
                if(o == button1){
                    //collect String data to copy to a label
                    String s = input.getText();
                    label.setText(s);
                    //set input text field to be an empty string
                    input.setText("");
                }
                else if(o == button2){
                    //collect String data and parse into a double value
                    String val = input.getText();
                    double n = Double.parseDouble(val);
                }
                
            }
        };

        //attach our button to the listener
        button1.addActionListener(buttonListener);
        button2.addActionListener(buttonListener);
    }

    
}
