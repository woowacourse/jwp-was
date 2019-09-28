package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpHeaderField;
import webserver.http.HttpVersion;
import webserver.http.response.core.ResponseContentType;
import webserver.http.response.core.ResponseHeader;
import webserver.http.response.core.ResponseStatus;
import webserver.http.response.core.ResponseStatusLine;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String CRLF = "\r\n";

    private HttpVersion httpVersion;
    private ResponseStatusLine responseStatusLine;
    private ResponseHeader responseHeader;

    public HttpResponse(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
        responseHeader = new ResponseHeader();
    }

    public void addStatus() {
        responseStatusLine = new ResponseStatusLine(httpVersion);
    }

    public void addStatus(ResponseStatus responseStatus) {
        responseStatusLine = new ResponseStatusLine(httpVersion, responseStatus);
    }

    public void addHeader(HttpHeaderField key, ResponseContentType contentType) {
        responseHeader.addHeaders(key, contentType);
    }

    public void addHeader(HttpHeaderField key, String value) {
        responseHeader.addHeaders(key, value);
    }

    public String doResponse() {
        log.debug("doResponse: {}", responseStatusLine.getStatusLine() +
                responseHeader.getResponseHeaders() + CRLF + CRLF);
        return responseStatusLine.getStatusLine() +
                responseHeader.getResponseHeaders() + CRLF + CRLF;
    }

    public byte[] responseBody(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
    }

    public boolean hasField(String contentLength) {
        return responseHeader.hasResponseField(contentLength);
    }
}
