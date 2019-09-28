package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class TemplateManager {
    private static final String TEMPLATES_PREFIX = "/templates";
    private static final String TEMPLATES_SUFFIX = ".html";
    private static final TemplateLoader LOADER = new ClassPathTemplateLoader();
    private static final Handlebars HANDLEBARS = new Handlebars(LOADER);

    private static class TemplateManagerLazyHolder {
        private static final TemplateManager INSTANCE = new TemplateManager();
    }

    public static TemplateManager getInstance() {
        return TemplateManagerLazyHolder.INSTANCE;
    }

    private TemplateManager() {

    }

    static {
        LOADER.setPrefix(TEMPLATES_PREFIX);
        LOADER.setSuffix(TEMPLATES_SUFFIX);
    }

    public byte[] render(ModelAndView modelAndView) throws IOException {
        Template template = HANDLEBARS.compile(modelAndView.getViewName());
        return template.apply(modelAndView.getModelMap()).getBytes();
    }
}
