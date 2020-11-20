package domain.common;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Cookie {

    private String key;
    private String value;

    public Cookie(String cookie) {
        String[] keyAndValue = cookie.split("=");
        this.key = keyAndValue[0];
        this.value = keyAndValue[1];
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
        return Objects.equals(getKey(), cookie.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }
}
