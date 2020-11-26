package webserver.request;

import exception.NotExistRequestHeader;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.response.HttpVersion;

public class RequestLine {

    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);
    private static final String HTTP_HEADER_FIRST_LINE_DELIMITER = " ";

    private final HttpMethod method;
    private final RequestUri requestUri;
    private final HttpVersion version;

    public RequestLine(String line) {
        validate(line);
        String[] lineSegment = line.split(HTTP_HEADER_FIRST_LINE_DELIMITER);
        validate(lineSegment);
        this.method = HttpMethod.find(lineSegment[0]);
        this.requestUri = new RequestUri(lineSegment[1]);
        this.version = HttpVersion.find(lineSegment[2]);
    }

    private void validate(String line) {
        if (StringUtils.isBlank(line)) {
            log.error("not exist Request Header");
            throw new NotExistRequestHeader("not exist Request Header");
        }
    }

    private void validate(String[] lineSegment) {
        if (lineSegment.length != 3) {
            log.error("RequestLine array size is {}", lineSegment.length);
            throw new NegativeArraySizeException("Header RequestLine의 사이즈가 맞지 않습니다!");
        }
    }

    public String getMethod() {
        return method.getHttpMethod();
    }

    public String getPath() {
        return requestUri.getPath();
    }

    public String getParameter(String paramName) {
        return requestUri.getParameter(paramName);
    }

    public boolean hasBody() {
        return method.getBody();
    }

    public boolean isSupported() {
        return method.isSupport();
    }

    public boolean isNotImplemented() {
        return Objects.isNull(method);
    }
}
