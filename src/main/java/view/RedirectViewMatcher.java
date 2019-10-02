package view;

import utils.StringUtils;

import static utils.StringUtils.BLANK;

public class RedirectViewMatcher implements ViewMatcher {
    public static final String REDIRECT_SIGNATURE = "redirect: ";

    @Override
    public boolean match(String viewName) {
        return StringUtils.isNotEmpty(viewName) &&
                viewName.startsWith(REDIRECT_SIGNATURE);
    }

    @Override
    public View getView(String viewName) {
        return new RedirectView(viewName.replace(REDIRECT_SIGNATURE, BLANK));
    }
}
