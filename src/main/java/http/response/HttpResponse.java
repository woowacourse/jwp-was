package http.response;

import http.HttpHeader;
import http.HttpVersion;

public class HttpResponse {
    private static final String CRLF = "\r\n";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String LOCATION_KEY = "Location";
    private static final int OK_CODE = 200;
    private static final int FOUND_CODE = 302;

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
        addHeader(LOCATION_KEY, "/" + viewName);
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

    public String getHeaderLines() {
        return header.toString();
    }

    public String getStatusLine() {
        return HttpVersion.V_1_1.getVersion() + " " +
                status.getStatusCode() + " " +
                status.getStatus() +
                CRLF;
    }

    public HttpResponseStatus getStatus() {
        return status;
    }

    public byte[] getBody() {
        return body;
    }
}
