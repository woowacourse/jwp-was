package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpHeaderField;
import webserver.http.HttpVersion;
import webserver.http.request.HttpRequest;
import webserver.http.response.core.ResponseContentType;
import webserver.http.response.core.ResponseHeader;
import webserver.http.response.core.ResponseStatus;
import webserver.http.response.core.ResponseStatusLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    public void sendResponse(OutputStream out, HttpRequest httpRequest) throws IOException, URISyntaxException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(doResponse());
        if (isOK()) {
            byte[] body = responseBody(httpRequest.getRequestPath().getFullPath());
            addHeader(HttpHeaderField.CONTENT_LENGTH, String.valueOf(body.length));
            dos.write(body, 0, body.length);
        }
        dos.flush();
    }

    private byte[] responseBody(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
    }

    private boolean isOK() {
        return responseStatusLine.getResponseStatusCode() == ResponseStatus.OK.getHttpStatusCode();
    }
}
