/* Main class for the computer build application */
import ui.GUIManager;

public class Main {
    public static void main(String[] args) {
        
        GUIManager gd = new GUIManager(1920, 1080);
        gd.setUpGUI();
    }
}