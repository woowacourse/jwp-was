package webserver.response;

import webserver.request.HttpVersion;

import java.util.ArrayList;
import java.util.List;

public class HttpResponse {
    private HttpVersion httpVersion;
    private ResponseStatus responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse(HttpVersion version) {
        this.httpVersion = version;
        this.responseStatus = ResponseStatus.OK;
        this.responseHeaders = new ResponseHeaders();
    }

    public HttpResponse(ResponseStatus responseStatus, ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.httpVersion = HttpVersion.HTTP_1_1;
        this.responseStatus = responseStatus;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    private static HttpResponse sendErrorResponse(ResponseStatus responseStatus) {
        return new HttpResponse(
                responseStatus,
                new ResponseHeaders(),
                new ResponseBody(String.format("error/%d.html", responseStatus.getCode())));
    }

    public static HttpResponse notFound() {
        return sendErrorResponse(ResponseStatus.NOT_FOUND);
    }

    public static HttpResponse internalServerError() {
        return sendErrorResponse(ResponseStatus.INTERNAL_SERVER_ERROR);
    }

    public static HttpResponse methodNotAllowed() {
        return sendErrorResponse(ResponseStatus.METHOD_NOT_ALLOWED);
    }

    public void forward(String filePath) {
        responseBody = new ResponseBody(filePath);
        responseHeaders.put("Content-Length", responseBody.getBodyLength());
    }

    public Object getHeader(String key) {
        return responseHeaders.get(key);
    }

    public void setContentType(String contentType) {
        responseHeaders.put("Content-Type", contentType);
    }

    public void setContentType(MediaType contentType) {
        responseHeaders.put("Content-Type", contentType.getMediaType());
    }

    public void sendRedirect(String uriPath) {
        responseHeaders.put("Location", uriPath);
        setResponseStatus(ResponseStatus.FOUND);
    }

    public List<String> responseBuilder() {
        List<String> responseExport = new ArrayList<>();

        responseExport.add(
                String.format("%s %d %s\r\n", httpVersion.getVersion(), responseStatus.getCode(), responseStatus.name()));
        responseHeaders.keySet().forEach(key ->
                responseExport.add(String.format("%s: %s\r\n", key, responseHeaders.get(key))));
        responseExport.add("\r\n");

        if (responseBody != null) {
            responseExport.add(new String(responseBody.getBody()));
        }
        return responseExport;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getViewPath() {
        return responseBody.getPath();
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
