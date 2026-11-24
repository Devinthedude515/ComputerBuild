package model;

/** Groups a build's requested specifications and its chosen components. */
public class SavedBuild {
    private final BuildRequest request;
    private final PCBuild build;

    public SavedBuild(BuildRequest request, PCBuild build) {
        if (request == null || build == null) {
            throw new IllegalArgumentException("A saved build needs specifications and components.");
        }
        this.request = request;
        this.build = build;
    }

    public BuildRequest getRequest() {
        return request;
    }

    public PCBuild getBuild() {
        return build;
    }
}
