package view;

import fileloader.TemplateFileLoader;

import java.util.ArrayList;
import java.util.List;

public class ViewResolver {
    private final List<ViewMatcher> VIEW_MATCHERS = new ArrayList<>();
    private final TemplateManager TEMPLATE_MANAGER = new HandlebarsManager(TemplateFileLoader.getInstance());

    {
        VIEW_MATCHERS.add(new RedirectViewMatcher());
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
        return VIEW_MATCHERS.stream()
                .filter(viewMatcher -> viewMatcher.match(viewName))
                .findAny()
                .map(viewMatcher -> viewMatcher.getView(viewName))
                .orElse(new TemplateView(viewName, TEMPLATE_MANAGER));
    }
}
