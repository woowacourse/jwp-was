package webserver.utils;

public class FilePathUtils {
    public static String getExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    public static String getResourcePath(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return isStaticFile(path) ?
                String.format("./static/%s", path) : String.format("./templates/%s", path);
    }

    private static boolean isStaticFile(String path) {
        return !isTemplateFile(path);
    }

    private static boolean isTemplateFile(String path) {
        return path.endsWith(".html");
    }
}
