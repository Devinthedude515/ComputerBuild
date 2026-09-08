/* Main class for the computer build application */

import model.BuildRequest;

public class Main {
    public static void main(String[] args) {

        // Create a new build request
        BuildRequest buildRequest = new BuildRequest("John Doe", "Gaming Rig", "HD_1080p", 1000.0, 2023, "First-Person Shooter", 60);

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