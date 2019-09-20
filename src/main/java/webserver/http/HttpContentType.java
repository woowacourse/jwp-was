package webserver.http;

import utils.parser.KeyValueParserFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpContentType {
    private enum Chemical {
        APPLICATION,
        AUDIO,
        EXAMPLE,
        FONT,
        IMAGE,
        MESSAGE,
        MODEL,
        MULTIPART,
        TEXT,
        VIDEO
    }
    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_CSS = "text/css";
    private static final String APPLICATION_JSON = "application/json";
    private static final Map<String, HttpContentType> CACHE = new HashMap<String, HttpContentType>() {{
        put(TEXT_HTML, new HttpContentType(Chemical.TEXT, "html"));
        put(TEXT_CSS, new HttpContentType(Chemical.TEXT, "css"));
        put(APPLICATION_JSON, new HttpContentType(Chemical.APPLICATION, "json"));
    }};
    private static final String DEFAULT = TEXT_HTML;

    private final Chemical chemical;
    private final String subtype;
    private final Map<String, String> params;

    public static HttpContentType TEXT_HTML() {
        return CACHE.get(TEXT_HTML);
    }

    public static HttpContentType TEXT_CSS() {
        return CACHE.get(TEXT_CSS);
    }

    public static HttpContentType APPLICATION_JSON() {
        return CACHE.get(APPLICATION_JSON);
    }

    public static HttpContentType of(String input, KeyValueParserFactory keyValueParserFactory) {
        if (CACHE.containsKey(input)) {
            return CACHE.get(input);
        }
        if (input.contains("/")) {
            final HttpContentType contentType = new HttpContentType(input, keyValueParserFactory);
            return (contentType.chemical != null)
                    ? CACHE.computeIfAbsent(contentType.get(), x -> contentType)
                    : CACHE.get(DEFAULT);
        }
        return CACHE.get(DEFAULT);
    }

    private HttpContentType(String input, KeyValueParserFactory keyValueParserFactory) {
        final int slashIdx = input.indexOf("/");
        this.chemical = Chemical.valueOf(input.substring(0, slashIdx).toUpperCase());
        if (input.contains(";")) {
            this.subtype = input.substring(slashIdx + 1, input.indexOf(";"));
            this.params = Collections.unmodifiableMap(keyValueParserFactory.contentTypeParser().toMap(input));
        } else {
            this.subtype = input.substring(slashIdx + 1);
            this.params = null;
        }
    }

    private HttpContentType(Chemical chemical, String subtype) {
        this.chemical = chemical;
        this.subtype = subtype;
        this.params = null;
    }

    public String get() {
        return this.chemical.name().toLowerCase() + "/" + this.subtype;
    }
}