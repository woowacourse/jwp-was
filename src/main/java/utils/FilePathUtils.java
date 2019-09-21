package utils;

public class FilePathUtils {

    public static String getExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
