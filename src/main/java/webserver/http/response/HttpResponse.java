package webserver.http.response;

import webserver.http.Cookie;
import webserver.http.HttpStatus;
import webserver.http.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private StatusLine statusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;
    private Map<String, Cookie> cookies = new HashMap<>();

    private HttpResponse(String httpVersion) {
        this.statusLine = new StatusLine(httpVersion);
        this.responseHeader = new ResponseHeader();
        this.responseBody = new ResponseBody();
    }

    public static HttpResponse of(String httpVersion) {
        return new HttpResponse(httpVersion);
    }

    public void addCookie(Cookie cookie) {
        cookies.put(cookie.getName(), cookie);
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public byte[] getResponseBody() {
        return responseBody.getBody();
    }
}
