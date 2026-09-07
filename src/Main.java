/* Main class for the computer build application */

import service.BuildRequest;

public class Main {
    public static void main(String[] args) {

        // Create a new build request
        BuildRequest buildRequest = new BuildRequest("John Doe", "Gaming Rig", 1000.0, 2023, "First-Person Shooter");

        // Display the build request information
        System.out.println("Name: " + buildRequest.getName());
        System.out.println("Build Name: " + buildRequest.getNameOfBuild());
        System.out.println("Budget: $" + buildRequest.getBudget());
        System.out.println("Target Year: " + buildRequest.getTargetYear());
        System.out.println("Gaming Genre: " + buildRequest.getGamingGenre());
    }
}