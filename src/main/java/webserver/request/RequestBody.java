package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

public class RequestBody {

    private static final Logger log = LoggerFactory.getLogger(RequestBody.class);
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
            log.error("ContentLength is Null!");
            throw new IllegalArgumentException("Content-Length를 다시 설정해주세요!");
        }
    }

    public String getBody() {
        validateBody();
        return (String) body;
    }

    public String getParameter(String paramName) {
        validateBody();
        return ((RequestParameters) body).getValue(paramName);
    }

    private void validateBody() {
        if (Objects.isNull(body)) {
            log.error("Request Body is Null!");
            throw new IllegalArgumentException("Request Body가 null입니다!");
        }
    }
}
