package ui;

//imports
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
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
    private JTextArea ta;
    private JLabel label;


    //create constructor
    public GUIDemo(int w, int h) {
        frame = new JFrame();
        label = new JLabel("Hello");
        input = new JTextField(10);
        ta = new JTextArea("Hello.\n This is a JText area");
        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        width = w;
        height = h;
    }

    public void setUpGUI() {

        //set the frame up
        Container cp = frame.getContentPane();
        //FlowLayout flow = new FlowLayout();
        BorderLayout brdr =  new BorderLayout();
        cp.setLayout(brdr);
        frame.setSize(width, height);
        frame.setTitle("GUI Demo");
        //have to add which region in add statements when using borderlayout
        cp.add(input, BorderLayout.NORTH);
        cp.add(label, BorderLayout.SOUTH);
        cp.add(button1, BorderLayout.WEST);
        cp.add(button2, BorderLayout.EAST);
        cp.add(ta, BorderLayout.CENTER);
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
                    //set input text field to be an empty string after setting the label
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
