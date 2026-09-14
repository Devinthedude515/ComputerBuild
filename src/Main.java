/* Main class for the computer build application */

import model.BuildRequest;
import service.UserInputService;
import ui.MainFrame;


public class Main {
    public static void main(String[] args) {

        // Create and display the main frame
        MainFrame mainFrame = new MainFrame();
        mainFrame.initialize();

        // Create a new build request
        UserInputService userInputService = new UserInputService();
        BuildRequest buildRequest = userInputService.collectUserInput();

        // Display the build request information
        System.out.println("Name: " + buildRequest.getName());
        System.out.println("Build Name: " + buildRequest.getNameOfBuild());
        System.out.println("Screen Resolution: " + buildRequest.getScreenResolution());
        System.out.println("Budget: $" + buildRequest.getBudget());
        System.out.println("Target Year: " + buildRequest.getTargetYear());
        System.out.println("Gaming Genre: " + buildRequest.getGamingGenre());
        System.out.println("Frame Rate: " + buildRequest.getFrameRate() + " FPS");
    }
}