package servlet;

import file.FileContainer;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServletContainer {

    private final FileContainer fileContainer;
    private final HttpRequestHandler httpRequestHandler;

    public ServletContainer(final FileContainer fileContainer, final HttpRequestHandler httpRequestHandler) {
        this.fileContainer = fileContainer;
        this.httpRequestHandler = httpRequestHandler;
    }

    public void process(final HttpRequest httpRequest,
                        final HttpResponse httpResponse) throws IOException, URISyntaxException {

        if (!fileContainer.process(httpRequest, httpResponse)) {
            httpRequestHandler.process(httpRequest, httpResponse);
        }
    }
}
