package webserver.http.headerfields;

import utils.parser.KeyValueParserFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpMimeTypeAndParams implements HttpHeaderField {
    private static final Map<String, HttpMimeTypeAndParams> CACHE = new HashMap<>();

    private final HttpMimeType mimeType;
    private final Map<String, String> params;

    public static Optional<HttpMimeTypeAndParams> of(String input) {
        if (CACHE.containsKey(input)) {
            return Optional.of(CACHE.get(input));
        }
        return HttpMimeType.of(
                input.contains(";") ? input.substring(0, input.indexOf(";")) : input
        ).map(mimeType -> {
            final HttpMimeTypeAndParams mimeTypeAndParams =
                    new HttpMimeTypeAndParams(mimeType, KeyValueParserFactory.contentTypeParser().interpret(input));
            CACHE.put(input, mimeTypeAndParams);
            return mimeTypeAndParams;
        });
    }

    private HttpMimeTypeAndParams(HttpMimeType mimeType, Map<String, String> params) {
        this.mimeType = mimeType;
        this.params = Collections.unmodifiableMap(params);
    }

    public HttpMimeType mimeType() {
        return this.mimeType;
    }

    public Map<String, String> params() {
        return this.params;
    }
}