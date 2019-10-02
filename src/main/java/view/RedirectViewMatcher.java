package view;

public class RedirectViewMatcher implements ViewMatcher {
    private static final String REDIRECT_VIEW_PREFIX = "redirect: ";

    @Override
    public boolean match(String viewName) {
        return viewName.startsWith(REDIRECT_VIEW_PREFIX);
    }

    @Override
    public View getView(String viewName) {
        return new RedirectView(viewName);
    }
}
