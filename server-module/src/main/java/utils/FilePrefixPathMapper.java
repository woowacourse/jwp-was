package utils;

import java.util.HashMap;
import java.util.Map;

public class FilePrefixPathMapper {

    private final Map<String, String> map;

    private FilePrefixPathMapper() {
        map = new HashMap<>();
        map.put("HTML", "templates");
        map.put("STATIC_EXTENSION", "static");
    }

    public static FilePrefixPathMapper getInstance() {
        return Cache.PREFIX_MAPPER;
    }

    public String addPrefix(String path, StaticFileType extension) {
        return findPrefix(extension) + path;
    }

    private String findPrefix(StaticFileType extension) {
        if (map.containsKey(extension.name())) {
            return map.get(extension.name());
        }
        return map.get("STATIC_EXTENSION");
    }

    private static class Cache {

        private static final FilePrefixPathMapper PREFIX_MAPPER = new FilePrefixPathMapper();
    }
}
