package Main.Java.pcBuilder.service;

public class BuildRequest {
    
    //create attributes for the build request class
    private String userName, buildName, gamingGenre;
    private double budget;
    private int targetYear;

    //create a constructor for the build request class
    public BuildRequest(String userName, String buildName, String gamingGenre, double budget, int targetYear) {
        this.userName = userName;
        this.buildName = buildName;
        this.gamingGenre = gamingGenre;
        this.budget = budget;
        this.targetYear = targetYear;
    }

    //create getters for the build request class
    public String getUserName() {
        return userName;
    }

    public String getBuildName() {
        return buildName;
    }

    public String getGamingGenre() {
        return gamingGenre;
    }

    public double getBudget() {
        return budget;
    }

    public int getTargetYear() {
        return targetYear;
    }
}
