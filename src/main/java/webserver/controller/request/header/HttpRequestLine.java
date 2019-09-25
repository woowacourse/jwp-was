package webserver.controller.request.header;

import exception.ContentTypeNotFoundException;
import jdk.jfr.ContentType;
import utils.FileIoUtils;
import webserver.controller.request.MimeType;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpRequestLine {
    private final HttpMethod httpMethod;
    private final String url;
    private final String version;

    public HttpRequestLine(String[] requestLine) {
        this.httpMethod = HttpMethod.match(requestLine[0]);
        this.url = requestLine[1];
        this.version = requestLine[2];
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public MimeType getMimeType() {
        return MimeType.match(url);
    }
}
