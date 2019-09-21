package webserver.httpResponse;

import webserver.httpRequest.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private Map<String, String> header = new HashMap<>();
    private HttpStatus httpStatus;

    public void forward() {
        setHttpStatus(HttpStatus.OK);
    }

    public void sendRedirect() {
        setHttpStatus(HttpStatus.FOUND);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public String getContentType() {
        String contentType = header.get("Content-Type");
        if (contentType == null) {
            return "text/plain";
        }
        return contentType;
    }

    public void setContentType(String contentType) {
        header.put("Content-Type", contentType);
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}
