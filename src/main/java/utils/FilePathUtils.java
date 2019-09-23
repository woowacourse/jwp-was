package utils;

public class FilePathUtils {
    public static String getExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    public static String getResourcePath(String path) {
        if (path.endsWith(".html")) {
            return "./templates" + path;
        }
        return "./static" + path;
    }
}
