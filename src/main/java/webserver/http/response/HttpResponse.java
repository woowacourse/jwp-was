package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class HttpResponse {
    private ResponseStatusLine responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    private HttpResponse(ResponseStatusLine responseStatus) {
        this.responseStatus = responseStatus;
        this.responseHeaders = new ResponseHeaders(new HashMap<>());
    }

    public static HttpResponse ofVersion(String httpVersion) {
        return new HttpResponse(new ResponseStatusLine(httpVersion));
    }

    public void setHeader(String key, String value) {
        responseHeaders.setHeader(key, value);
    }

    public void writeBody(String body) {
        writeBody(new ResponseBody(body));
    }

    public void writeBody(byte[] body) {
        writeBody(new ResponseBody(body));
    }

    public void writeBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
        responseStatus.setHttpStatus(HttpStatus.OK);
        setHeader("Content-Type", "text/html;charset=utf-8");
        setHeader("Content-Length", String.valueOf(responseBody.getBodyLength()));
    }

    public void sendRedirect(String address) {
        responseStatus.setHttpStatus(HttpStatus.FOUND);
        responseHeaders.setHeader("Location", address);
    }

    public void sendError(HttpStatus httpStatus, String message) {
        responseStatus.setHttpStatus(httpStatus);
        responseHeaders.setHeader("Content-Type", "text/html;charset=utf-8");
        this.responseBody = new ResponseBody(message);
    }

    public void write(DataOutputStream dos) throws IOException {
        responseStatus.write(dos);
        responseHeaders.write(dos);
        if (Objects.nonNull(responseBody)) {
            responseBody.write(dos);
        }
        dos.flush();
    }
}
