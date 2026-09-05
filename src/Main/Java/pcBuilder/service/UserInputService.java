package pcBuilder.service;
import java.util.*;
import model.BuildRequest;
/*
 This class is responsible for gathering user input for the PC build process.
 The information gathered will be used to select compatible components and score the performance of the build.
 */
public class UserInputService {
    
    //create a scanner class
    private Scanner keyboard = new Scanner(System.in);

    //create constructor that initializes the scanner class
    public UserInputService() {
        this.keyboard = new Scanner(System.in);
    }

    //create a BuildRequest object to collect user input
    public BuildRequest collectUserInput() {

        //create a title to display to the user
        System.out.println("Welcome to the PC Builder!");

        //request user name 
        System.out.print("Please enter your name: ");
        String userName = keyboard.nextLine();

        //request build name
        System.out.print("Please enter a name for your build: ");
        String buildName = keyboard.nextLine();

        //request gaming genre
        System.out.print("Please enter your preferred gaming genre: ");
        String gamingGenre = keyboard.nextLine();

        //request budget
        System.out.print("Please enter your budget (in USD): ");
        double budget = keyboard.nextDouble();

        //request target year
        System.out.print("Please enter the target year for your build: ");
        int targetYear = keyboard.nextInt();

        //create and return a BuildRequest object with the collected input
        return new BuildRequest(userName, buildName, gamingGenre, budget, targetYear);
    }
}