package view;

import http.common.ContentType;
import http.response.HttpResponse;
import http.response.ResponseStatus;

import java.util.Map;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class TemplateView implements View {
    private static final String CHARSET_UTF_8 = ";charset=utf-8";

    private final TemplateEngineManager templateEngineManager;
    private final String viewName;

    public TemplateView(TemplateEngineManager templateEngineManager, String viewName) {
        this.templateEngineManager = templateEngineManager;
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, Object> model, HttpResponse httpResponse) {
        byte[] body = templateEngineManager.applyCompile(viewName, model);
        httpResponse.setResponseStatus(ResponseStatus.OK);
        httpResponse.addHeaderAttribute(CONTENT_TYPE, ContentType.HTML + CHARSET_UTF_8);
        httpResponse.addHeaderAttribute(CONTENT_LENGTH, String.valueOf(body.length));
        httpResponse.setBody(body);
    }
}
