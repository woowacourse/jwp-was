package webserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public enum FileNameExtension {
    API(null, endsNotWithExtensions(), Kind.API),
    CSS(Collections.singletonList(".css"), endsWithExtensions(), Kind.STATIC_FILE),
    FILE(null, null, Kind.WEBAPP_FILE),
    FONTS(Arrays.asList(".eot", ".svg", ".ttf", ".woff", ".woff2"), endsWithExtensions(),
        Kind.STATIC_FILE),
    HTML(Collections.singletonList(".html"), endsWithExtensions(), Kind.WEBAPP_FILE),
    IMAGES(Collections.singletonList(".png"), endsWithExtensions(), Kind.STATIC_FILE),
    JS(Collections.singletonList(".js"), endsWithExtensions(), Kind.STATIC_FILE);

    private static final String URL_DELIMITER = "/";
    private static final String EXTENSION_DELIMITER = ".";
    private final List<String> extensions;
    private final BiPredicate<List<String>, String> biPredicate;
    private final Kind kind;

    FileNameExtension(List<String> extensions, BiPredicate<List<String>, String> biPredicate,
        Kind kind) {
        this.extensions = extensions;
        this.biPredicate = biPredicate;
        this.kind = kind;
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

    public Kind getKind() {
        return kind;
    }
}
