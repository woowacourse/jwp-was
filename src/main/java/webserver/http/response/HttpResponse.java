package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class HttpResponse {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";

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
        setHeader(CONTENT_TYPE, "text/html;charset=utf-8");
        setHeader(CONTENT_LENGTH, String.valueOf(responseBody.getBodyLength()));
    }

    public void sendRedirect(String address) {
        responseStatus.setHttpStatus(HttpStatus.FOUND);
        responseHeaders.setHeader(LOCATION, address);
    }

    public void sendError(HttpStatus httpStatus, String message) {
        if (!httpStatus.isErrorCode()) {
            throw new IllegalArgumentException("에러 코드만 입력할 수 있습니다.");
        }
        responseStatus.setHttpStatus(httpStatus);
        this.responseBody = new ResponseBody(message);
        setHeader(CONTENT_TYPE, TEXT_HTML_CHARSET_UTF_8);
        setHeader(CONTENT_LENGTH, String.valueOf(responseBody.getBodyLength()));
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
