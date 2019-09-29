package http.response;

import http.HttpHeaders;
import http.HttpVersion;
import view.EmptyView;
import view.SimpleView;
import view.View;

import static http.HttpHeaders.LOCATION;
import static http.HttpHeaders.SET_COOKIE;
import static http.response.HttpStatus.FOUND;
import static http.response.HttpStatus.OK;

public class HttpResponse {
    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";

    private HttpVersion version;
    private HttpStatus status;
    private HttpHeaders headers;
    private View view;

    public HttpResponse(HttpVersion version) {
        this.version = version;
        this.status = OK;
        this.headers = new HttpHeaders();
        this.view = new EmptyView();
    }

    public void redirect(String location) {
        status = FOUND;
        headers.put(LOCATION, location);
        this.view = new EmptyView();
    }

    public void error(HttpStatus status) {
        headers.clear();
        this.status = status;
        this.view = new SimpleView(status.getMessage());
    }

    public HttpVersion getVersion() {
        return version;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setCookie(String key, String value) {
        String cookie = key + KEY_VALUE_DELIMITER + value;
        if (headers.existHeader(SET_COOKIE)) {
            cookie = headers.getHeader(SET_COOKIE) + COOKIE_DELIMITER + cookie;
        }
        headers.put(SET_COOKIE, cookie);
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
