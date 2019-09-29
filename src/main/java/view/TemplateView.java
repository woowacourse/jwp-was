package view;

import http.common.ContentType;
import http.response.HttpResponse;
import http.response.ResponseStatus;

import java.util.Map;

public class TemplateView implements View {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CHARSET_UTF_8 = ";charset=utf-8";
    private final String viewName;
    private final TemplateManager templateManager;

    public TemplateView(String viewName, TemplateManager templateManager) {
        this.viewName = viewName;
        this.templateManager = templateManager;
    }

    @Override
    public void render(Map<String, Object> model, HttpResponse httpResponse) {
        byte[] body = templateManager.applyCompile(viewName, model);
        httpResponse.setResponseStatus(ResponseStatus.OK);
        httpResponse.addHeaderAttribute(CONTENT_TYPE, ContentType.HTML + CHARSET_UTF_8);
        httpResponse.addHeaderAttribute(CONTENT_LENGTH, String.valueOf(body.length));
        httpResponse.setBody(body);
    }
}
