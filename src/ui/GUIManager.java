package ui;

//imports
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import service.UserInputService;

public class GUIManager {

    //fields that control the size of the GUI, and the components that are displayed on it. The frame is the main window that contains all the other components. The buttons are used to perform actions, and the text field is used to collect user input. The text area is used to display output, and the label is used to display instructions or questions.
    private JFrame frame;
    private int width;
    private int height;

    //buttons to confirm user input, or to perform an action, like a submit button. They can be set to be enabled or disabled, and can have an action listener attached to them.
    private JButton nameButton;

    //text field to collect user input, it can be set to a certain number of columns, which is the width of the text field
    private JTextField nameInput;

    //text area to display output, or for user input, or both. It can be used to display multiple lines of text, and can be set to be editable or not.
    private JTextArea ta;

    //all labels like questions, instructions, and output should be in a JLabel object
    private JLabel nameLabel;


    //create constructor
    public GUIManager(int w, int h) {
        //sets the Frame
        frame = new JFrame();

        //labels
        nameLabel = new JLabel("Name: ");

        //text fields
        nameInput = new JTextField(10);

        //text areas

        //buttons
        nameButton = new JButton("Submit");

        //sets the size of the frame
        width = w;
        height = h;
    }

    //this function controls what is being displayed on the GUI, and how it is laid out. It also sets the size of the frame and makes it visible.
    public void setUpGUI() {

        //set the frame up
        Container cp = frame.getContentPane();
        FlowLayout flow = new FlowLayout();
        //BorderLayout brdr =  new BorderLayout();
        cp.setLayout(flow);
        frame.setSize(width, height);
        frame.setTitle("PCBuilder Unlimited");
        //have to add which region in add statements when using borderlayout
        cp.add(nameLabel);
        cp.add(nameInput);
        cp.add(nameButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    //button listener setup
    public void setUpButtonListeners() {

        //call user input service to validate the name
        UserInputService userInputService = new UserInputService();

        //create action Listener 
        ActionListener buttonListener = new ActionListener() {

            //create action performed
            @Override 
            public void actionPerformed(ActionEvent ae) {
                //create object to validate button presses
                //getSource() function determines what action takes place
                Object o = ae.getSource();

                //validate the nameButton using the Object o variable
                if(o == nameButton){
                    //collect String data to copy to a label
                    String s = nameInput.getText();
                    String validatedName = userInputService.getUserName(s);
                    //set input text field to be an empty string after setting the label)
                        nameLabel.setText("Name: " + validatedName);
                    } else {
                        nameLabel.setText("Invalid input. Please enter a name that is not null and less than 10 characters.");
                    }
                    
                    //make the box empty after the user presses the button, so they can enter a new name if they want to
                    nameInput.setText("");
                }
            };

        //attach our button to the listener
        nameButton.addActionListener(buttonListener);
    }

    
}
