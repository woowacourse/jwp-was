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

    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";

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

        while (!"".equals(line)) {
            line = bufferedReader.readLine();
            String[] lineSegment = line.split(": ");

            if (lineSegment.length != 2) {
                break;
            }
            String headerKey = lineSegment[0];
            String headerValue = lineSegment[1];
            headers.put(headerKey, headerValue);
        }
        if (Objects.nonNull(headers.get("Content-Length"))) {
            body = IOUtils.readData(bufferedReader, Integer.parseInt(headers.get("Content-Length")));
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
