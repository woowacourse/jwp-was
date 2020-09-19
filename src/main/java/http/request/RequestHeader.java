package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class RequestHeader {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);
    private static final String HEADER_DELIMITER = ": ";

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        logger.info(String.format("RequestHeader: {}", line));

        while (!StringUtils.isEmpty(line)) {
            String[] headerToken = line.split(HEADER_DELIMITER);
            headers.put(headerToken[0], headerToken[1]);
            line = bufferedReader.readLine();
            logger.info(String.format("RequestHeader: {}", line));
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }
}
