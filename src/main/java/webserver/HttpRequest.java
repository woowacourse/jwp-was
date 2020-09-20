package webserver;

import exception.NotExistRequestHeader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

public class HttpRequest {

    private static final String HTTP_HEADER_DELIMITER = ": ";
    private static final String BLANK = "";

    private final RequestLine requestLine;
    private final Map<String, String> headers = new HashMap<>();
    private final RequestBody requestBody;

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        if (isNotAvailable(line)) {
            logger.info("not exist Request Header");
            throw new NotExistRequestHeader("not exist Request Header");
        }
        requestLine = new RequestLine(line);

        while (!StringUtils.isBlank(line = bufferedReader.readLine())) {
            String[] lineSegment = line.split(HTTP_HEADER_DELIMITER);

            if (lineSegment.length != 2) {
                continue;
            }
            String headerKey = lineSegment[0];
            String headerValue = lineSegment[1];
            headers.put(headerKey, headerValue);
        }
        requestBody = new RequestBody(bufferedReader, headers.get("Content-Length"));
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
        return headers.get(headerName);
    }

    public String getParameter(String paramName) {
        return requestLine.getParameter(paramName);
    }
}
