package model;
/*
    This class will be used to create a build request for a computer build.
    The information gathered will be the name, nameOfBuild, budget, target year for gaming, and gaming genre.
*/
public class BuildRequest {
    
    //create private fields to collect data
    private String name, nameOfBuild, gamingGenre, screenResolution;
    private double budget;
    private int targetYear, frameRate;

    //create a constructor to initialize the fields
    public BuildRequest(String name, String nameOfBuild, String screenResolution, double budget, int targetYear, String gamingGenre, int frameRate) {
        this.name = name;
        this.nameOfBuild = nameOfBuild;
        this.screenResolution = screenResolution;
        this.budget = budget;
        this.targetYear = targetYear;
        this.gamingGenre = gamingGenre;
        this.frameRate = frameRate;
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

    public String getScreenResolution() {
        return screenResolution;
    }

    public int getFrameRate() {
        return frameRate;
    }
}
