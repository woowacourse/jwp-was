package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResourcePathExtractor {
    public static List<String> extract(String root) throws IOException {
        String rootPath = ClassLoader.getSystemResource(root).getFile();
        List<String> filePaths = new ArrayList<>();
        findFilePaths(filePaths, rootPath);

        return filePaths.stream()
                .map(path -> path.split(root)[1])
                .collect(Collectors.toList());
    }

    private static void findFilePaths(List<String> filePaths, String rootPath) throws IOException {
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
