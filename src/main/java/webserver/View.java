package webserver;

import java.util.Objects;

public final class View {
    private final String viewName;
    private final TemplateModel templateModel;

    public View(String viewName, TemplateModel templateModel) {
        this.viewName = viewName;
        this.templateModel = templateModel;
    }

    public View(String viewName) {
        this(viewName, null);
    }

    public View(String viewName, String key, Object value) {
        this(viewName, new TemplateModel(key, value));
    }

    public boolean nameStartedWith(String viewNameCondition) {
        return viewName.startsWith(viewNameCondition);
    }

    public boolean nameEndedWith(String viewNameCondition) {
        return viewName.endsWith(viewNameCondition);
    }

    public String getName() {
        return viewName;
    }

    public boolean isTemplate() {
        return Objects.nonNull(templateModel);
    }

    public String getModelKey() {
        return templateModel.getKey();
    }

    public Object getModelValue() {
        return templateModel.getValue();
    }
}
