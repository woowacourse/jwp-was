package webserver.viewProcessor;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import utils.FileIoUtils;
import webserver.MimeType;
import webserver.ResponseProcessor;
import webserver.View;
import webserver.ViewProcessor;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HtmlViewProcessor implements ViewProcessor {

    //TODO public -> private
    public static final String HTML_SUFFIX = ".html";
    private static final String HTML_ROUTE = "/templates";

    @Override
    public boolean isSupported(View view) {
        return view.nameEndedWith(HTML_SUFFIX);
    }

    @Override
    public void process(DataOutputStream dos, View view, HttpResponse httpResponse) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();

        byte[] bytes = initViewData(view);
        setResponseBody(httpResponse, view, bytes);
        responseProcessor.forward(dos, bytes, httpResponse);
    }

    private byte[] initViewData(View view) {
        if (view.isTemplate()) {
            return getTemplateView(view);
        }

        return getNonTemplateView(view);
    }

    private byte[] getTemplateView(View view) {
        Handlebars handlebars = initHandlebar();
        Template template = initTemplate(view.getName(), handlebars);
        Map<String, Object> templateData = initTemplateData(view);

        try {
            return template.apply(templateData).getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("올바르지 않는 뷰 모델 입니다.");
        }
    }

    private Map<String, Object> initTemplateData(View view) {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put(view.getModelKey(), view.getModelValue());
        return templateData;
    }

    private Handlebars initHandlebar() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(HTML_ROUTE);
        loader.setSuffix(HTML_SUFFIX);
        return new Handlebars(loader);
    }

    private Template initTemplate(String viewName, Handlebars handlebars) {
        try {
            String templateViewName = viewName.split("\\.")[0];
            return handlebars.compile(templateViewName);
        } catch (IOException e) {
            throw new IllegalArgumentException("올바르지 않은 뷰 입니다.");
        }
    }

    private byte[] getNonTemplateView(View view) {
        try {
            return FileIoUtils.loadFileFromClasspath("." + HTML_ROUTE + view.getName());
        } catch (IOException | URISyntaxException e) {

            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
        }
    }

    private void setResponseBody(HttpResponse httpResponse, View view, byte[] bytes) {
        httpResponse.setContentType(MimeType.values(view.getName()));
        httpResponse.setContentLength(bytes.length);
    }
}
