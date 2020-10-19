package webserver.response;

import java.util.Map;

public class ModelAndView {
    private final StatusCode statusCode;
    private final ResponseHeader headers;
    private final ResponseBody body;
    private final String viewName;

    private ModelAndView(StatusCode statusCode, ResponseHeader headers, ResponseBody body, String viewName) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
        this.viewName = viewName;
    }

    public static ModelAndView of(StatusCode statusCode, Map<String, String> headers, Map<String, String> body,
        String viewName) {
        ResponseHeader responseHeader = new ResponseHeader(headers);
        ResponseBody responseBody = new ResponseBody(body);

        return new ModelAndView(statusCode, responseHeader, responseBody, viewName);
    }

    public static ModelAndView of(StatusCode statusCode, String viewName) {
        return new ModelAndView(statusCode, ResponseHeader.emptyHeader(), ResponseBody.emptyBody(), viewName);
    }

    public void addHeader(String key, String value) {
        headers.addHeader(key, value);
    }

    public void addBody(String key, String value) {
        body.addBody(key, value);
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public ResponseHeader getHeaders() {
        return headers;
    }

    public ResponseBody getBody() {
        return body;
    }

    public String getViewName() {
        return viewName;
    }
}
