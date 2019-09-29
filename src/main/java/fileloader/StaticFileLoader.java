package fileloader;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

public class StaticFileLoader extends ClassPathTemplateLoader {
    private static final String STATIC_RESOURCE_PATH_PREFIX = "/static";
    private static final String STATIC_RESOURCE_PATH_SUFFIX = "";

    private StaticFileLoader() {
    }

    private StaticFileLoader(String prefix, String suffix) {
        super(prefix, suffix);
    }

    private static class StaticFileLoaderLazyHolder {
        private static final StaticFileLoader INSTANCE = new StaticFileLoader(STATIC_RESOURCE_PATH_PREFIX, STATIC_RESOURCE_PATH_SUFFIX);
    }

    public static StaticFileLoader getInstance() {
        return StaticFileLoaderLazyHolder.INSTANCE;
    }
}
