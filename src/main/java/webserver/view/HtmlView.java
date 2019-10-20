package webserver.view;

import utils.FileIoUtils;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class HtmlView implements View {
    private static final String CONTENT_TYPE_HTML = "text/html";
    private static final String CHARSET_UTF8 = "charset=utf-8";
    private static final String SEMICOLON = ";";

    private final String viewName;

    public HtmlView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView) {
        httpResponse.forward(httpRequest, FileIoUtils.compileTemplate(modelAndView),
                CONTENT_TYPE_HTML + SEMICOLON + CHARSET_UTF8);
    }

    @Override
    public String getViewName() {
        return viewName;
    }
}
