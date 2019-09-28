package view;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class ViewResolver {
    public static final String REDIRECT_SIGNATURE = "redirect: ";
    private static final String TEMPLATES_PREFIX = "/templates";
    private static final String TEMPLATES_SUFFIX = ".html";
    private static final TemplateEngineManager HANDLEBARS_MANAGER = new HandlebarsManager(
            new ClassPathTemplateLoader(), TEMPLATES_PREFIX, TEMPLATES_SUFFIX);
    private static final Map<Predicate<String>, Function<String, View>> VIEWS = new HashMap<>();

    static {
        VIEWS.put(viewName -> viewName.startsWith(REDIRECT_SIGNATURE), RedirectView::new);
    }

    private ViewResolver() {
    }

    public static ViewResolver getInstance() {
        return ViewResolverLazyHolder.INSTANCE;
    }

    public View resolve(String viewName) {
        return VIEWS.entrySet().stream()
                .filter(entry -> entry.getKey().test(viewName))
                .map(entry -> entry.getValue().apply(viewName))
                .findAny()
                .orElse(new TemplateView(HANDLEBARS_MANAGER, viewName));
    }

    private static class ViewResolverLazyHolder {
        private static final ViewResolver INSTANCE = new ViewResolver();
    }
}