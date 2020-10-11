package web.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHeader {
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_LENGTH = "Content-Length";
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);
    private final Map<String, String> requestHeader;

    public RequestHeader(BufferedReader request) throws IOException {
        this.requestHeader = mappingHeaders(request);
    }

    private Map<String, String> mappingHeaders(BufferedReader request) throws IOException {
        Map<String, String> headers = new HashMap<>();

        String line = request.readLine();
        while (!Objects.isNull(line) && !line.isEmpty()) {
            logger.debug(line);
            String[] splitLine = line.split(":");
            String key = splitLine[0].trim();
            String value = splitLine[1].trim();

            headers.put(key, value);
            line = request.readLine();
        }
        return headers;
    }

    public String getHeadersByKey(String key) {
        return requestHeader.get(key);
    }

    public String getContentType() {
        String acceptInfo = requestHeader.get(ACCEPT);

        return acceptInfo.split(",")[0];
    }

    public int getContentLength() {
        String contentLength = requestHeader.get(CONTENT_LENGTH);
        return Integer.parseInt(contentLength);
    }
}
