package domain.response;

import domain.common.Cookie;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class ResponseCookie {

    private static final String DELIMITER = "; ";

    private final Cookie cookie;
    private final List<String> options;

    public ResponseCookie(String key, String value, String... options) {
        this.cookie = new Cookie(key, value);
        this.options = Arrays.asList(options);
    }

    public String convertToString() {
        StringBuilder sb = new StringBuilder();
        String cookie = "Set-Cookie: " + getCookie().getKey() + "=" + getCookie().getValue();
        String options = String.join(DELIMITER, this.getOptions());
        sb.append(String.join(DELIMITER, cookie, options));
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseCookie that = (ResponseCookie) o;
        return Objects.equals(getCookie(), that.getCookie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCookie());
    }
}
