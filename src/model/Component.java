package model;

public class Component {

    //create attributes for the component class
    private String name;
    private ComponentType type;
    private String manufacturer;
    private double price;
    private double performanceScore;
    private int releaseYear, psuCapacityWatts;
    private CPUPlatform cpuPlatform;
    private MemoryType memoryType;

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

    // Retrieve the PSU's rated wattage
    public int getPsuCapacityWatts() {
        return psuCapacityWatts;
    }

    // Store the rated capacity of a power supply
    public void setPsuCapacityWatts(int watts) {

        if (getType() != ComponentType.POWER_SUPPLY || watts <= 0) {
            throw new IllegalArgumentException("PSU wattage must be positive and belong to a power supply.");
        }

        this.psuCapacityWatts = watts;
    }

    // Retrieve the CPU/motherboard platform
    public CPUPlatform getCpuPlatform() {
        return cpuPlatform;
    }

    // Assign CPU/motherboard platform
    public void setCpuPlatform(CPUPlatform platform) {

        if (type != ComponentType.CPU && type != ComponentType.MOTHERBOARD) {

            throw new IllegalArgumentException("Only CPUs and motherboards can have a CPU platform.");
        }

        this.cpuPlatform = platform;
    }

    public MemoryType getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(MemoryType memoryType) {

        if (type != ComponentType.RAM && type != ComponentType.MOTHERBOARD) {

            throw new IllegalArgumentException("Memory type only applies to RAM and motherboards.");
        }

        this.memoryType = memoryType;
    }
}
