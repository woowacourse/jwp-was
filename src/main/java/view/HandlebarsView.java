package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.common.ContentType;
import http.response.HttpResponse;
import http.response.ResponseStatus;

import java.io.IOException;
import java.util.Map;

public class HandlebarsView implements View {
    private static final TemplateLoader LOADER = new ClassPathTemplateLoader();
    private static final Handlebars HANDLEBARS = new Handlebars(LOADER);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CHARSET_UTF_8 = ";charset=utf-8";

    private final String viewName;

    public HandlebarsView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, Object> model, HttpResponse httpResponse) {
        try {
            byte[] body = render(model);
            httpResponse.setResponseStatus(ResponseStatus.OK);
            httpResponse.addHeaderAttribute(CONTENT_TYPE, ContentType.HTML + CHARSET_UTF_8);
            httpResponse.addHeaderAttribute(CONTENT_LENGTH, String.valueOf(body.length));
            httpResponse.setBody(body);
        } catch (IOException e) {
            httpResponse.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] render(Map<String, Object> model) throws IOException {
        Template template = HANDLEBARS.compile(viewName);
        return template.apply(model).getBytes();
    }
}
