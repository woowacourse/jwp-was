package webserver.view;

import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.FileNotFoundException;

public interface View {
    void render(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView) throws FileNotFoundException;

    String getViewName();
}
