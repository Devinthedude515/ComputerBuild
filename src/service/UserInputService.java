package service;
/*
 this class is responsible for handling user input and processing it accordingly.
*/

//import the entire java.util package to use the Scanner class for user input
import java.util.*;
import model.BuildRequest;

public class UserInputService {

    //create scanner object to read user input from the console
    private Scanner scanner = new Scanner(System.in);


    //create a method using BuildRequest object to gather user input for a computer build request
    public BuildRequest collectUserInput() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the name of your build: ");
        String nameOfBuild = scanner.nextLine();

        System.out.print("Enter your budget: ");
        double budget = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character

        System.out.print("Enter the target year for gaming: ");
        int targetYear = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        System.out.print("Enter your preferred gaming genre: ");
        String gamingGenre = scanner.nextLine();

        //create a new BuildRequest object with the collected user input
        return new BuildRequest(name, nameOfBuild, budget, targetYear, gamingGenre);
    }
    
}
