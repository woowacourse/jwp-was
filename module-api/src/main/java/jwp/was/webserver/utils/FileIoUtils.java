package jwp.was.webserver.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileIoUtils {

    private static final String DIRECTORY_DELIMITER = "/";
    private static final int FIRST_DIRECTORY_INDEX = 1;
    private static final String WEBAPP = "webapp";
    private static final String STATIC = "static";
    private static final int MINIMUM_DEPTH_EXISTS_DIRECTORY = 2;
    private static final String STATIC_DIRECTORY_ROUTE = "./module-api/src/main/resources/static";
    private static final List<String> STATIC_SUB_DIRECTORIES;

    static {
        List<String> staticSubDirectories = new ArrayList<>();
        File staticDirectory = new File(STATIC_DIRECTORY_ROUTE);
        File[] staticDirectorySubFiles = staticDirectory.listFiles();

        assert staticDirectorySubFiles != null;
        for (File staticDirectorySubFile : staticDirectorySubFiles) {
            addSubDirectory(staticSubDirectories, staticDirectorySubFile);
        }
        STATIC_SUB_DIRECTORIES = Collections.unmodifiableList(staticSubDirectories);
    }

    private FileIoUtils() {
    }

    private static void addSubDirectory(List<String> staticSubDirectories,
        File staticDirectorySubFile) {
        if (staticDirectorySubFile.isDirectory()) {
            staticSubDirectories.add(staticDirectorySubFile.getName());
        }
    }

    public static byte[] loadFileFromClasspath(String filePath)
        throws IOException, URISyntaxException {
        String directory = getDirectory(filePath);
        Path path = Paths.get(findFileUri(filePath, directory));
        return Files.readAllBytes(path);
    }

    private static String getDirectory(String filePath) {
        String directory = WEBAPP;
        String[] splitFilePath = filePath.split(DIRECTORY_DELIMITER);
        String firstDirectory = splitFilePath[FIRST_DIRECTORY_INDEX];
        if (splitFilePath.length > MINIMUM_DEPTH_EXISTS_DIRECTORY
            && STATIC_SUB_DIRECTORIES.contains(firstDirectory)) {
            directory = STATIC;
        }
        return directory;
    }

    private static URI findFileUri(String filePath, String directory) throws URISyntaxException {
        String fullFilePath = directory + filePath;
        URL resource = FileIoUtils.class.getClassLoader().getResource(fullFilePath);
        if (Objects.isNull(resource)) {
            throw new FileNotExitsException(filePath);
        }
        return Objects.requireNonNull(resource).toURI();
    }
}
