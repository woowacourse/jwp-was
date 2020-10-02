package web.application.common;

import java.util.HashMap;
import java.util.Map;

import web.application.exception.InvalidStaticFileTypeException;

public class StaticFilePath {

    private final Map<String, String> map;

    private StaticFilePath() {
        map = new HashMap<>();
        map.put("html", "./templates");
        map.put("ico", "./templates");
        map.put("css", "./static");
        map.put("js", "./static");
        map.put("png", "./static");
        map.put("ttf", "./static");
        map.put("woff", "./static");
    }

    public static StaticFilePath getInstance() {
        return Cache.staticFilePath;
    }

    public String findPath(String path) {
        String[] split = path.split("\\.");
        String extensionType = split[split.length - 1];

        if (map.containsKey(extensionType)) {
            return map.get(extensionType);
        }
        throw new InvalidStaticFileTypeException(extensionType);
    }

    private static class Cache {
        private static final StaticFilePath staticFilePath = new StaticFilePath();
    }
}
