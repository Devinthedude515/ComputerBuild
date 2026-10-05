/* Main class for the computer build application */
import ui.GUIManager;

public class Main {
    public static void main(String[] args) {
        
        GUIManager gd = new GUIManager(1920, 1080);
        gd.setUpGUI();
        gd.setUpButtonListeners();
         
        /* 
        //use UserInputService to gather user data for processing
        UserInputService request = new UserInputService();

        //allow user to use the input service and use Build Request
        BuildRequest newBuild = request.collectUserInput();

        //testing that the enum values are validated, print them from BuildRequest
        System.out.println("Screen Resolution: " + newBuild.getScreenResolution());
        System.out.println("Gaming Genre: " + newBuild.getGamingGenre());
        System.out.println("Desired refresh rate: " + newBuild.getFrameRate());

        */

    }
}