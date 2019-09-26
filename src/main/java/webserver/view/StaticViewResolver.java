package webserver.view;

public class StaticViewResolver implements ViewResolver {
    private final StaticResourceMapping mapping;

    public StaticViewResolver(final StaticResourceMapping mapping) {
        this.mapping = mapping;
    }

    public boolean isStaticFile(final String name){
        return mapping.isMapping(name);
    }

    @Override
    public View resolveViewName(final String name) {
        final String viewName = mapping.addPrefix(name);
        return new StaticView(viewName);
    }
}
