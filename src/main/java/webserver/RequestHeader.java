package webserver;

import exception.NotExistRequestHeader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import utils.UrlUtils;

public class RequestHeader {

    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String HTTP_HEADER_DELIMITER = ": ";
    private static final String BLANK = "";

    private final String firstLine;
    private final Map<String, String> headers = new HashMap<>();

    private String body;

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        if (isNotAvailable(line)) {
            logger.info("not exist Request Header");
            throw new NotExistRequestHeader("not exist Request Header");
        }
        firstLine = line;

        while (!BLANK.equals(line)) {
            line = bufferedReader.readLine();
            String[] lineSegment = line.split(HTTP_HEADER_DELIMITER);

            if (lineSegment.length != 2) {
                break;
            }
            String headerKey = lineSegment[0];
            String headerValue = lineSegment[1];
            headers.put(headerKey, headerValue);
        }
        String contentLength = headers.get("Content-Length");

        if (Objects.nonNull(contentLength)) {
            body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
        }
    }

    private boolean isNotAvailable(String line) {
        return Objects.isNull(line);
    }

    public boolean isGet() {
        return HTTP_METHOD_GET.equals(UrlUtils.extractHttpMethod(firstLine));
    }

    public boolean isPost() {
        return HTTP_METHOD_POST.equals(UrlUtils.extractHttpMethod(firstLine));
    }

    public String getFirstLine() {
        return firstLine;
    }

    public String getBody() {
        return body;
    }
}
