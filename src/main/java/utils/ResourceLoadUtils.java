package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResourceLoadUtils {

    private static final String PREFIX = "./src/main/resources";
    private static List<String> resourceDirectories;

    static {
        resourceDirectories = Arrays.stream(new File(PREFIX).listFiles())
                .filter(File::isDirectory)
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public static Optional<File> detectFile(String filePath) {
        return resourceDirectories.stream()
                .map(dir -> PREFIX + "/" + dir + filePath)
                .map(Paths::get)
                .filter(Files::exists)
                .findFirst()
                .map(Path::toFile);
    }
}
