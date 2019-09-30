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
        responseHeader.addCookie(cookie);
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
