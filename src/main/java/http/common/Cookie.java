package http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cookie {
    private static final String COOKIE_DELIMITER = "=";
    private String name;
    private String value;
    private Map<String, String> optionValue;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        optionValue = new HashMap<>();
    }

    public Cookie(String line) {
        this(line.split(COOKIE_DELIMITER)[0], line.split(COOKIE_DELIMITER)[1]);
    }

    public void addOption(String optionName, String value) {
        optionValue.put(optionName, value);
    }

    public String getFieldString() {
        StringBuilder cookie = new StringBuilder(name + "=" + value);
        for (String option : optionValue.keySet()) {
            cookie.append("; ").append(option).append("=").append(optionValue.get(option));
        }
        return cookie.toString();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) &&
                Objects.equals(value, cookie.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
