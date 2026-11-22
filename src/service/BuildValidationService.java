package service;

import java.util.ArrayList;

import model.BuildRequest;
import model.Component;
import model.ComponentType;
import model.PCBuild;

public class BuildValidationService {
    
    //reuse our existing PSU recommendation calculations
    private PSURecommendationService powerService = new PSURecommendationService();

    //check whether a build can be safely submitted
    public ArrayList<String> validateBuild(BuildRequest request, PCBuild build) {

        ArrayList<String> errors = new ArrayList<>();

        //check for missing components
        for (ComponentType type : ComponentType.values()) {

            Component selected = build.getSelectedComponent(type);

            if (selected == null) {
                errors.add("Missing component: " + type.name().replace('_', ' '));
            }
        }

        //retrieve the important components
        Component cpu = build.getSelectedComponent(ComponentType.CPU);

        Component gpu = build.getSelectedComponent(ComponentType.GPU);

        Component motherboard = build.getSelectedComponent(ComponentType.MOTHERBOARD);

        Component psu = build.getSelectedComponent(ComponentType.POWER_SUPPLY);


        //check CPU and motherboard compatibility
        if (cpu != null && motherboard != null) {

            if (cpu.getCpuPlatform() == null || motherboard.getCpuPlatform() == null) {

                errors.add("CPU or motherboard platform is unknown.");

            } else if (cpu.getCpuPlatform() != motherboard.getCpuPlatform()) {

                errors.add("The selected motherboard is not " + "compatible with the CPU.");
            }
        }


        //check power supply requirements
        if (cpu != null && gpu != null) {

            int minimumWatts = powerService.getMinimumSystemWatts(cpu, gpu);

            if (minimumWatts < 0) {

                errors.add("CPU/GPU power requirements could not " + "be verified.");

            } else if (psu != null && psu.getPsuCapacityWatts() < minimumWatts) {

                errors.add("Power supply is insufficient. " + "At least " + minimumWatts + "W is recommended.");
            }
        }


        //check year-based hardware eligibility
        ComponentType[] yearRestrictedTypes = {
            ComponentType.CPU,
            ComponentType.GPU,
            ComponentType.MOTHERBOARD
        };

        for (ComponentType type : yearRestrictedTypes) {

            Component selected = build.getSelectedComponent(type);

            if (selected != null && selected.getReleaseYear() > request.getTargetYear()) {

                errors.add(selected.getName() + " was released after the target year.");
            }
        }

        return errors;
    }
}
