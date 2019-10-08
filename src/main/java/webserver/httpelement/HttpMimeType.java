package webserver.httpelement;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class HttpMimeType {
    private enum Chemical {
        ALL,
        APPLICATION,
        AUDIO,
        EXAMPLE,
        FONT,
        IMAGE,
        MESSAGE,
        MODEL,
        MULTIPART,
        TEXT,
        VIDEO;
    }

    private static final Map<String, HttpMimeType> CACHE = new HashMap<>();
    public static final HttpMimeType ALL = of("*/*").get();
    public static final HttpMimeType APPLICATION_JAVASCRIPT = of("application/javascript").get();
    public static final HttpMimeType APPLICATION_JSON = of("application/json").get();
    public static final HttpMimeType APPLICATION_XHTML_XML = of("application/xhtml+xml").get();
    public static final HttpMimeType APPLICATION_XML = of("application/xml").get();
    public static final HttpMimeType APPLICATION_X_WWW_FORM_URLENCODED = of("application/x-www-form-urlencoded").get();
    public static final HttpMimeType IMAGE_ALL = of("image/*").get();
    public static final HttpMimeType IMAGE_BMP = of("image/bmp").get();
    public static final HttpMimeType IMAGE_GIF = of("image/gif").get();
    public static final HttpMimeType IMAGE_JPEG = of("image/jpeg").get();
    public static final HttpMimeType IMAGE_PNG = of("image/png").get();
    public static final HttpMimeType IMAGE_X_ICON = of("image/x-icon").get();
    public static final HttpMimeType MULTIPART_FORM_DATA = of("multipart/form-data").get();
    public static final HttpMimeType TEXT_CSS = of("text/css").get();
    public static final HttpMimeType TEXT_HTML = of("text/html").get();
    public static final HttpMimeType TEXT_PLAIN = of("text/plain").get();

    private final Chemical chemical;
    private final String subtype;

    public static Optional<HttpMimeType> of(String input) {
        final String noSpace = input.replaceAll("\\s+", "").toLowerCase();
        if (CACHE.containsKey(noSpace)) {
            return Optional.of(CACHE.get(noSpace));
        }
        final String[] args = noSpace.split("/");
        try {
            final HttpMimeType mimeType = (args[0].equals("*") && args[1].equals("*"))
                    ? new HttpMimeType(Chemical.ALL, "*")
                    : new HttpMimeType(Chemical.valueOf(args[0].toUpperCase()), args[1]);
            CACHE.put(noSpace, mimeType);
            return Optional.of(mimeType);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    private HttpMimeType(Chemical chemical, String subtype) {
        this.chemical = chemical;
        this.subtype = subtype;
    }

    public String subtype() {
        return this.subtype;
    }

    @Override
    public String toString() {
        return (this.chemical == Chemical.ALL ? "*" : this.chemical.toString().toLowerCase()) + "/" + this.subtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof HttpMimeType)) {
            return false;
        }
        final HttpMimeType rhs = (HttpMimeType) o;
        return this.chemical == rhs.chemical &&
                Objects.equals(this.subtype, rhs.subtype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.chemical, this.subtype);
    }
}
