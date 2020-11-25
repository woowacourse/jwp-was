package was.webserver.controller;

import was.webserver.http.URL;
import was.webserver.http.request.HttpRequest;
import was.webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public abstract class AbstractController implements Controller {
    protected List<String> paths;

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.isGetRequest()) {
            doGet(httpRequest, httpResponse);
            return;
        }
        if (httpRequest.isPostRequest()) {
            doPost(httpRequest, httpResponse);
        }
    }

    @Override
    public boolean isUrlPath(HttpRequest httpRequest) {
        URL url = httpRequest.getUrl();
        return paths.stream()
                .anyMatch(url::isEndsWith);
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    }
}
