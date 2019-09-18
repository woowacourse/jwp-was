package webserver.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader(String headerInput) {
        String[] splitedInput = headerInput.split("\n");
        headers.put("method", splitedInput[0].split(" ")[0]);
        headers.put("url", splitedInput[0].split(" ")[1]);

        for (int i = 1; i < splitedInput.length; i++) {
            String key = splitedInput[i].split(":")[0].trim().toLowerCase();
            String value = splitedInput[i].split(":")[1].trim();

            headers.put(key, value);
        }
    }

    public String get(String key) {
        return headers.get(key.toLowerCase());
    }

    public Map<String, String> extractQueryParameter() {
        Map<String, String> map = new HashMap<>();
        String url = headers.get("url");
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
