package webserver.view;

public class ViewResolverList {
    public static final ViewResolver STATIC_RESOLVER = new StaticResourceResolver();
    public static final ViewResolver TEMPLATE_RESOLVER = new TemplateResourceResolver();
}
