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

    public void updateStatusLine(HttpStatus httpStatus) {
        statusLine.setHttpStatus(httpStatus);
    }

    public void updateResponseHeader(ModelAndView modelAndView) {
        if (modelAndView.isRedirectView()) {
            responseHeader.addHeader("Location: ", modelAndView.getView());
        }
        responseHeader.addHeader("Content-Type: ", modelAndView.getMediaType().getContentType() + ";charset=utf-8");
        responseHeader.addHeader("Content-Length: ", String.valueOf(modelAndView.getByteView().length));
    }

    public void updateResponseBody(ModelAndView modelAndView) {
        responseBody.setBody(modelAndView.getByteView());
    }

    public byte[] getResponseBody() {
        return responseBody.getBody();
    }

    public void addCookie(Cookie cookie) {
        cookies.put(cookie.getName(), cookie);
    }

    public String writeHeader() {
        return this.responseHeader.toString();
    }

    public String writeStatusLine() {
        return this.statusLine.toString() + "\r\n";
    }

    public String writeCookie() {
        StringBuilder sb = new StringBuilder();
        for (String key : cookies.keySet()) {
            sb.append("Set-cookie: ");
            sb.append(cookies.get(key).toString());
        }
        return sb.toString();
    }
}
