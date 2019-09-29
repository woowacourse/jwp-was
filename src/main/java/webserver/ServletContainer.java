package webserver;

import file.FileContainer;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.servlet.HttpServletHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServletContainer {

    private final FileContainer fileContainer;
    private final HttpServletHandler httpServletHandler;

    public ServletContainer(final FileContainer fileContainer, final HttpServletHandler httpServletHandler) {
        this.fileContainer = fileContainer;
        this.httpServletHandler = httpServletHandler;
    }

    public void process(final HttpRequest httpRequest,
                        final HttpResponse httpResponse) throws IOException, URISyntaxException {

        if (!fileContainer.process(httpRequest, httpResponse)) {
            httpServletHandler.process(httpRequest, httpResponse);
        }
    }
}
