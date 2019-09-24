package webserver.response;

import java.util.ArrayList;
import java.util.List;

public class HttpResponse {
    private ResponseStatus responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse() {
        this.responseStatus = ResponseStatus.OK;
        this.responseHeaders = new ResponseHeaders();
    }

    public HttpResponse(ResponseStatus responseStatus, ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.responseStatus = responseStatus;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse sendErrorResponse(ResponseStatus responseStatus) {
        return new HttpResponse(
                responseStatus, new ResponseHeaders(), new ResponseBody(String.format("error/%d.html", responseStatus.getCode())));
    }

    public void forward(String filePath) {
        responseBody = new ResponseBody(filePath);
        responseHeaders.put("Content-Length", responseBody.getLength());
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

        responseExport.add(String.format("HTTP/1.1 %d %s\r\n", responseStatus.getCode(), responseStatus.name()));
        responseHeaders.keySet().forEach(key ->
                responseExport.add(String.format("%s: %s\r\n", key, responseHeaders.get(key))));
        responseExport.add("\r\n");

        if (responseBody != null) {
            responseExport.add(new String(responseBody.getBody()));
        }
        return responseExport;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    private void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
