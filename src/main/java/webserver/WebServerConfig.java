package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebServerConfig {
    private static final Logger log = LoggerFactory.getLogger(WebServerConfig.class);

    private static final String ROOT_PATH = "./out/production/classes";
    private static final List<String> EXCLUSIONS = Arrays.asList("webserver", "utils");
    private static final String INNER_CLASS_SIGN = "$";

    private static final WebServerConfig instance = new WebServerConfig();

    private final List<Class<?>> classes;

    public static WebServerConfig getInstance() {
        return instance;
    }

    private WebServerConfig() {
        this.classes = scanAllClasses();
        this.classes.forEach(x -> log.debug(x.getName()));
    }

    private List<Class<?>> scanAllClasses() {
        return Stream.of(
                Paths.get(ROOT_PATH).normalize().toAbsolutePath().toFile().listFiles()
        ).filter(Objects::nonNull)
        .flatMap(x -> scanClassFromFile(x, x.getName(), Stream.empty()))
        .collect(Collectors.toList());
    }

    private Stream<Class<?>> scanClassFromFile(File file, String packageName, Stream<Class<?>> classes) {
        try {
            if (file.isDirectory()) {
                return scanClassesFromDirectory(file, packageName, classes);
            } else if (file.getName().endsWith(".class")) {
                return Stream.concat(
                        classes,
                        Stream.of(
                                Class.forName(
                                        packageName + "." + file.getName().substring(0, file.getName().length() - 6)
                                )
                        )
                );
            }
        } catch (ClassNotFoundException e) {}
        return classes;
    }

    private Stream<Class<?>> scanClassesFromDirectory(File directory, String packageName, Stream<Class<?>> classes) {
        if (!directory.exists() || EXCLUSIONS.contains(directory.getName())) {
            return classes;
        }
        return Stream.of(directory.listFiles()).filter(Objects::nonNull)
                                                .filter(file -> !file.getName().contains(INNER_CLASS_SIGN))
                                                .flatMap(file ->
                                                    scanClassFromFile(file, packageName, Stream.empty())
                                                );
    }

    public Stream<Class<?>> getAllClasses() {
        return this.classes.stream();
    }
}