package view;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import java.util.ArrayList;
import java.util.List;

public class ViewResolver {
    private static final String TEMPLATES_PREFIX = "/templates";
    private static final String TEMPLATES_SUFFIX = ".html";
    private static final TemplateEngineManager HANDLEBARS_MANAGER = new HandlebarsManager(
            new ClassPathTemplateLoader(TEMPLATES_PREFIX, TEMPLATES_SUFFIX));
    private static final List<ViewMatcher> VIEW_MATCHERS = new ArrayList<>();

    static {
        VIEW_MATCHERS.add(new RedirectViewMatcher());
    }

    private ViewResolver() {
    }

    public static ViewResolver getInstance() {
        return ViewResolverLazyHolder.INSTANCE;
    }

    public View resolve(String viewName) {
        return VIEW_MATCHERS.stream()
                .filter(viewMatcher -> viewMatcher.match(viewName))
                .map(viewMatcher -> viewMatcher.getView(viewName))
                .findAny()
                .orElse(new TemplateView(HANDLEBARS_MANAGER, viewName))
                ;
    }

    private static class ViewResolverLazyHolder {
        private static final ViewResolver INSTANCE = new ViewResolver();
    }
}