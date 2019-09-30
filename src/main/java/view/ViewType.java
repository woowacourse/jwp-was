package view;

public enum ViewType {
    RESOURCE_FILE_VIEW("resource"),
    TEMPLATE_ENGINE_VIEW("template"),
    REDIRECT_VIEW("redirect");

    private final String viewType;

    ViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getViewType() {
        return this.viewType;
    }
}
