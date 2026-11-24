package service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.filechooser.FileSystemView;
import model.BuildRequest;
import model.Component;
import model.ComponentType;
import model.CPUPlatform;
import model.FrameRate;
import model.GamingGenre;
import model.MemoryType;
import model.PCBuild;
import model.SavedBuild;
import model.ScreenResolution;

public class BuildFileService {
    
    private static final String FILE_NAME = "PCBuilderUnlimitedBuilds.properties";
    private static final String FORMAT_VERSION = "1";

    public Path getSavePath() throws IOException {
        return findDesktop().resolve(FILE_NAME);
    }

    public Path saveBuilds(List<SavedBuild> builds) throws IOException {
        if (builds == null) {
            throw new IllegalArgumentException("Build list cannot be null.");
        }

        Properties values = new Properties();
        values.setProperty("format.version", FORMAT_VERSION);
        values.setProperty("build.count", Integer.toString(builds.size()));

        for (int i = 0; i < builds.size(); i++) {
            SavedBuild saved = builds.get(i);
            BuildRequest request = saved.getRequest();
            PCBuild build = saved.getBuild();
            String prefix = "build." + i + ".";

            values.setProperty(prefix + "name", request.getName());
            values.setProperty(prefix + "buildName", request.getNameOfBuild());
            values.setProperty(prefix + "budget", Double.toString(request.getBudget()));
            values.setProperty(prefix + "targetYear", Integer.toString(request.getTargetYear()));
            values.setProperty(prefix + "genre", request.getGamingGenre().name());
            values.setProperty(prefix + "resolution", request.getScreenResolution().name());
            values.setProperty(prefix + "frameRate", request.getFrameRate().name());

            for (ComponentType type : ComponentType.values()) {
                Component part = build.getSelectedComponent(type);
                if (part == null) {
                    continue;
                }
                String key = prefix + "component." + type.name() + ".";
                values.setProperty(key + "name", part.getName());
                values.setProperty(key + "price", Double.toString(part.getPrice()));
                values.setProperty(key + "score", Double.toString(part.getPerformanceScore()));
                values.setProperty(key + "releaseYear", Integer.toString(part.getReleaseYear()));

                if ((type == ComponentType.CPU || type == ComponentType.MOTHERBOARD)
                        && part.getCpuPlatform() != null) {
                    values.setProperty(key + "cpuPlatform", part.getCpuPlatform().name());
                }
                if ((type == ComponentType.RAM || type == ComponentType.MOTHERBOARD)
                        && part.getMemoryType() != null) {
                    values.setProperty(key + "memoryType", part.getMemoryType().name());
                }
                if (type == ComponentType.POWER_SUPPLY) {
                    values.setProperty(key + "watts", Integer.toString(part.getPsuCapacityWatts()));
                }
            }
        }

        Path destination = getSavePath();
        Path temporary = Files.createTempFile(destination.getParent(), "pcbuilder-", ".tmp");

        try {
            try (OutputStream output = Files.newOutputStream(temporary)) {
                values.store(output, "PCBuilder Unlimited saved builds - format version 1");
            }
            //replace the previous save only after the new file is complete.
            try {
                Files.move(temporary, destination, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } 
            catch (AtomicMoveNotSupportedException ex) {
                Files.move(temporary, destination, StandardCopyOption.REPLACE_EXISTING);
            }
        } 
        finally {
            Files.deleteIfExists(temporary);
        }
        return destination;
    }

    public List<SavedBuild> loadBuilds() throws IOException {
        Path source = getSavePath();
        List<SavedBuild> result = new ArrayList<>();
        
        //check if file exists or not
        if (!Files.exists(source)) {
            return result;
        }

        Properties values = new Properties();
        try (InputStream input = Files.newInputStream(source)) {
            values.load(input);
        }
        if (!FORMAT_VERSION.equals(values.getProperty("format.version"))) {
            throw new IOException("Unsupported or missing saved-build file version.");
        }

        try {
            int count = Integer.parseInt(required(values, "build.count"));
            if (count < 0 || count > 10000) {
                throw new IOException("Invalid number of saved builds.");
            }

            for (int i = 0; i < count; i++) {
                String prefix = "build." + i + ".";
                BuildRequest request = new BuildRequest(
                    required(values, prefix + "name"),
                    required(values, prefix + "buildName"),
                    ScreenResolution.valueOf(required(values, prefix + "resolution")),
                    Double.parseDouble(required(values, prefix + "budget")),
                    Integer.parseInt(required(values, prefix + "targetYear")),
                    GamingGenre.valueOf(required(values, prefix + "genre")),
                    FrameRate.valueOf(required(values, prefix + "frameRate"))
                );

                PCBuild build = new PCBuild();
                for (ComponentType type : ComponentType.values()) {
                    String key = prefix + "component." + type.name() + ".";
                    if (!values.containsKey(key + "name")) {
                        continue;
                    }

                    // Restores the recorded price and score, not today's catalog price.
                    Component part = new Component(
                        required(values, key + "name"), type,
                        "Saved", // The original manufacturer isn't in the saved format.
                        Double.parseDouble(required(values, key + "price")),
                        Double.parseDouble(required(values, key + "score")),
                        Integer.parseInt(required(values, key + "releaseYear"))
                    );

                    String platform = values.getProperty(key + "cpuPlatform");
                    if (platform != null &&
                        (type == ComponentType.CPU || type == ComponentType.MOTHERBOARD)) {
                        part.setCpuPlatform(CPUPlatform.valueOf(platform));
                    }

                    String memory = values.getProperty(key + "memoryType");
                    if (memory != null &&
                        (type == ComponentType.RAM || type == ComponentType.MOTHERBOARD)) {
                        part.setMemoryType(MemoryType.valueOf(memory));
                    }

                    if (type == ComponentType.POWER_SUPPLY) {
                        part.setPsuCapacityWatts(
                            Integer.parseInt(required(values, key + "watts")));
                    }
                    build.selectComponent(part);
                }
                result.add(new SavedBuild(request, build));
            }
        } 
        catch (IllegalArgumentException | NullPointerException ex) {
            throw new IOException("The saved builds file contains an invalid value.", ex);
        }
        return result;
    }

    private static String required(Properties values, String key) throws IOException {
        String value = values.getProperty(key);
        if (value == null) {
            throw new IOException("Missing required saved-build field: " + key);
        }
        return value;
    }

    private Path findDesktop() throws IOException {
        // Windows usually reports the actual Desktop here, even if redirected.
        File systemDesktop = FileSystemView.getFileSystemView().getHomeDirectory();
        if (systemDesktop.isDirectory() &&
            systemDesktop.getName().equalsIgnoreCase("Desktop")) {
            return systemDesktop.toPath();
        }

        // Support common Windows OneDrive Desktop locations.
        for (String variable : new String[]{"OneDriveConsumer", "OneDrive"}) {
            String root = System.getenv(variable);
            if (root != null && !root.isBlank()) {
                Path candidate = Paths.get(root, "Desktop");
                if (Files.isDirectory(candidate)) {
                    return candidate;
                }
            }
        }

        Path standard = Paths.get(System.getProperty("user.home"), "Desktop");
        if (Files.isDirectory(standard)) {
            return standard;
        }
        throw new IOException("Could not locate the user's Desktop folder.");
    }

}//end of class
