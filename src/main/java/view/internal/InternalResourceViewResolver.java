package view.internal;

import view.View;
import view.ViewResolver;
import webserver.http.response.HttpResponse;

import java.io.IOException;

public interface InternalResourceViewResolver extends ViewResolver {

    View resolveView(final HttpResponse httpResponse) throws IOException;

    void setPrefix(final String prefix);

    void setSuffix(final String suffix);
}
