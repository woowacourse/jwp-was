package webserver.message.request;

import org.slf4j.Logger;
import utils.IOUtils;
import webserver.message.HttpCookie;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class RequestHeader {
    private static final Logger LOG = getLogger(RequestHeader.class);

    private static final String HEADER_KEY_VALUE_DELIMITER = ":";
    private static final String EMPTY = "";
    private static final String CONTENT_LENGTH = "content-length";
    private static final String ZERO_LENGTH = "0";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> requestFields;
    private final List<HttpCookie> requestCookies;

    public RequestHeader(final IOUtils IOUtils) {
        this.requestFields = makeFields(IOUtils);
        this.requestCookies = RequestCookieParser.parse(requestFields.get("cookie"));
    }

    private Map<String, String> makeFields(final IOUtils IOUtils) {
        final Map<String, String> fields = new HashMap<>();
        for (String input : IOUtils) {
            fields.put(makeKey(input), makeValue(input));
        }
        return Collections.unmodifiableMap(fields);
    }

    private String makeKey(final String rawField) {
        return rawField.split(HEADER_KEY_VALUE_DELIMITER)[KEY_INDEX].trim().toLowerCase();
    }

    private String makeValue(final String rawField) {
        return rawField.split(HEADER_KEY_VALUE_DELIMITER)[VALUE_INDEX].trim();
    }

    public int getContentLength() {
        return Integer.parseInt(this.requestFields.getOrDefault(CONTENT_LENGTH, ZERO_LENGTH));
    }

    public Map<String, String> getRequestFields() {
        return requestFields;
    }

    public String getFieldsValue(final String key) {
        return requestFields.getOrDefault(key, EMPTY);
    }

    public List<HttpCookie> getRequestCookie() {
        return this.requestCookies;
    }

    public String getCookieValue(final String key) {
        return this.requestCookies.stream()
                .filter(cookie -> cookie.matchesName(key))
                .findFirst()
                .orElseGet(() -> new HttpCookie.Builder("", "").build())
                .getValue();
    }
}
