package webserver.http.headerfields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.exception.NotFoundContentTypeSeparatorException;
import utils.parser.ContentTypeParser;
import webserver.http.exception.NonexistentContentTypeException;

import java.util.*;

public class HttpContentType implements HttpHeaderField {
    private static final Logger logger = LoggerFactory.getLogger(HttpContentType.class);

    private static final Map<String, String> DEFAULT_PARAMS = new HashMap<String, String>() {{
        put("charset", "utf-8");
    }};

    private static final Map<String, HttpContentType> CACHE = new HashMap<String, HttpContentType>() {{
        put(MimeType.APPLICATION_JSON.getName(), new HttpContentType(Chemical.APPLICATION, "json"));
        put(MimeType.APPLICATION_X_WWW_FORM_URLENCODED.getName(), new HttpContentType(Chemical.APPLICATION, "x-www-form-urlencoded"));
        put(MimeType.MULTIPART_FORM_DATA.getName(), new HttpContentType(Chemical.MULTIPART, "form-data"));
        put(MimeType.TEXT_HTML.getName(), new HttpContentType(Chemical.TEXT, "html"));
        put(MimeType.TEXT_CSS.getName(), new HttpContentType(Chemical.TEXT, "css"));
        put(MimeType.TEXT_PLAIN.getName(), new HttpContentType(Chemical.TEXT, "plain"));
        put(MimeType.APPLICATION_JAVASCRIPT.getName(), new HttpContentType(Chemical.APPLICATION, "javascript"));
        put(MimeType.IMAGE_GIF.getName(), new HttpContentType(Chemical.IMAGE, "gif"));
        put(MimeType.IMAGE_JPEG.getName(), new HttpContentType(Chemical.IMAGE, "jpeg"));
        put(MimeType.IMAGE_PNG.getName(), new HttpContentType(Chemical.IMAGE, "png"));
        put(MimeType.IMAGE_X_ICON.getName(), new HttpContentType(Chemical.IMAGE, "x-icon"));
    }};

    private static final int CONTENT_TYPE_INDEX = 0;
    private static final int CONTENT_TYPE_PARAMS_INDEX = 1;

    private final Chemical chemical;
    private final String subtype;
    private final Map<String, String> params;

    public static Optional<HttpContentType> of(String input) {
        if (CACHE.containsKey(input)) {
            return Optional.of(CACHE.get(input));
        }

        try {
            CACHE.put(input, HttpContentType.of(ContentTypeParser.convertContentType(input)));
            return Optional.of(CACHE.get(input));
        } catch (NotFoundContentTypeSeparatorException | IllegalArgumentException e) {
            logger.debug(e.getMessage());
            return Optional.empty();
        }
    }

    private static HttpContentType of(List<String> input) {
        final String contentTypeLine = input.get(CONTENT_TYPE_INDEX);
        final Chemical chemical = ContentTypeParser.chemicalParse(contentTypeLine);
        final String subtype = ContentTypeParser.subtypeParse(contentTypeLine);
        Map<String, String> params = DEFAULT_PARAMS;

        if (input.size() > CONTENT_TYPE_PARAMS_INDEX) {
            params.putAll(ContentTypeParser.toMap(input.get(CONTENT_TYPE_PARAMS_INDEX)));
        }

        return new HttpContentType(chemical, subtype, params);
    }

    private HttpContentType(Chemical chemical, String subtype, Map<String, String> params) {
        this.chemical = chemical;
        this.subtype = subtype;
        this.params = params;
    }

    private HttpContentType(Chemical chemical, String subtype) {
        this.chemical = chemical;
        this.subtype = subtype;
        this.params = DEFAULT_PARAMS;
    }

    public static HttpContentType extensionToContentType(String extension) {
        extension = extension.toLowerCase();

        return Extension.extensionToContentType(extension).orElse(getHttpContentType(MimeType.TEXT_PLAIN));
    }

    public static HttpContentType getHttpContentType(String key) {
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }
        throw new NonexistentContentTypeException();
    }

    public static HttpContentType getHttpContentType(MimeType key) {
        String mimeTypeName = key.getName();
        return getHttpContentType(mimeTypeName);
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