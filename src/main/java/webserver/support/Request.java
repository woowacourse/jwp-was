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

    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public Request(RequestHeader requestHeader, RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public Request(RequestHeader header) {
        this(header, new RequestBody(""));
    }

    public String extractUrl() {
//        String[] url = requestHeader.split(SPACE_DELIMITER);
//        return validate(url[INDEX_OF_URL]);
        return validate(requestHeader.get("url"));
    }

    private String validate(String url) {
        if (url.equals(ROOT_URL)) {
            return INDEX_HTML;
        }
        return url;
    }

    public Map<String, String> extractQueryParameter() {
        return requestHeader.extractQueryParameter();
    }

    public Map<String, String> extractFormData() {
        return requestBody.getBody();
    }
}
