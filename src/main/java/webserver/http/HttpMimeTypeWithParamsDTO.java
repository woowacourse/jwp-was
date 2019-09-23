package webserver.http;

import utils.parser.KeyValueParserFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpMimeTypeWithParamsDTO {
    private static final Map<String, HttpMimeTypeWithParamsDTO> CACHE = new HashMap<>();

    private final HttpMimeType mimeType;
    private final Map<String, String> params;

    public static Optional<HttpMimeTypeWithParamsDTO> of(String input) {
        if (CACHE.containsKey(input)) {
            return Optional.of(CACHE.get(input));
        }
        return HttpMimeType.of(
                input.contains(";") ? input.substring(0, input.indexOf(";")) : input
        ).map(mimeType -> {
            final HttpMimeTypeWithParamsDTO mimeTypeAndParams =
                    new HttpMimeTypeWithParamsDTO(
                            mimeType,
                            KeyValueParserFactory.contentTypeParser().interpret(input)
                    );
            CACHE.put(input, mimeTypeAndParams);
            return mimeTypeAndParams;
        });
    }

    private HttpMimeTypeWithParamsDTO(HttpMimeType mimeType, Map<String, String> params) {
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