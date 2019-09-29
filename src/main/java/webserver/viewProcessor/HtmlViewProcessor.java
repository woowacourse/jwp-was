package webserver.viewProcessor;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import utils.FileIoUtils;
import webserver.MimeType;
import webserver.ResponseProcessor;
import webserver.ViewProcessor;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HtmlViewProcessor implements ViewProcessor {

    public static final String HTML_SUFFIX = ".html";
    private static final String HTML_ROUTE = "/templates";

    @Override
    public boolean isSupported(String viewName) {
        return viewName.endsWith(HTML_SUFFIX);
    }

    @Override
    public void process(DataOutputStream dos, String viewName, HttpResponse httpResponse) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();

        byte[] bytes = initViewData(httpResponse, viewName);
        setResponseBody(httpResponse, viewName, bytes);
        responseProcessor.forward(dos, bytes, httpResponse);
    }

    private byte[] initViewData(HttpResponse response, String viewName) {
        if (response.hasBody()) {
            String view = viewName.split("\\.")[0];
            String key = response.getBodyKey();
            Object data = response.getBodyValue();
            return getTemplateData(view, key, data);
        }

        try {
            return FileIoUtils.loadFileFromClasspath("." + HTML_ROUTE + viewName);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
        }
    }

    private byte[] getTemplateData(String view, String key, Object value) {
        Handlebars handlebars = initHandlebar();
        Template template = initTemplate(view, handlebars);

        Map<String, Object> templateData = new HashMap<>();
        templateData.put(key, value);
        try {
            return template.apply(templateData).getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("올바르지 않는 뷰 모델 입니다.");
        }
    }

    private Handlebars initHandlebar() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(HTML_ROUTE);
        loader.setSuffix(HTML_SUFFIX);
        return new Handlebars(loader);
    }

    private Template initTemplate(String view, Handlebars handlebars) {
        try {
            return handlebars.compile(view);
        } catch (IOException e) {
            throw new IllegalArgumentException("올바르지 않은 뷰 입니다.");
        }
    }

    private void setResponseBody(HttpResponse httpResponse, String viewName, byte[] bytes) {
        httpResponse.setContentType(MimeType.values(viewName));
        httpResponse.setContentLength(bytes.length);
    }
}
