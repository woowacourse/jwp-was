package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class AbstractController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);
    private static final String ERROR_HTML_URL = "/error.html";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String acceptValue = httpRequest.getHeader("Accept");
            httpResponse.setContentType(acceptValue);

            if (!httpRequest.isMethodSupported()) {
                httpResponse.methodNotAllowed(ERROR_HTML_URL);
                return;
            }

            if (Objects.isNull(httpRequest.getMethod())) {
                doNone(httpResponse);
                return;
            }

            if (HttpMethod.GET.isSame(httpRequest.getMethod())) {
                doGet(httpRequest, httpResponse);
                return;
            }

            if (HttpMethod.POST.isSame(httpRequest.getMethod())) {
                doPost(httpRequest, httpResponse);
            }
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    private void doNone(HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.notImplemented(ERROR_HTML_URL);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.forward(httpRequest.getPath());
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.forward(httpRequest.getPath());
    }
}
