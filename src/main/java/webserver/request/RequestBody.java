package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import utils.IOUtils;

public class RequestBody {

    private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private final Object body;

    public RequestBody(BufferedReader bufferedReader, RequestHeader requestHeader, RequestLine requestLine) throws IOException {
        if(!requestLine.hasBody()) {
            this.body = null;
            return;
        }

        String contentLength = requestHeader.getHeader("Content-Length");
        String contentType = requestHeader.getHeader("Content-Type");
        validate(contentLength, requestLine);
        String readBody = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));

        if (DEFAULT_CONTENT_TYPE.equals(contentType)) {
            this.body = new RequestParameters(readBody);
            return;
        }
        this.body = readBody;
    }

    private void validate(String contentLength, RequestLine requestLine) {
        if (requestLine.hasBody() && Objects.isNull(contentLength)) {
            throw new IllegalArgumentException("Content-Length를 설정해주세요!");
        }
    }

    public String getBody() {
        return (String) body;
    }

    public String getParameter(String paramName) {
        return ((RequestParameters) body).getValue(paramName);
    }
}
