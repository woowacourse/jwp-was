package webserver.view;

import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class RedirectView implements View {
    private final String viewName;

    public RedirectView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView) {
        httpResponse.sendRedirect(httpRequest, viewName);
    }

    @Override
    public String getViewName() {
        return viewName;
    }
}
