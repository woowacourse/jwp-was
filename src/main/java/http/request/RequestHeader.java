package http.request;

import com.google.common.collect.Maps;
import http.HTTP;
import http.HttpCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);
    private static final String HEADER_DELIMITER = ": ";
    private static final String EMPTY_STRING = "";

    private final Map<String, String> headerContents = Maps.newHashMap();
    private HttpCookie cookie;


    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        createHeader(bufferedReader);
        createCookie();
    }

    private void createCookie() {
        if (headerContents.containsKey(HTTP.COOKIE.getPhrase())) {
            this.cookie = new HttpCookie(headerContents.get(HTTP.COOKIE.getPhrase()));
        }
    }

    private void createHeader(BufferedReader bufferedReader) throws IOException {
        String line;
        while (true) {
            line = bufferedReader.readLine();

            if (line == null || line.equals(EMPTY_STRING)) {
                break;
            }

            String[] keyValue = line.split(HEADER_DELIMITER);
            headerContents.put(keyValue[0], keyValue[1]);
            logger.info("request header contents: {}", line);
        }
    }

    public boolean contains(HTTP http) {
        return headerContents.containsKey(http.getPhrase());
    }

    public String getHeaderContents(String key) {
        return headerContents.getOrDefault(key, EMPTY_STRING);
    }

    public String getSessionId() {
        if (cookie != null) {
            return cookie.getAttribute(HttpCookie.Option.SESSION_ID.getPhrase());
        }
        return EMPTY_STRING;
    }
}
