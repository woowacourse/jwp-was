package webserver.controller;

import webserver.http.*;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseStartLine;
import webserver.http.response.HttpStatusCode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
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
        // 베스킨 라빈스 == 31
        // 31 == 쿨라임
        // 베스킨 라빈스 == 쿨라
    }

    @Override
    public boolean isUrlPath(HttpRequest httpRequest) {
        URL url = httpRequest.getUrl();
        return paths.stream()
                .anyMatch(url::isEndsWith);
    }

    protected HttpResponseStartLine response200StartLine() {
        return new HttpResponseStartLine(HttpVersion.HTTP_1_1, HttpStatusCode.OK);
    }

    protected HttpResponseStartLine response302StartLine() {
        return new HttpResponseStartLine(HttpVersion.HTTP_1_1, HttpStatusCode.FOUND);
    }

    protected HttpHeaders responseWithContent(ContentType contentType, byte[] body) {
        HttpHeader headerContentType = HttpHeader.of(HttpHeaderType.CONTENT_TYPE.getType()
                + contentType.getContentType());
        HttpHeader headerContentLength = HttpHeader.of(HttpHeaderType.CONTENT_LENGTH.getType() + body.length);
        List<HttpHeader> httpHeaders = Arrays.asList(headerContentType, headerContentLength);
        return HttpHeaders.of(httpHeaders);
    }

    protected HttpHeaders responseWithLocation(String location) {
        HttpHeader headerLocation = HttpHeader.of(HttpHeaderType.LOCATION.getType() + location);
        List<HttpHeader> httpHeaders = Collections.singletonList(headerLocation);
        return HttpHeaders.of(httpHeaders);
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    }
}
