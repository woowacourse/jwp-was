package fileloader;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

public class TemplateFileLoader extends ClassPathTemplateLoader {
    private static final String TEMPLATE_RESOURCE_PATH_PREFIX = "/templates";
    private static final String TEMPLATE_RESOURCE_PATH_SUFFIX = ".html";

    private TemplateFileLoader() {
    }

    private TemplateFileLoader(String prefix, String suffix) {
        super(prefix, suffix);
    }

    private static class TemplateFileLoaderLazyHolder {
        private static final TemplateFileLoader INSTANCE = new TemplateFileLoader(TEMPLATE_RESOURCE_PATH_PREFIX, TEMPLATE_RESOURCE_PATH_SUFFIX);
    }

    public static TemplateFileLoader getInstance() {
        return TemplateFileLoaderLazyHolder.INSTANCE;
    }
}
