package view;

public interface ViewMatcher {
    boolean match(String viewName);

    View getView(String viewName);
}
