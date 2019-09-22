package was.http;

import java.util.HashMap;
import java.util.Map;

public class MimeType {
    public final static Map<String, String> MAP = new HashMap<>();

    static {
        MAP.put("css", "text/css;charset=utf-8");
        MAP.put("js", "text/javascript;charset=UTF-8:");
        MAP.put("htm", "text/html;charset=utf-8");
        MAP.put("html", "text/html;charset=utf-8");
        MAP.put("gif", "image/gif");
        MAP.put("png", "image/png");
        MAP.put("jpg", "image/jpeg");
        MAP.put("jpeg", "image/jpeg");
        MAP.put("bmp", "image/bmp");
        MAP.put("webp", "image/webp");
        MAP.put("ttf", "font/ttf");
        MAP.put("woff", "font/woff");
        MAP.put("ico", "image/vnd.microsoft.icon");
    }

    public static String getType(String extension) {
        return MAP.get(extension);
    }

    public static boolean contains(final String extension) {
        return MAP.containsKey(extension);
    }
}
