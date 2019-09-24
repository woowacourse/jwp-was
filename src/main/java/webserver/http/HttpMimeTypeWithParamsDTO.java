package webserver.http;

import utils.parser.simple.KeyValueParserFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class HttpMimeTypeWithParamsDTO {
    private final HttpMimeType mimeType;
    private final Map<String, String> params;

    public static Optional<HttpMimeTypeWithParamsDTO> of(String input) {
        return HttpMimeType.of(
                input.contains(";") ? input.substring(0, input.indexOf(";")) : input
        ).map(mimeType ->
                new HttpMimeTypeWithParamsDTO(
                        mimeType,
                        KeyValueParserFactory.contentTypeParser().interpret(input)
                )
        );
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