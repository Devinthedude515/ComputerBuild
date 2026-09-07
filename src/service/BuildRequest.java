package service;
/*
    This class will be used to create a build request for a computer build.
    The information gathered will be the name, nameOfBuild, budget, target year for gaming, and gaming genre.
*/
public class BuildRequest {
    
    //create private fields to collect data
    private String name, nameOfBuild, gamingGenre;
    private double budget;
    private int targetYear;

    //create a constructor to initialize the fields
    public BuildRequest(String name, String nameOfBuild, double budget, int targetYear, String gamingGenre) {
        this.name = name;
        this.nameOfBuild = nameOfBuild;
        this.budget = budget;
        this.targetYear = targetYear;
        this.gamingGenre = gamingGenre;
    }

    //create getter methods to retrieve the values of the fields
    public String getName() {
        return name;
    }

    public String getNameOfBuild() {
        return nameOfBuild;
    }

    public double getBudget() {
        return budget;
    }

    public int getTargetYear() {
        return targetYear;
    }

    public String getGamingGenre() {
        return gamingGenre;
    }

}
