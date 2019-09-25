package webserver.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpResponse {
    private HttpVersion httpVersion;
    private ResponseStatus responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse() {
        this.httpVersion = HttpVersion.HTTP_1_1;
        this.responseStatus = ResponseStatus.OK;
        this.responseHeaders = new ResponseHeaders();
    }

    public HttpResponse(ResponseStatus responseStatus, ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.httpVersion = HttpVersion.HTTP_1_1;
        this.responseStatus = responseStatus;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse sendErrorResponse(ResponseStatus responseStatus) {
        return new HttpResponse(
                responseStatus,
                new ResponseHeaders(),
                new ResponseBody(String.format("error/%d.html", responseStatus.getCode())));
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

    public boolean hasNotContent() {
        return Objects.isNull(responseBody);
    }
}
