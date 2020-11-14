package webserver.controller;

import webserver.http.*;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseStartLine;
import webserver.http.response.HttpStatusCode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
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
        Url url = httpRequest.getUrl();
        return paths.stream()
                .anyMatch(url::isEndsWith);
    }

    protected HttpResponseStartLine response200StartLine() {
        return new HttpResponseStartLine(HttpVersion.HTTP_1_1, HttpStatusCode.OK);
    }

    protected HttpHeaders responseWithContent(ContentType contentType, byte[] body) {
        HttpHeader headerContentType = HttpHeader.of(HttpHeaderType.CONTENT_TYPE.getType()
                + contentType.getContentType());
        HttpHeader headerContentLength = HttpHeader.of(HttpHeaderType.CONTENT_LENGTH.getType() + body.length);
        List<HttpHeader> httpHeaders = Arrays.asList(headerContentType, headerContentLength);
        return HttpHeaders.of(httpHeaders);
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
