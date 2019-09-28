package http.response;

import http.HttpHeader;
import http.HttpVersion;

import static http.HttpHeader.*;

public class HttpResponse {
    private HttpStatus status;
    private HttpHeader header = new HttpHeader();
    private byte[] body;

    public HttpResponse() {
    }

    public void response2xx(byte[] body, String mimeType) {
        setStatus(HttpStatus.OK.getStatusCode());
        addHeader(CONTENT_TYPE_KEY, mimeType);
        setBody(body);
    }

    public void response3xx(String viewName) {
        setStatus(HttpStatus.FOUND.getStatusCode());
        addHeader(LOCATION_KEY, "/" + viewName);
    }

    public void addHeader(String key, String value) {
        header.addHeader(key, value);
    }

    public void setStatus(int statusCode) {
        status = HttpStatus.of(statusCode);
    }

    public void setBody(byte[] body) {
        this.body = body;
        header.addHeader(CONTENT_LENGTH_KEY, String.valueOf(body.length));
    }

    public String getStatusLine() {
        return String.format("%s %s %s\r\n",
                HttpVersion.V_1_1.getVersion(),
                status.getStatusCode(),
                status.getStatus());
    }

    public HttpStatus getStatus() {
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
