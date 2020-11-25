package utils;

import java.io.File;
import java.io.IOException;

public class FileIoUtils {
    public static final String TEMPLATE_PATH = "/Users/sangbo/Projects/woowa/jwp-was/application/module-core/src/main/resources/templates";
    public static final String STATIC_PATH = "/Users/sangbo/Projects/woowa/jwp-was/application/module-core/src/main/resources/static";
    public static final String TEMPLATE_CLASS_PATH = "/templates";
    public static final String STATIC_CLASS_PATH = "/static";

    public static byte[] findStaticFile(String path) throws IOException {
        File file = new File(TEMPLATE_PATH + path);
        if (file.isFile()) {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_CLASS_PATH + path);
        }
        file = new File(STATIC_PATH + path);
        if (file.isFile()) {
            return FileIoUtils.loadFileFromClasspath(STATIC_CLASS_PATH + path);
        }
        return null;
    }

    public static byte[] loadFileFromClasspath(String filePath) throws IOException {
        return FileIoUtils.class.getResourceAsStream(filePath).readAllBytes();
    }
}
