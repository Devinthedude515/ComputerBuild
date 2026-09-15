/* Main class for the computer build application */

import ui.GUIDemo;


public class Main {
    public static void main(String[] args) {
        
        GUIDemo gd = new GUIDemo(640, 480);
        gd.setUpGUI();
        gd.setUpButtonListeners();
    }
}