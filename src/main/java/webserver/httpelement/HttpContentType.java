package webserver.httpelement;

import utils.io.FileExtension;
import utils.parser.MimeTypeWithParamsParser;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class HttpContentType implements HttpHeaderField {
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
            final String noSpace = input.replaceAll("\\s+", "").toLowerCase();
            if (CACHE.containsKey(noSpace)) {
                return Optional.of(CACHE.get(noSpace));
            }
            return MimeTypeWithParamsParser.interpret(noSpace).filter(y -> !y.fst().subtype().equals("*"))
                                                                .map(y -> {
                                                                    try {
                                                                        final HttpContentType contentType =
                                                                                new HttpContentType(y.fst(), y.snd());
                                                                        if (contentType.boundary == null) {
                                                                            CACHE.put(noSpace, contentType);
                                                                        }
                                                                        return contentType;
                                                                    } catch (UnsupportedCharsetException e) {
                                                                        return null;
                                                                    }
                                                                });
    }

    public static HttpContentType fromFileExtension(FileExtension extension) {
        switch (extension.get()) {
            case "html":
            case "htm":
                return TEXT_HTML_UTF_8;
            case "css":
                return TEXT_CSS_UTF_8;
            case "js":
                return APPLICATION_JAVASCRIPT_UTF_8;
            case "bmp":
                return IMAGE_BMP;
            case "gif":
                return IMAGE_GIF;
            case "jpg":
                return IMAGE_JPEG;
            case "png":
                return IMAGE_PNG;
            case "ico":
                return IMAGE_X_ICON;
            case "txt":
            default:
                return TEXT_PLAIN_UTF_8;
        }
    }

    private HttpContentType(HttpMimeType mimeType, Map<String, String> params) {
        this.mimeType = mimeType;
        this.charset = params.containsKey("charset") ? Charset.forName(params.get("charset")) : null;
        this.boundary = params.get("boundary");
    }

    public HttpMimeType mimeType() {
        return this.mimeType;
    }

    public String boundary() {
        return this.boundary;
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