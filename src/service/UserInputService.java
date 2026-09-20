package service;
/*
 This class is responsible for handling user input and processing it accordingly.
*/

//import the entire java.util package to use the Scanner class for user input
//import the BuildRequest class from the model package to create a new BuildRequest object with the collected user input
import java.util.*;
import model.BuildRequest;
import model.FrameRate;
import model.GamingGenre;
import model.ScreenResolution;

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

        /*
        Multi function method to print available options to user, collects info, then validates the input.
        */
        //prints the available options to the user
        int count = 1;

        System.out.println("Choose available screen resolutions: ");
        for (ScreenResolution resolution : ScreenResolution.values()) {
            System.out.println(count + ". " + resolution);
            //increment count, it labels each option for switch statements
            count++;
        } 
        
        //collect user input
        ScreenResolution screenResolution = ScreenResolution.HD_1080p;
        int screenRes = scanner.nextInt();

        //vallidate the screen res number
        while (screenRes < 1 || screenRes > 3) {
            //print error statement
            System.out.println("Error. Please choose option 1-3");
            screenRes = scanner.nextInt();
        }

        //confirm the screen resolution choice
        switch(screenRes){

            //user chooses 1080p
            case 1:
                screenResolution = ScreenResolution.HD_1080p;
                break;
            //user chooses 1440p
            case 2:
                screenResolution = ScreenResolution.QHD_1440p;
                break;
            //user chooses 2160p
            case 3:
                screenResolution = ScreenResolution.UHD_2160p;
                break;
        }

        //reset count to print numbered options
        count = 1;

        //collect the gaming genre and validate that it is one of the available options
        System.out.println("Choose available gaming genres: ");
        for (GamingGenre genre : GamingGenre.values()) {
            System.out.println(count + ". " + genre);
            count++;
        }
        
        //collect user input
        GamingGenre gamingGenre = GamingGenre.FPS;
        int genreChoice = scanner.nextInt();

        //vallidate the screen res number
        while (genreChoice < 1 || genreChoice > 6) {
            //print error statement
            System.out.println("Error. Please choose option 1-6");
            genreChoice = scanner.nextInt();
        }

        //confirm the genre choice
        switch(genreChoice){

            //user chooses FPS
            case 1:
                gamingGenre = GamingGenre.FPS;
                break;
            //user chooses Survival
            case 2:
                gamingGenre = GamingGenre.Survival;
                break;
            //user chooses RPG
            case 3:
                gamingGenre = GamingGenre.RPG;
                break;
            //user chooses sports
            case 4:
                gamingGenre = GamingGenre.SPORTS;
                break;
            //user chooses sports
            case 5:
                gamingGenre = GamingGenre.STRATEGY;
                break;
            //user chooses sports
            case 6:
                gamingGenre = GamingGenre.RACING;
                break;
        }

        
        //reset count to print numbered options
        count = 1;

        System.out.println("Choose available frame rates: ");
        for (FrameRate frameRate : FrameRate.values()) {
            System.out.println(count + ". " +frameRate);
            count++;
        }

        //user input for frame rate
        FrameRate frameRate = FrameRate.FPS_30;
        int frameChoice = scanner.nextInt();

        //confirm the genre choice
        switch(frameChoice){

            //user chooses 30 FPS
            case 1:
                frameRate = FrameRate.FPS_30;
                break;
            //user chooses 60 FPS
            case 2:
                frameRate = FrameRate.FPS_60;
                break;
            //user chooses 120 FPS
            case 3:
                frameRate = FrameRate.FPS_120;
                break;
            //user chooses 144 FPS
            case 4:
                frameRate = FrameRate.FPS_144;
                break;
            //user chooses 240 FPS
            case 5:
                frameRate = FrameRate.FPS_240;
                break;
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


        //create a new BuildRequest object with the collected user input
        return new BuildRequest(name, nameOfBuild, screenResolution, budget, targetYear, gamingGenre, frameRate);
    }
    
}
