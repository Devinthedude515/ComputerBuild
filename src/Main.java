/* Main class for the computer build application */
import model.BuildRequest;
import service.UserInputService;


public class Main {
    public static void main(String[] args) {
        /*
        GUIDemo gd = new GUIDemo(640, 480);
        gd.setUpGUI();
        gd.setUpButtonListeners();
         */

        //use UserInputService to gather user data for processing
        UserInputService request = new UserInputService();

        //allow user to use the input service and use Build Request
        BuildRequest newBuild = request.collectUserInput();

        //testing that the enum values are validated, print them from BuildRequest
        System.out.println("Screen Resolution: " + newBuild.getScreenResolution());
        System.out.println("Gaming Genre: " + newBuild.getGamingGenre());
        System.out.println("Desired refresh rate: " + newBuild.getFrameRate());

    }
}