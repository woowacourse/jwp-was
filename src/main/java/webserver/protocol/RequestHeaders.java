package webserver.protocol;

import java.util.Map;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RequestHeaders {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String TEXT_CSS = "text/css,*/*;q=0.1";
    private static final String ACCEPT = "Accept";

    private final Map<String, String> headers;

    public RequestHeaders(final Map<String, String> headers) {
        validate(headers);
        this.headers = headers;
    }

    private void validate(final Map<String, String> headers) {
        if (Objects.isNull(headers)) {
            throw new IllegalArgumentException("headers가 유효하지 않습니다: " + null);
        }
    }

    public boolean hasContentLength() {
        return headers.containsKey(CONTENT_LENGTH);
    }

    public boolean isAcceptCSS() {
        return Objects.equals(TEXT_CSS, headers.get(ACCEPT));
    }
}
