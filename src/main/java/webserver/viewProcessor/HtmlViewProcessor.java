package webserver.viewProcessor;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.MimeType;
import webserver.ResponseProcessor;
import webserver.ViewProcessor;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlViewProcessor implements ViewProcessor {

    private static final String HTML_ROUTE = "/templates";
    public static final String HTML_SUFFIX = ".html";

    @Override
    public boolean isSupported(String viewName) {
        return viewName.endsWith(".html");
    }

    @Override
    public void process(DataOutputStream dos, String viewName, HttpResponse httpResponse) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();

        String view = viewName.split("\\.")[0];
        String key = httpResponse.getBodyKey();
        Object data = httpResponse.getBodyValue();
        byte[] bytes = getTemplateData(view, key, data);

        setResponseBody(httpResponse, viewName, bytes);
        responseProcessor.forward(dos, bytes, httpResponse);
    }

    private byte[] getTemplateData(String view, String key, Object value) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(HTML_ROUTE);
        loader.setSuffix(HTML_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);

        Template template = null;
        try {
            template = handlebars.compile(view);
        } catch (IOException e) {
            throw new IllegalArgumentException("올바르지 않은 뷰 입니다.");
        }
        Map<String, Object> templateData = new HashMap<>();
        templateData.put(key, value);
        try {
            return template.apply(templateData).getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("올바르지 않는 뷰 모델 입니다.");
        }
    }

    private void setResponseBody(HttpResponse httpResponse, String viewName, byte[] bytes) {
        httpResponse.setContentType(MimeType.values(viewName));
        httpResponse.setContentLength(bytes.length);
    }
}
