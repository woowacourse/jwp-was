package webserver.http.headerfields;

import utils.parser.KeyValueParserFactory;

import java.util.*;

public class HttpContentType implements HttpHeaderField {
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
    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_CSS = "text/css";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String APPLICATION_JAVASCRIPT = "application/javascript";
    private static final String IMAGE_GIF = "image/gif";
    private static final String IMAGE_JPEG = "image/jpeg";
    private static final String IMAGE_PNG = "image/png";
    private static final String IMAGE_X_ICON = "image/x-icon";
    private static final Map<String, String> DEFAULT_PARAMS = new HashMap<String, String>() {{
        put("charset", "utf-8");
    }};

    private static final Map<String, HttpContentType> CACHE = new HashMap<String, HttpContentType>() {{
        put(APPLICATION_JSON, new HttpContentType(Chemical.APPLICATION, "json"));
        put(APPLICATION_X_WWW_FORM_URLENCODED, new HttpContentType(Chemical.APPLICATION, "x-www-form-urlencoded"));
        put(MULTIPART_FORM_DATA, new HttpContentType(Chemical.MULTIPART, "form-data"));
        put(TEXT_HTML, new HttpContentType(Chemical.TEXT, "html"));
        put(TEXT_CSS, new HttpContentType(Chemical.TEXT, "css"));
        put(TEXT_PLAIN, new HttpContentType(Chemical.TEXT, "plain"));
        put(APPLICATION_JAVASCRIPT, new HttpContentType(Chemical.APPLICATION, "javascript"));
        put(IMAGE_GIF, new HttpContentType(Chemical.IMAGE, "gif"));
        put(IMAGE_JPEG, new HttpContentType(Chemical.IMAGE, "jpeg"));
        put(IMAGE_PNG, new HttpContentType(Chemical.IMAGE, "png"));
        put(IMAGE_X_ICON, new HttpContentType(Chemical.IMAGE, "x-icon"));
    }};

    private final Chemical chemical;
    private final String subtype;
    private final Map<String, String> params;

    public static HttpContentType APPLICATION_JSON() {
        return CACHE.get(APPLICATION_JSON);
    }

    public static HttpContentType APPLICATION_X_WWW_FORM_URLENCODED() {
        return CACHE.get(APPLICATION_X_WWW_FORM_URLENCODED);
    }

    public static HttpContentType MULTIPART_FORM_DATA() {
        return CACHE.get(MULTIPART_FORM_DATA);
    }

    public static HttpContentType TEXT_HTML() {
        return CACHE.get(TEXT_HTML);
    }

    public static HttpContentType TEXT_CSS() {
        return CACHE.get(TEXT_CSS);
    }

    public static HttpContentType TEXT_PLAIN() {
        return CACHE.get(TEXT_PLAIN);
    }

    public static HttpContentType APPLICATION_JAVASCRIPT() {
        return CACHE.get(APPLICATION_JAVASCRIPT);
    }

    public static HttpContentType IMAGE_GIF() {
        return CACHE.get(IMAGE_GIF);
    }

    public static HttpContentType IMAGE_PNG() {
        return CACHE.get(IMAGE_PNG);
    }

    public static HttpContentType IMAGE_JPEG() {
        return CACHE.get(IMAGE_JPEG);
    }

    public static HttpContentType IMAGE_X_ICON() {
        return CACHE.get(IMAGE_X_ICON);
    }

    public static Optional<HttpContentType> of(String input) {
        input = input.replaceAll(";\\s+", ";").toLowerCase();
        if (CACHE.containsKey(input)) {
            return Optional.of(CACHE.get(input));
        }
        if (!input.contains("/")) {
            return Optional.empty();
        }
        try {
            final HttpContentType contentType = new HttpContentType(input);
            CACHE.put(input, contentType);
            return Optional.of(contentType);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private HttpContentType(String input) {
        final int slashIdx = input.indexOf("/");
        this.chemical = Chemical.valueOf(input.substring(0, slashIdx).toUpperCase());
        if (input.contains(";")) {
            this.subtype = input.substring(slashIdx + 1, input.indexOf(";"));
            final Map<String, String> params = KeyValueParserFactory.contentTypeParser().toMap(input);
            params.putAll(DEFAULT_PARAMS);
            this.params = Collections.unmodifiableMap(params);
        } else {
            this.subtype = input.substring(slashIdx + 1);
            this.params = DEFAULT_PARAMS;
        }
    }

    private HttpContentType(Chemical chemical, String subtype) {
        this.chemical = chemical;
        this.subtype = subtype;
        this.params = DEFAULT_PARAMS;
    }

    @Override
    public String nameOfField() {
        return "Content-Type";
    }

    @Override
    public String toString() {
        return this.chemical.name().toLowerCase()
                + "/"
                + this.subtype
                + this.params.entrySet().stream()
                                        .map(param -> param.getKey() + "=" + param.getValue())
                                        .reduce("", (a, b) -> a + ";" + b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpContentType)) {
            return false;
        }
        final HttpContentType rhs = (HttpContentType) o;
        return this.chemical == rhs.chemical &&
                this.subtype.equals(rhs.subtype) &&
                Objects.equals(this.params, rhs.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.chemical, this.subtype, this.params);
    }
}