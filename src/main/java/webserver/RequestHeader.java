package webserver;

import exception.NotExistRequestHeader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHeader {

    private static final String HTTP_HEADER_DELIMITER = ": ";
    private static final String BLANK = "";

    private final RequestHeaderFirstLine requestHeaderFirstLine;
    private final Map<String, String> headers = new HashMap<>();
    private final RequestBody requestBody;

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        if (isNotAvailable(line)) {
            logger.info("not exist Request Header");
            throw new NotExistRequestHeader("not exist Request Header");
        }
        requestHeaderFirstLine = new RequestHeaderFirstLine(line);

        while (!BLANK.equals(line)) {
            line = bufferedReader.readLine();
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
        return requestHeaderFirstLine.isGet();
    }

    public boolean isPost() {
        return requestHeaderFirstLine.isPost();
    }

    public String getResourcePath() {
        return requestHeaderFirstLine.getResourcePath();
    }

    public String getBody() {
        return requestBody.getBody();
    }
}
