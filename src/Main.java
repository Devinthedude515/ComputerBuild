/* Main class for the computer build application */
import model.Component;
import model.ComponentType;
import model.PCBuild;

public class Main {
    public static void main(String[] args) {
        
        //GUIManager gd = new GUIManager(1920, 1080);
        //gd.setUpGUI();

        PCBuild build = new PCBuild();

        Component cpu = new Component(
            "Ryzen Sample 7",
            ComponentType.CPU,
            "AMD",
            299.99,
            85,
            2025
        );

        Component gpu = new Component(
            "Radeon Sample 9070",
            ComponentType.GPU,
            "AMD",
            599.99,
            90,
            2025
        );

        build.selectComponent(cpu);
        build.selectComponent(gpu);

        System.out.println("Total: $" + build.calculateTotalPrice());
        System.out.println("Remaining: $" + build.getRemainingBudget(1500));
    }
}