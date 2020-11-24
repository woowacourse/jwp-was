package kr.wootecat.dongle.model.servlet;

import static com.google.common.base.Charsets.*;
import static kr.wootecat.dongle.model.http.MimeType.*;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import kr.wootecat.dongle.model.http.exception.ResourceNotFoundException;
import kr.wootecat.dongle.model.http.response.HttpResponse;

public class TemplateRenderer {

    private static final String DEFAULT_PREFIX = "/templates";
    private static final String DEFAULT_SUFFIX = ".html";

    private final String prefix;
    private final String suffix;

    private TemplateRenderer(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public static TemplateRenderer ofDefault() {
        return new TemplateRenderer(DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    public void forward(HttpResponse response, String url, Object context) {
        TemplateLoader loader = new ClassPathTemplateLoader(prefix, suffix);
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(url);
            String userListPage = template.apply(context);
            response.addBody(userListPage.getBytes(UTF_8), HTML_UTF_8);
        } catch (IOException e) {
            throw new ResourceNotFoundException(url);
        }
    }
}
