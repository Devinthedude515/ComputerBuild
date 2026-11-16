package service;

import model.Component;

public class RecommendedPair {
    
    private Component cpu;
    private Component gpu;
    private double gamingScore;

    public RecommendedPair(Component cpu, Component gpu, double gamingScore) {

        this.cpu = cpu;
        this.gpu = gpu;
        this.gamingScore = gamingScore;
    }

    public Component getCpu() {
        return cpu;
    }

    public Component getGpu() {
        return gpu;
    }

    public double getGamingScore() {
        return gamingScore;
    }

    public double getCombinedPrice() {
        return cpu.getPrice() + gpu.getPrice();
    }
}
