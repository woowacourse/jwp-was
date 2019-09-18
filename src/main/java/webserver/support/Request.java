package webserver.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private static final int INDEX_OF_URL = 1;
    private static final String SPACE_DELIMITER = " ";
    private static final String ROOT_URL = "/";
    private static final String INDEX_HTML = "/index.html";

    private final String requestHeader;

    public Request(String header) {
        requestHeader = header;
    }

    public String extractUrl() {
        String[] url = requestHeader.split(SPACE_DELIMITER);
        return validate(url[INDEX_OF_URL]);
    }

    private String validate(String url) {
        if (url.equals(ROOT_URL)) {
            return INDEX_HTML;
        }
        return url;
    }

    public Map<String, String> extractQueryParameter(String url) {
        Map<String, String> map = new HashMap<>();
        logger.debug("Extract Query Parameter in {}", url);

        if (url.contains("?")) {
            String query = url.split("\\?")[1];
            String[] queryParameters = query.split("&");
            for (String queryParameter : queryParameters) {
                String name = queryParameter.split("=")[0];
                String value = queryParameter.split("=")[1];
                map.put(name, value);
            }
        }

        return map;
    }
}
