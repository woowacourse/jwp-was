package webserver.httpelement;

import utils.parser.KeyValueParserFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class HttpCookie implements HttpHeaderField {
    private final Map<String, String> cookie;

    public static Optional<HttpCookie> of(String input) {
        final Map<String, String> cookie = KeyValueParserFactory.httpHeaderFieldAttrParser().interpret(input);
        return cookie.isEmpty() ? Optional.empty() : Optional.of(new HttpCookie(cookie));
    }

    private HttpCookie(Map<String, String> cookie) {
        this.cookie = Collections.unmodifiableMap(cookie);
    }

    public String get(String key) {
        return this.cookie.get(key);
    }

    @Override
    public String toString() {
        return this.cookie.entrySet().stream()
                                    .map(x -> x.getKey() + "=" + x.getValue())
                                    .reduce((a, b) -> a + "; " + b)
                                    .orElse("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpCookie)) {
            return false;
        }
        final HttpCookie rhs = (HttpCookie) o;
        return Objects.equals(this.cookie, rhs.cookie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cookie);
    }
}