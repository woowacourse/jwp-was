package webserver.http.headerfields;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HttpContentType implements HttpHeaderField {
    private static final Map<String, HttpContentType> CACHE = new HashMap<>();
    public static final HttpContentType APPLICATION_JAVASCRIPT_UTF_8 = of("application/javascript;charset=utf-8").get();
    public static final HttpContentType APPLICATION_JSON_UTF_8 = of("application/json;charset=utf-8").get();
    public static final HttpContentType IMAGE_BMP = of("image/bmp").get();
    public static final HttpContentType IMAGE_GIF = of("image/gif").get();
    public static final HttpContentType IMAGE_JPEG = of("image/jpeg").get();
    public static final HttpContentType IMAGE_PNG = of("image/png").get();
    public static final HttpContentType IMAGE_X_ICON = of("image/x-icon").get();
    public static final HttpContentType TEXT_CSS_UTF_8 = of("text/css;charset=utf-8").get();
    public static final HttpContentType TEXT_HTML_UTF_8 = of("text/html;charset=utf-8").get();
    public static final HttpContentType TEXT_PLAIN_UTF_8 = of("text/plain;charset=utf-8").get();

    private final HttpMimeType mimeType;
    private final Charset charset;
    private final String boundary;

    public static Optional<HttpContentType> of(String input) {
        return Optional.ofNullable(input).flatMap(x -> {
            final String escaped = x.replaceAll("\\s+", "").toLowerCase();
            if (CACHE.containsKey(escaped)) {
                return Optional.of(CACHE.get(escaped));
            }
            return HttpMimeTypeAndParams.of(escaped).filter(y -> !y.mimeType().subtype().equals("*"))
                                                    .map(y -> {
                                                        try {
                                                            final HttpContentType contentType =
                                                                    new HttpContentType(y.mimeType(), y.params());
                                                            if (contentType.boundary == null) {
                                                                CACHE.put(escaped, contentType);
                                                            }
                                                            return contentType;
                                                        } catch (UnsupportedCharsetException e) {
                                                            return null;
                                                        }
                                                    });
        });
    }

    private HttpContentType(HttpMimeType mimeType, Map<String, String> params) {
        this.mimeType = mimeType;
        this.charset = params.containsKey("charset") ? Charset.forName(params.get("charset")) : null;
        this.boundary = params.get("boundary");
    }

    public HttpMimeType mimeType() {
        return this.mimeType;
    }

    @Override
    public String toString() {
        return this.mimeType
                + ((this.charset != null) ? "; charset=" + this.charset : "")
                + ((this.boundary != null) ? "; boundary=" + this.boundary : "");
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
        return Objects.equals(this.mimeType, rhs.mimeType) &&
                Objects.equals(this.charset, rhs.charset) &&
                Objects.equals(this.boundary, rhs.boundary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mimeType, this.charset, this.boundary);
    }
}