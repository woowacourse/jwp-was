package webserver;

import exception.NotExistRequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

public class RequestLine {

    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);
    private static final String HTTP_HEADER_FIRST_LINE_DELIMITER = " ";

    private final HttpMethod method;
    private final RequestUri requestUri;
    private final String version;

    public RequestLine(String line) {
        validate(line);
        String[] lineSegment = line.split(HTTP_HEADER_FIRST_LINE_DELIMITER);
        validate(lineSegment);
        this.method = HttpMethod.find(lineSegment[0]);
        this.requestUri = new RequestUri(lineSegment[1]);
        this.version = lineSegment[2];
    }

    private void validate(String line) {
        if (StringUtils.isBlank(line)) {
            log.info("not exist Request Header");
            throw new NotExistRequestHeader("not exist Request Header");
        }
    }

    private void validate(String[] lineSegment) {
        if (lineSegment.length != 3) {
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
}
