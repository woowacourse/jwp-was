package webserver.response;

import webserver.request.HttpVersion;

import java.util.ArrayList;
import java.util.List;

import static webserver.response.ResponseHeaders.*;

public class HttpResponse {
    private static final String ERROR_VIEW_FORMAT = "error/%d.html";

    private HttpVersion httpVersion;
    private ResponseStatus responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse(HttpVersion version) {
        this.httpVersion = version;
        this.responseStatus = ResponseStatus.OK;
        this.responseHeaders = new ResponseHeaders();
    }

    private HttpResponse(ResponseStatus responseStatus, ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.httpVersion = HttpVersion.HTTP_1_1;
        this.responseStatus = responseStatus;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse sendErrorResponse(ResponseStatus responseStatus) {
        return new HttpResponse(
                responseStatus,
                new ResponseHeaders(),
                new ResponseBody(String.format(ERROR_VIEW_FORMAT, responseStatus.getCode())));
    }

    public Object getHeader(String key) {
        return responseHeaders.get(key);
    }

    public void addHeader(String key, String value) {
        responseHeaders.put(key, value);
    }

    public void setContentType(String contentType) {
        responseHeaders.put(CONTENT_TYPE, contentType);
    }

    public void setContentType(MediaType contentType) {
        responseHeaders.put(CONTENT_TYPE, contentType.getMediaType());
    }

    public void sendRedirect(String uriPath) {
        responseHeaders.put(LOCATION, uriPath);
        setResponseStatus(ResponseStatus.FOUND);
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

    public void forward(String filePath) {
        responseBody = new ResponseBody(filePath);
        responseHeaders.put(CONTENT_LENGTH, responseBody.getBodyLength());
    }

    public void templateForward(String content) {
        responseBody = ResponseBody.of(content);
        responseHeaders.put(CONTENT_LENGTH, responseBody.getBodyLength());
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

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpVersion=" + httpVersion +
                ", responseStatus=" + responseStatus +
                ", responseHeaders=" + responseHeaders +
                ", responseBody=" + responseBody +
                '}';
    }
}
