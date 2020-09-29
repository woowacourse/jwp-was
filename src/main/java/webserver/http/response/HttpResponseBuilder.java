package webserver.http.response;

import webserver.staticfile.StaticFileMatcher;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseBuilder {
    private HttpResponseLine httpResponseLine;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public void httpStatus(HttpStatus httpStatus) {
        httpResponseLine = new HttpResponseLine(httpStatus);
    }

    public void contentType(String filePath) {
        String contentType = String.format("text/%s;charset=utf-8", StaticFileMatcher.findStaticFileType(filePath).getFileType());
        headers.put("Content-Type", contentType);
    }

    public void contentLength(byte[] body) {
        headers.put("Content-Length", String.valueOf(body.length));
    }

    public void location(String url) {
        headers.put("Location", url);
    }

    public void body(byte[] body) {
        this.body = body;
    }

    public HttpResponse build() {
        return new HttpResponse(new HttpResponseHeader(httpResponseLine, headers), body);
    }
}
