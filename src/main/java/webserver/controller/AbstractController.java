package webserver.controller;

import webserver.http.HttpHeader;
import webserver.http.HttpHeaderType;
import webserver.http.URL;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public abstract class AbstractController implements Controller {
    private static final String COOKIE_DELIMITER_REGEX = "; ";

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

    protected String[] parseCookie(HttpRequest httpRequest) {
        String cookieHeader = httpRequest.getHeader(HttpHeader.of(HttpHeaderType.COOKIE));
        return cookieHeader.split(COOKIE_DELIMITER_REGEX);
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    }
}
