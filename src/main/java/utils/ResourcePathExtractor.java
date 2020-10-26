package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResourcePathExtractor {

    public static List<ResourcePath> extract(String root) {
        String rootPath = ClassLoader.getSystemResource(root).getFile();
        List<String> filePaths = new ArrayList<>();
        findFilePaths(filePaths, rootPath);

        return filePaths.stream()
                .map(path -> path.split(root)[1])
                .map(requestPath -> new ResourcePath(root, requestPath))
                .collect(Collectors.toList());
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
