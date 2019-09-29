package view.internal;

public interface TemplateEngineViewResolver extends InternalResourceViewResolver{
    void setPrefix(final String prefix);

    void setSuffix(final String suffix);
}
