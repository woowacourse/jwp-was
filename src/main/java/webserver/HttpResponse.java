package webserver;

import webserver.httpRequest.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";

    private Map<String, String> header = new HashMap<>();
    private HttpStatus httpStatus;

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getContentType() {
        String contentType = header.get(CONTENT_TYPE);
        if (contentType == null) {
            return "text/plain";
        }
        return contentType;
    }

    public void setContentType(MimeType contentType) {
        header.put(CONTENT_TYPE, contentType.mimeType);
    }

    public void setContentLength(int length) {
        header.put(CONTENT_LENGTH, String.valueOf(length));
    }

    public void setLocation(String location) {
        header.put(LOCATION, location);
    }

    public String getStatusCodeAndMessage() {
        return httpStatus.getCodeAndMessage();
    }

    public Map<String, String> getHeaders() {
        return header;
    }

    public void setCookie(String cookie) {
        header.put("Set-Cookie", cookie);
    }
}
