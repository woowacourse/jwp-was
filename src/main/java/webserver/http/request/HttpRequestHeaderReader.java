package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import webserver.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpRequestHeaderReader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String REQUEST_SEPARATOR = ": ";

    public static Header readRequest(BufferedReader bufferedReader) throws IOException {
        Map<String, String> requests = new LinkedHashMap<>();
        String requestLine = bufferedReader.readLine();

        while (!(doseNotExistsNextHeaderElement(requestLine))) {
            logger.info("request header - {}", requestLine);
            if (doseNotExistsNextHeaderElement(requestLine)) {
                break;
            }
            String[] request = requestLine.split(REQUEST_SEPARATOR);
            requests.put(request[0].trim(), request[1].trim());
            requestLine = bufferedReader.readLine();
        }

        return new Header(requests);
    }

    private static boolean doseNotExistsNextHeaderElement(String requestLine) {
        return requestLine.isEmpty() || Objects.isNull(requestLine);
    }
}
