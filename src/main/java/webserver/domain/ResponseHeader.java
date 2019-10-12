package webserver.domain;

import java.nio.charset.StandardCharsets;
import java.util.Map;

class ResponseHeader {
    private static final String SPACE = " ";
    private static final String NEW_LINE = "\r\n";
    private static final String KEY_VALUE_DELIMITER = ": ";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final int DEFAULT_HEADER_CAPACITY = 256; // 처리성능 향상을 위해 StringBuilder에서 사용할 array 공간을 미리 확보

    private HttpVersion protocol;
    private HttpStatus httpStatus;
    private Map<String, String> responseFields;
    private Cookies cookies;

    ResponseHeader(final HttpVersion protocol,
                   final HttpStatus httpStatus,
                   final Map<String, String> responseFields,
                   final Cookies cookies) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.responseFields = responseFields;
        this.cookies = cookies;
    }

    byte[] getBytes(final int bodyContentLength) {
        setContentLength(bodyContentLength);
        final StringBuilder builder = makeFirstLine();
        addRestLine(builder);
        addSetCookies(builder);
        return builder
                .toString()
                .getBytes(StandardCharsets.ISO_8859_1); // 이 인코딩을 명시적으로 지정하면 Charset 변환과정 없이 array 복사만 한다.
    }

    private void setContentLength(final int contentLength) {
        this.responseFields.put(CONTENT_LENGTH, Integer.toString(contentLength));
    }

    private StringBuilder makeFirstLine() {
        return new StringBuilder(DEFAULT_HEADER_CAPACITY)
                .append(this.protocol.toString())
                .append(SPACE)
                .append(httpStatus.toString())
                .append(NEW_LINE);
    }

    private void addRestLine(final StringBuilder builder) {
        for (final String key: this.responseFields.keySet()) {
            builder.append(key)
                    .append(KEY_VALUE_DELIMITER)
                    .append(this.responseFields.get(key))
                    .append(NEW_LINE);
        }
    }

    private void addSetCookies(final StringBuilder builder) {
        for (final Cookie cookie : this.cookies.getCollection()) {
            builder.append(SET_COOKIE)
                    .append(KEY_VALUE_DELIMITER)
                    .append(cookie.toString())
                    .append(NEW_LINE);
        }
    }
}
