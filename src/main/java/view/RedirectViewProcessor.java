package view;

import http.response.HttpResponse;

public class RedirectViewProcessor implements ViewProcessor {
    static final String REDIRECT = "redirect: ";
    private static final String SPACE = " ";
    private static final int LOCATION_INDEX = 1;

    @Override
    public byte[] render(ModelAndView modelAndView, HttpResponse response) {
        String location = modelAndView.getView().split(SPACE)[LOCATION_INDEX];
        response.redirect(location);

        return new byte[]{};
    }

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith(REDIRECT);
    }
}
