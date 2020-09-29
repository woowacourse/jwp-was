package web.application.common;

import java.util.HashMap;
import java.util.Map;

import web.server.utils.StaticFileType;

public class FilePathMapper {

    private final Map<String, String> map;

    private FilePathMapper() {
        map = new HashMap<>();
        map.put("HTML", "./templates");
        map.put("STATIC_EXTENSION", "./static");
    }

    public static FilePathMapper getInstance() {
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
        private static final FilePathMapper PREFIX_MAPPER = new FilePathMapper();
    }
}
