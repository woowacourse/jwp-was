package view;

import fileloader.TemplateFileLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class ViewResolver {
    private static final String REDIRECT = "redirect: ";
    private static final Map<Predicate<String>, Function<String, View>> VIEWS = new HashMap<>();
    private static final TemplateManager TEMPLATE_MANAGER = new HandlebarsManager(TemplateFileLoader.getInstance());

    static {
        VIEWS.put(viewName -> viewName.startsWith(REDIRECT), RedirectView::new);
    }

    private static class ViewResolverLazyHolder {
        private static final ViewResolver INSTANCE = new ViewResolver();
    }

    public static ViewResolver getInstance() {
        return ViewResolverLazyHolder.INSTANCE;
    }

    private ViewResolver() {
    }

    public View resolve(String viewName) {
        return VIEWS.entrySet().stream()
                .filter(entry -> entry.getKey().test(viewName))
                .map(entry -> entry.getValue().apply(viewName))
                .findAny()
                .orElse(new TemplateView(viewName, TEMPLATE_MANAGER));
    }
}
