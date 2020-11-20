package server.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathExtractor {
    private static final String CURRENT_PATH = "./";

    public static List<String> extractSourcePath(Class<?> rootClass) {
        String rootPackageName = rootClass.getPackage().getName(); // server
        String absoluteRootClassPath = rootClass.getResource(".").getPath(); // /Users/../server/
        String prefix = absoluteRootClassPath.substring(0, absoluteRootClassPath.length() - rootPackageName.length() - 1);

        return getFilePath(rootPackageName).stream()
                .map(filePath -> filePath.split(prefix)[1])
                .map(sourcePath -> sourcePath.replace("/", "."))
                .map(sourcePath -> sourcePath.split(".class")[0])
                .collect(Collectors.toList());
    }

    public static List<ResourcePath> extractResourcePath(String root) {
        List<String> filePaths = getFilePath(root);

        return filePaths.stream()
                .map(path -> path.split(root)[1])
                .map(requestPath -> new ResourcePath(root, requestPath))
                .collect(Collectors.toList());
    }

    public static List<String> getFilePath(String root) {
        String rootPath;
        try {
            rootPath = ClassLoader.getSystemResource(CURRENT_PATH + root).getFile();
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
        List<String> filePaths = new ArrayList<>();
        findFilePaths(filePaths, rootPath);
        return filePaths;
    }

    private static void findFilePaths(List<String> filePaths, String rootPath) {
        File rootFile = new File(rootPath);
        File[] files = rootFile.listFiles();

        if (Objects.isNull(files)) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                findFilePaths(filePaths, file.getPath());
            } else {
                filePaths.add(file.getPath());
            }
        }
    }
}
