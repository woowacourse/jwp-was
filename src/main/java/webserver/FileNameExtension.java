package webserver;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public enum FileNameExtension {
    API(
        null,
        endsNotWithExtensions(),
        null
    ),
    CSS(
        Collections.singletonList(".css"),
        endsWithExtensions(),
        Constants.DIRECTORY_STATIC
    ),
    FILE(
        null,
        null,
        Constants.DIRECTORY_WEBAPP
    ),
    FONTS(
        Arrays.asList(".eot", ".svg", ".ttf", ".woff", ".woff2"),
        endsWithExtensions(),
        Constants.DIRECTORY_STATIC
    ),
    HTML(
        Collections.singletonList(".html"),
        endsWithExtensions(),
        Constants.DIRECTORY_WEBAPP
    ),
    IMAGES(
        Collections.singletonList(".png"),
        endsWithExtensions(),
        Constants.DIRECTORY_STATIC
    ),
    JS(
        Collections.singletonList(".js"),
        endsWithExtensions(),
        Constants.DIRECTORY_STATIC
    );

    private static final String URL_DELIMITER = "/";
    private static final String EXTENSION_DELIMITER = ".";

    private final String directory;
    private final List<String> extensions;
    private final BiPredicate<List<String>, String> biPredicate;

    FileNameExtension(List<String> extensions, BiPredicate<List<String>, String> biPredicate,
        String directory) {
        this.extensions = extensions;
        this.biPredicate = biPredicate;
        this.directory = directory;
    }

    private static BiPredicate<List<String>, String> endsNotWithExtensions() {
        return (extensions, urlPath) -> {
            urlPath = getLastUrlPath(urlPath);
            return !urlPath.contains(EXTENSION_DELIMITER);
        };
    }

    private static BiPredicate<List<String>, String> endsWithExtensions() {
        return (extensions, urlPath) -> extensions.stream()
            .anyMatch(urlPath::endsWith);
    }

    private static String getLastUrlPath(String urlPath) {
        int delimiterIndex = urlPath.lastIndexOf(URL_DELIMITER);
        urlPath = urlPath.substring(delimiterIndex + 1);
        return urlPath;
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

    public String getDirectory() {
        return directory;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    static class Constants {

        static final String DIRECTORY_WEBAPP = "webapp";
        static final String DIRECTORY_STATIC = "static";
    }
}
