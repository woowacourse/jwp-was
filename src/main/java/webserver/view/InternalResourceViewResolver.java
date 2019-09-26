package webserver.view;

import webserver.http.response.HttpResponse;

import java.io.IOException;

public interface InternalResourceViewResolver extends ViewResolver {

    View resolveViewName(final HttpResponse httpResponse) throws IOException;

    void setPrefix(final String prefix);

    void setSuffix(final String suffix);
}
