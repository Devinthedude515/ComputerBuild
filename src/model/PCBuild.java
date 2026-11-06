package model;
import java.util.*;

public class PCBuild {

    //create a HashMap to hold the compents Type
    private EnumMap<ComponentType, Component> selectedComponents;

    //create a constructor for the PCBuild class
    public PCBuild() {
        selectedComponents = new EnumMap<>(ComponentType.class);
    }

    //selects a component or replaces the existing component of that type
    public void selectComponent(Component component) {
        
        //if not null add the component to the selectedComponents map
        if (component != null) {
            selectedComponents.put(component.getType(), component);
        }
    }
    
    //removes a selected component
    public void removeComponent(ComponentType type) {
        selectedComponents.remove(type);
    }

    //gets the selected component of a specific type
    public Component getSelectedComponent(ComponentType type) {
        return selectedComponents.get(type);
    }

    //calculates the combined price of all selected components
    public double calculateTotalPrice() {
        double totalPrice = 0.0;

        for (Component component : selectedComponents.values()) {
            totalPrice += component.getPrice();
        }

        return totalPrice;
    }

    //calculates how much of the user's budget remains
    public double getRemainingBudget(double budget) {
        return budget - calculateTotalPrice();
    }

    //returns a copy of the selected components map
    public Collection<Component> getSelectedComponents() {
        return new ArrayList<>(selectedComponents.values());
    }
}
