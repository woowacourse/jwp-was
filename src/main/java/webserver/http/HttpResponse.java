package webserver.http;

import webserver.MimeType;
import webserver.http.cookie.Cookie;
import webserver.http.cookie.Cookies;
import webserver.http.httpRequest.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpRequest.JSESSION_ID;

public class HttpResponse {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";

    private Map<String, String> header = new HashMap<>();
    private HttpStatus httpStatus;
    private Cookies cookies = new Cookies();

    public HttpResponse(HttpRequest httpRequest) {
        if (httpRequest.hasSession()) {
            cookies.addCookie(JSESSION_ID, httpRequest.getSessionId());
        }
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setContentType(MimeType contentType) {
        header.put(CONTENT_TYPE, contentType.getMimeType());
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

    public void setCookie(String key, String value) {
        cookies.addCookie(key, value);
    }

    public void setSession(String uuid) {
        cookies.addCookie(JSESSION_ID, uuid);
    }

    public Map<String, Cookie> getCookies() {
        return cookies.getCookies();
    }
}
