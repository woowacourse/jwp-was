package webserver.controller;

import webserver.http.Body;
import webserver.http.ContentType;
import webserver.http.HttpHeaders;
import webserver.http.Url;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseStartLine;

import java.io.IOException;
import java.util.Collections;

public class IndexController extends AbstractController {
    public IndexController() {
        this.paths = Collections.singletonList("/");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        HttpResponseStartLine httpResponseStartLine = response200StartLine();

        Url url = httpRequest.getUrl();
        ContentType contentType = ContentType.from(url);
        byte[] body = "Hello World".getBytes();
        HttpHeaders httpResponseHeaders = responseWithContent(contentType, body);

        Body httpResponseBody = Body.of(body);

        httpResponse.forward(httpResponseStartLine, httpResponseHeaders, httpResponseBody);
    }
}
