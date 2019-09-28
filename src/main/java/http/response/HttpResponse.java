package http.response;

import http.HttpHeader;
import http.HttpVersion;

import static http.HttpString.*;

public class HttpResponse {
    private HttpResponseStatus status;
    private HttpHeader header = new HttpHeader();
    private byte[] body;

    public HttpResponse() {
    }

    public void response2xx(byte[] body, String mimeType) {
        setStatus(OK_CODE);
        addHeader(CONTENT_TYPE_KEY, mimeType);
        setBody(body);
    }

    public void response3xx(String viewName) {
        setStatus(FOUND_CODE);
        addHeader(LOCATION_KEY, SLASH + viewName);
    }

    public void addHeader(String key, String value) {
        header.addHeader(key, value);
    }

    public void setStatus(int statusCode) {
        status = HttpResponseStatus.of(statusCode);
    }

    public void setBody(byte[] body) {
        this.body = body;
        header.addHeader(CONTENT_LENGTH_KEY, String.valueOf(body.length));
    }

    public String getStatusLine() {
        return HttpVersion.V_1_1.getVersion() + WHITE_SPACE +
                status.getStatusCode() + WHITE_SPACE +
                status.getStatus() +
                CRLF;
    }

    public HttpResponseStatus getStatus() {
        return status;
    }

    public String getHeaderLines() {
        return header.toString();
    }

    public String getHeaderAttribute(String key) {
        return header.getValue(key);
    }

    public byte[] getBody() {
        return body;
    }
}
