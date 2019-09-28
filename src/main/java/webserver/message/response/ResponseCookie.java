package webserver.message.response;

public class ResponseCookie {
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String OPTION_DELIMITER = "; ";

    private final String name;
    private final String value;
    private final String options;

    private ResponseCookie(final String name, final String value, final String options) {
        this.name = name;
        this.value = value;
        this.options = options;
    }

    public static class Builder {
        private final String name;
        private final String value;
        private final StringBuilder options = new StringBuilder();

        public Builder(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        public Builder expires(final String date) {
            this.options.append(bindKeyValue("Expires", date));
            return this;
        }

        public Builder maxAge(final String nonZeroDigit) {
            this.options.append(bindKeyValue("Max-Age", nonZeroDigit));
            return this;
        }

        public Builder domain(final String value) {
            this.options.append(bindKeyValue("Domain", value));
            return this;
        }

        public Builder path(final String path) {
            this.options.append(bindKeyValue("Path", path));
            return this;
        }

        public Builder secure(final String path) {
            this.options.append("Secure" + OPTION_DELIMITER);
            return this;
        }

        public Builder httpOnly(final String path) {
            this.options.append("HttpOnly" + OPTION_DELIMITER);
            return this;
        }

        public Builder sameSite(final String type) {
            this.options.append(bindKeyValue("Path", type));
            return this;
        }

        public ResponseCookie build() {
            return new ResponseCookie(this.name, this.value, this.options.toString());
        }
    }

    private static String bindKeyValue(final String key, final String value) {
        return key + KEY_VALUE_DELIMITER + value + OPTION_DELIMITER;
    }

    @Override
    public String toString() {
        return bindKeyValue(this.name, this.value) + this.options;
    }
}
