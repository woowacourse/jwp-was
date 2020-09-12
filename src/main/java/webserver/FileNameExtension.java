package webserver;

import static webserver.FileNameExtension.Constants.DIRECTORY_STATIC;
import static webserver.FileNameExtension.Constants.DIRECTORY_WEBAPP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public enum FileNameExtension {
    API(null, endsNotWithExtensions(), null),
    CSS(Collections.singletonList(".css"), endsWithExtensions(), DIRECTORY_STATIC),
    FILE(null, null, DIRECTORY_WEBAPP),
    FONTS(Arrays.asList(".eot", ".svg", ".ttf", ".woff", ".woff2"), endsWithExtensions(),
        DIRECTORY_STATIC),
    HTML(Collections.singletonList(".html"), endsWithExtensions(), DIRECTORY_WEBAPP),
    IMAGES(Collections.singletonList(".png"), endsWithExtensions(), DIRECTORY_STATIC),
    JS(Collections.singletonList(".js"), endsWithExtensions(), DIRECTORY_STATIC);

    private final String directory;

    private static final String URL_DELIMITER = "/";
    private static final String EXTENSION_DELIMITER = ".";

    private final List<String> extensions;
    private final BiPredicate<List<String>, String> biPredicate;

    FileNameExtension(List<String> extensions, BiPredicate<List<String>, String> biPredicate,
        String directory) {
        this.extensions = extensions;
        this.biPredicate = biPredicate;
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    private static BiPredicate<List<String>, String> endsNotWithExtensions() {
        return (extensions, urlPath) -> {
            urlPath = getLastUrlPath(urlPath);
            return !urlPath.contains(EXTENSION_DELIMITER);
        };
    }

    private static String getLastUrlPath(String urlPath) {
        int delimiterIndex = urlPath.lastIndexOf(URL_DELIMITER);
        urlPath = urlPath.substring(delimiterIndex + 1);
        return urlPath;
    }

    private static BiPredicate<List<String>, String> endsWithExtensions() {
        return (extensions, urlPath) -> extensions.stream()
            .anyMatch(urlPath::endsWith);
    }

    public static FileNameExtension from(String urlPath) {
        List<FileNameExtension> fileNameExtensions
            = new ArrayList<>(Arrays.asList(FileNameExtension.values()));
        fileNameExtensions.remove(FILE);

        return fileNameExtensions.stream()
            .filter(CorrespondFileNameExtension(urlPath))
            .findFirst()
            .orElse(FILE);
    }

    private static Predicate<FileNameExtension> CorrespondFileNameExtension(String urlPath) {
        return fileNameExtension
            -> fileNameExtension.biPredicate.test(fileNameExtension.extensions, urlPath);
    }

    public List<String> getExtensions() {
        return extensions;
    }

    static class Constants {

        static final String DIRECTORY_WEBAPP = "webapp";
        static final String DIRECTORY_STATIC = "static";
    }
}
