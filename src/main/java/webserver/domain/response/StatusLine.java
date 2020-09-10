package webserver.domain.response;

import java.util.Arrays;

public enum StatusLine {
    OK("200", "HTTP/1.1 200 OK"),
    ELSE("", "");

    private final String code;
    private final String value;

    StatusLine(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static StatusLine of(String code) {
        return Arrays.stream(values())
            .filter(statusLine -> statusLine.code.equals(code))
            .findAny()
            .orElse(ELSE);
    }

    public String getValue() {
        return value;
    }
}
