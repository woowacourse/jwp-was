package webserver.http.header.cookie;

import exception.InvalidCookieException;
import utils.StringUtils;

public class HttpCookieOption {
    private final HttpCookieOptionName name;
    private final String value;

    public HttpCookieOption(HttpCookieOptionName name, String value) {
        this.name = name;
        this.value = value;
    }

    public static HttpCookieOption of(HttpCookieOptionName name, String value) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidCookieException(value));

        String optionValue = value.trim();
        if (optionValue.isEmpty()) {
            throw new InvalidCookieException(value);
        }

        return new HttpCookieOption(name, optionValue);
    }

    public String toHttpMessage() {
        return this.name.getName() + "=" + this.value;
    }
}
