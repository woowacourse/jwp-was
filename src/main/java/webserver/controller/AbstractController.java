package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class AbstractController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
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

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.forward(httpRequest.getPath());
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.forward(httpRequest.getPath());
    }
}
