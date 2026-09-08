package service;
/*
 This class is responsible for handling user input and processing it accordingly.
*/

//import the entire java.util package to use the Scanner class for user input
//import the BuildRequest class from the model package to create a new BuildRequest object with the collected user input
import java.util.*;
import model.BuildRequest;
import model.ScreenResolution;
import model.GamingGenre;
import model.FrameRate;

public class UserInputService {

    //create scanner object to read user input from the console
    private Scanner scanner = new Scanner(System.in);

    //create a method using BuildRequest object to gather user input for a computer build request
    public BuildRequest collectUserInput() {

        //collect user name and validate that it is not null and less than 10 characters
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        while (name == null || name.length() > 10) {
            System.out.println("Invalid input. Please enter a name that is not null and less than 10 characters.");
            System.out.print("Enter your name: ");
            name = scanner.nextLine();
        }

        //collect the name of the build and validate that it is not null and less than 20 characters
        System.out.print("Enter the name of your build: ");
        String nameOfBuild = scanner.nextLine();

        while (nameOfBuild == null || nameOfBuild.length() > 20) {
            System.out.println("Invalid input. Please enter a build name that is not null and less than 20 characters.");
            System.out.print("Enter the name of your build: ");
            nameOfBuild = scanner.nextLine();
        }

        //collect the screen resolution and display the available options to the user using the ScreenResolution enum
        System.out.println("Choose available screen resolutions: ");
        for (ScreenResolution resolution : ScreenResolution.values()) {
            System.out.println(resolution);
        }
        System.out.print("Enter your preferred screen resolution: ");
        String screenResolution = scanner.nextLine();

        //validate that the input is one of the available options in the ScreenResolution enum
        while (screenResolution == null || screenResolution.isEmpty() ||!Arrays.asList(ScreenResolution.values()).contains(ScreenResolution.valueOf(screenResolution))) {
            System.out.println("Invalid input. Please enter a valid screen resolution from the available options.");
            System.out.print("Enter your preferred screen resolution: ");
            screenResolution = scanner.nextLine();
        }


        //collect the budget of the build and validate that it is more than 500 dollars
        System.out.print("Enter your budget: ");
        double budget = scanner.nextDouble();

        while (budget <= 500) {
            System.out.println("Invalid input. Please enter a budget that is more than 500 dollars.");
            System.out.print("Enter your budget: ");
            budget = scanner.nextDouble();
            scanner.nextLine(); // consume the newline character
        }

        //collect the target year for gaming and validate that it is more than 2005 and less than or equal to 2026
        System.out.print("Enter the target year for gaming: ");
        int targetYear = scanner.nextInt();

        while (targetYear <= 2005 || targetYear > 2026) {
            System.out.println("Invalid input. Please enter a target year that is more than 2005 and less than or equal to 2026.");
            System.out.print("Enter the target year for gaming: ");
            targetYear = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
        }

        //collect the preferred gaming genre and create a enum list of gaming genres to validate the input
        System.out.print("Enter your preferred gaming genre: ");
        String gamingGenre = scanner.nextLine();

        while (gamingGenre == null || gamingGenre.isEmpty()) {
            System.out.println("Invalid input. Please enter a preferred gaming genre.");
            System.out.print("Enter your preferred gaming genre: ");
            gamingGenre = scanner.nextLine();
        }

        //create a new BuildRequest object with the collected user input
        return new BuildRequest(name, nameOfBuild, screenResolution, budget, targetYear, gamingGenre, frameRate);
    }
    
}
