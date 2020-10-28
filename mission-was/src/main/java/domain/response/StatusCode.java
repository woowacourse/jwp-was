package domain.response;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public enum StatusCode {
    OK("OK", 200, Arrays.asList("Content-Type", "Content-Length")),
    FOUND("Found", 302, Arrays.asList("Location")),
    NOT_FOUND("Not Found", 404, Collections.emptyList()),
    METHOD_NOT_ALLOWED("Method Not Allowed", 405, Collections.emptyList());

    private final String reasonPhrase;
    private final int statusCode;
    private final List<String> headers;

    StatusCode(String reasonPhrase, int statusCode, List<String> headers) {
        this.reasonPhrase = reasonPhrase;
        this.statusCode = statusCode;
        this.headers = headers;
    }
}
