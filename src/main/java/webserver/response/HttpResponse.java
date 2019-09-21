package webserver.response;

import java.util.ArrayList;
import java.util.List;

public class HttpResponse {
    private ResponseStatus responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse(ResponseStatus responseStatus, ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.responseStatus = responseStatus;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public void buildGetHeader(String extension) {
        responseHeaders.put("Content-Type", MediaType.of(extension).getMediaType());
        responseHeaders.put("Content-Length", responseBody.getLength());
    }

    public void buildRedirectHeader(String redirectUri) {
        responseHeaders.put("Location", redirectUri);
    }

    public List<String> responseBuilder() {
        List<String> responseExport = new ArrayList<>();

        responseExport.add(String.format("HTTP/1.1 %d %s\r\n", responseStatus.getCode(), responseStatus.name()));

        for (String key : responseHeaders.keySet()) {
            responseExport.add(String.format("%s: %s\r\n", key, responseHeaders.get(key)));
        }
        responseExport.add("\r\n");

        if (responseBody != null) {
            responseExport.add(new String(responseBody.getBody()));
        }
        return responseExport;
    }

    public Object getHeader(String key) {
        return responseHeaders.get(key);
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
