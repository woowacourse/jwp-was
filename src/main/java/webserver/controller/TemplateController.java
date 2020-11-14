package webserver.controller;

import utils.FileIoUtils;
import webserver.http.Body;
import webserver.http.ContentType;
import webserver.http.HttpHeaders;
import webserver.http.Url;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseStartLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class TemplateController extends AbstractController {
    public TemplateController() {
        this.paths = Arrays.asList(".html", ".ico");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        HttpResponseStartLine httpResponseStartLine = response200StartLine();

        Url url = httpRequest.getUrl();
        ContentType contentType = ContentType.from(url);
        String filePath = contentType.getDirectory() + url.getUrl();
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        HttpHeaders httpResponseHeaders = responseWithContent(contentType, body);

        Body httpResponseBody = Body.of(body);

        httpResponse.forward(httpResponseStartLine, httpResponseHeaders, httpResponseBody);
    }
}
