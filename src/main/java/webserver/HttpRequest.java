package webserver;

import exception.NotExistRequestHeader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

public class HttpRequest {

    private static final String HTTP_HEADER_DELIMITER = ": ";

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        validateRequestLine(line);
        requestLine = new RequestLine(line);
        requestHeader = new RequestHeader(new HashMap<>());

        while (!StringUtils.isBlank(line = bufferedReader.readLine())) {
            String[] lineSegment = line.split(HTTP_HEADER_DELIMITER);
            if (isNotAccessibleLine(lineSegment)) {
                continue;
            }
            requestHeader.putHeader(lineSegment[0], lineSegment[1]);
        }
        requestBody = new RequestBody(bufferedReader, requestHeader, requestLine);
    }

    private boolean isNotAccessibleLine(String[] lineSegment) {
        return lineSegment.length != 2;
    }

    private void validateRequestLine(String line) {
        if (isNotAvailable(line)) {
            logger.info("not exist Request Header");
            throw new NotExistRequestHeader("not exist Request Header");
        }
    }

    private boolean isNotAvailable(String line) {
        return Objects.isNull(line);
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public String getResourcePath() {
        return requestLine.getPath();
    }

    public String getBody() {
        return requestBody.getBody();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String headerName) {
        return requestHeader.getHeader(headerName);
    }

    public String getParameter(String paramName) {
        return requestLine.getParameter(paramName);
    }

    public String getBodyParameter(String paramName) {
        return requestBody.getParameter(paramName);
    }
}
