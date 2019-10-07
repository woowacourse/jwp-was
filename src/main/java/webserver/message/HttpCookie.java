package webserver.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpCookie {
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String OPTION_DELIMITER = "; ";

    private final String name;
    private final String value;
    private final Map<String, String> options;

    private HttpCookie(final String name, final String value, final Map<String, String> options) {
        this.name = name;
        this.value = value;
        this.options = options;
    }

    // TODO Builder 굳이 안해도 될수도
    public static class Builder {
        private final String name;
        private final String value;
        private final Map<String, String> options = new HashMap<>();

        public Builder(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        public Builder expires(final String date) {
            this.options.put("Expires", date);
            return this;
        }

        public Builder maxAge(final String nonZeroDigit) {
            this.options.put("Max-Age", nonZeroDigit);
            return this;
        }

        public Builder domain(final String value) {
            this.options.put("Domain", value);
            return this;
        }

        public Builder path(final String path) {
            this.options.put("Path", path);
            return this;
        }

        public Builder secure() {
            this.options.put("Secure", "");
            return this;
        }

        public Builder httpOnly() {
            this.options.put("HttpOnly", "");
            return this;
        }

        public Builder sameSite(final String type) {
            this.options.put("Path", type);
            return this;
        }

        public HttpCookie build() {
            return new HttpCookie(this.name, this.value, this.options);
        }
    }

    public boolean matchesName(final String name) {
        return name.equals(this.name);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    private boolean hasValueOption(final Map.Entry entry) {
        return entry.getValue().equals("");
    }

    private static String bindKeyValue(final String key, final String value) {
        return key + KEY_VALUE_DELIMITER + value + OPTION_DELIMITER;
    }

    @Override
    public String toString() {
        return bindKeyValue(this.name, this.value)
                + this.options.entrySet().stream()
                .filter(entry -> !hasValueOption(entry))
                .map(entry -> bindKeyValue(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining())
                + this.options.entrySet().stream()
                .filter(this::hasValueOption)
                .map(entry -> entry.getKey() + OPTION_DELIMITER)
                .collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpCookie that = (HttpCookie) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value) &&
                Objects.equals(options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, options);
    }
}
