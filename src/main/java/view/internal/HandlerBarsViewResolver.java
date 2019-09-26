package view.internal;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import view.EmptyView;
import view.View;
import webserver.http.HttpHeaders;
import webserver.http.MimeType;
import webserver.http.response.HttpResponse;
import webserver.http.utils.HttpUtils;

import java.io.IOException;
import java.util.Map;

public class HandlerBarsViewResolver implements InternalResourceViewResolver {
    private static final String DEFAULT_PREFIX = "/templates";
    private static final String DEFAULT_SUFFIX = ".html";

    private final Handlebars handlebars;
    private String prefix = DEFAULT_PREFIX;
    private String suffix = DEFAULT_SUFFIX;

    public HandlerBarsViewResolver() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(suffix);
        handlebars = new Handlebars(loader);
    }

    @Override
    public View resolveView(final HttpResponse httpResponse) throws IOException {
        final String resource = httpResponse.getResource();

        if (resource == null) {
            return EmptyView.getInstance();
        }

        final Map<String, Object> attributes = httpResponse.getAttributes();
        final HandleBarsView view = new HandleBarsView(handlebars, resource, attributes);

        httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, MimeType.getType(HttpUtils.parseExtension(resource + suffix)));
        httpResponse.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(view.getContentLength()));
        return view;
    }

    @Override
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
}
