package servlet;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Cookie {

    private static final String COOKIE_DELIMITER = "=";

    private final String key;
    private final String value;

    public static Cookie parseCookie(String cookie) {
        String[] keyAndValue = cookie.split(COOKIE_DELIMITER);
        return new Cookie(keyAndValue[0], keyAndValue[1]);
    }

    public static Cookie of(String key, String value) {
        return new Cookie(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cookie cookie = (Cookie)o;
        return Objects.equals(getKey(), cookie.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }
}
