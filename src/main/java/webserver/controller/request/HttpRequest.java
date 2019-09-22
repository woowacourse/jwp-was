package webserver.controller.request;

import exception.ContentTypeNotFoundException;
import webserver.controller.request.header.HttpBeginningHeader;
import webserver.controller.request.header.HttpHeaderFields;
import webserver.controller.response.ContentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class HttpRequest {
    private final BufferedReader bufferedReader;
    private final HttpBeginningHeader httpBeginningHeader;
    private final HttpHeaderFields httpHeaderFields;

    public HttpRequest(final InputStream inputStream) throws IOException {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        this.httpBeginningHeader = new HttpBeginningHeader(bufferedReader);
        this.httpHeaderFields = new HttpHeaderFields(bufferedReader);
    }

    public byte[] getResponseBody(ContentType contentType) throws IOException, URISyntaxException {
        return httpBeginningHeader.getResponseBody(contentType);
    }

    public ContentType getContentType() throws ContentTypeNotFoundException {
        return httpBeginningHeader.getContentType();
    }

    public String readData() throws IOException {
        return httpHeaderFields.readData(bufferedReader);
    }

    public String getPath() {
        return httpBeginningHeader.getUrl();
    }
}
