package model;
import model.ComponentType;

public class Component {

    //create attributes for the component class
    private String name;
    private ComponentType type;
    private String manufacturer;
    private double price;
    private double performanceScore;
    private int releaseYear;

    //create constructor for the component class
    public Component(String name, ComponentType type, String manufacturer, double price, double performanceScore, int releaseYear) {
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
        this.price = price;
        this.performanceScore = performanceScore;
        this.releaseYear = releaseYear;
    }

    //create getters and setters for the component class
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(double performanceScore) {
        this.performanceScore = performanceScore;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
