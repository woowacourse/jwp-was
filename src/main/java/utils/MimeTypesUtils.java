package utils;

import java.util.HashMap;
import java.util.Map;

public class MimeTypesUtils {
    private static final Map<String, String> mimeNameMap = new HashMap<>();

    static {
        mimeNameMap.put("html", "text/html");
        mimeNameMap.put("css", "text/css");
        mimeNameMap.put("js", "application/javascript");
    }

    public static String getMimeType(String fileName) {
        String[] extension = fileName.split("\\.");
        return mimeNameMap.getOrDefault(extension[extension.length - 1], "text/plain");
    }
}
