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

    public static ModelAndView of(final StatusCode statusCode, final Map<String, String> headers, final Map<String, String> body, final String viewName) {
        ResponseHeader responseHeader = new ResponseHeader(headers);
        ResponseBody responseBody = new ResponseBody(body);

        return new ModelAndView(statusCode, responseHeader, responseBody, viewName);
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
