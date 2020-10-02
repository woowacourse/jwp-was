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

    private final String text;
    private final RequestParameters requestParameters;

    public RequestBody(BufferedReader bufferedReader, RequestHeader requestHeader, RequestLine requestLine) throws IOException {
        if(!requestLine.hasBody()) {
            this.text = null;
            this.requestParameters = null;
            return;
        }

        String contentLength = requestHeader.getHeader("Content-Length");
        String contentType = requestHeader.getHeader("Content-Type");
        validate(contentLength, requestLine);
        String readBody = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));

        if (DEFAULT_CONTENT_TYPE.equals(contentType)) {
            this.text = null;
            this.requestParameters = new RequestParameters(readBody);
            return;
        }
        this.text = readBody;
        this.requestParameters = null;
    }

    private void validate(String contentLength, RequestLine requestLine) {
        if (requestLine.hasBody() && Objects.isNull(contentLength)) {
            log.error("ContentLength is Null!");
            throw new IllegalArgumentException("Content-Length를 다시 설정해주세요!");
        }
    }

    public String getBody() {
        validateBody(text);
        return text;
    }

    public String getParameter(String paramName) {
        validateBody(requestParameters);
        return requestParameters.getValue(paramName);
    }

    private void validateBody(Object body) {
        if (Objects.isNull(body)) {
            log.error("Request Body is Null!");
            throw new IllegalArgumentException("Request Body가 null입니다!");
        }
    }
}
