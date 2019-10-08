package webserver.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileExtension;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Reflections {
    private static final Logger logger = LoggerFactory.getLogger(Reflections.class);

    private static final List<String> EXCLUSIONS = Arrays.asList("webserver");
    private static final String INNER_CLASS_SIGN = "$";

    private final List<Class<?>> classes;

    protected Reflections() {
        this.classes = scanAllUserDefinedClasses();
        this.classes.forEach(x -> logger.debug("Class scanned: " + x.getName()));
    }

    private List<Class<?>> scanAllUserDefinedClasses() {
        final Stream<Class<?>> classesFromClassFiles = scanClassesFromDirectory(
                new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile()),
                ""
        );
        final Stream<Class<?>> classesFromJarFiles = Stream.of(
                System.getProperty("java.class.path").split(File.pathSeparator)
        ).filter(this::isJarFile)
        .flatMap(path -> {
            try {
                return scanClassesFromJarFile(new JarFile(path));
            } catch (IOException e) {
                return Stream.empty();
            }
        });
        return classesFromClassFiles.collect(Collectors.toList());
    }

    private Stream<Class<?>> scanClassesFromDirectory(File directory, String packageName) {
        return Stream.of(directory.listFiles())
                    .filter(Objects::nonNull)
                    .flatMap(file -> scanClassesFromFile(file, packageName));
    }

    private Stream<Class<?>> scanClassesFromFile(File file, String packageName) {
        if (file.isDirectory()) {
            return scanClassesFromDirectory(
                    file,
                    packageName.isEmpty() ? (packageName + file.getName()) : (packageName + "." + file.getName())
            );
        }
        if (isClassFile(file.getName())
                && isUserDefinedClass(packageName)
                    && !file.getName().contains(INNER_CLASS_SIGN)
        ) {
            try {
                return Stream.of(
                        Class.forName(
                                packageName + "." + file.getName().substring(0, file.getName().length() - 6)
                        )
                );
            } catch (ClassNotFoundException e) {}
        }
        return Stream.empty();
    }

    private Stream<Class<?>> scanClassesFromJarFile(JarFile jarFile) {
        final List<String> pathsOfClasses = new ArrayList<>();
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final String entryName = entries.nextElement().getName();
            if (isClassFile(entryName)) {
                pathsOfClasses.add(entryName);
            }
        }
        return pathsOfClasses.stream().filter(x -> !x.contains(INNER_CLASS_SIGN))
                                        .map(x -> x.replaceAll("/", "\\."))
                                        .map(x -> x.substring(0, x.length() - 6))
                                        .map(x -> {
                                            try {
                                                return Class.forName(x);
                                            } catch (ClassNotFoundException e) {
                                                return null;
                                            }
                                        });
    }

    private boolean isClassFile(String path) {
        return new FileExtension(path).equals(new FileExtension(".class"));
    }

    private boolean isJarFile(String path) {
        return new FileExtension(path).equals(new FileExtension(".jar"));
    }

    private boolean isUserDefinedClass(String packageName) {
        return EXCLUSIONS.stream().noneMatch(packageName::startsWith);
    }

    public Stream<Class<?>> getAllClassesAnnotatedX(Class<? extends Annotation> x) {
        return this.classes.stream().filter(clazz -> clazz.isAnnotationPresent(x));
    }

    public Stream<Method> getAllMethodsAnnotatedX(Class<? extends Annotation> x) {
        return this.classes.stream().flatMap(clazz ->
                Stream.of(clazz.getMethods()).filter(method -> method.isAnnotationPresent(x))
        );
    }
}