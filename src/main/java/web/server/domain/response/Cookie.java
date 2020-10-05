package web.server.domain.response;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Cookie {

    private static final String DELIMITER = "; ";

    private final String key;
    private final String value;
    private final List<String> options;

    public Cookie(String key, String value, String... options) {
        this.key = key;
        this.value = value;
        this.options = Arrays.asList(options);
    }

    public String convertToString() {
        StringBuilder sb = new StringBuilder();
        String cookie = "Set-Cookie: " + this.getKey() + "=" + this.getValue();
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
        Cookie cookie = (Cookie) o;
        return Objects.equals(key, cookie.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
