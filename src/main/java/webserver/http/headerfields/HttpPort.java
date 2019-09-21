package webserver.http.headerfields;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HttpPort {
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65535;

    private static final Map<Integer, HttpPort> CACHE = new HashMap<Integer, HttpPort>();
    public static final HttpPort PORT_80 = of(80).get();
    public static final HttpPort PORT_8080 = of(8080).get();

    private final int number;

    public static Optional<HttpPort> of(int number) {
        if (CACHE.containsKey(number)) {
            return Optional.of(CACHE.get(number));
        }
        if (PORT_MIN <= number && number <= PORT_MAX) {
            final HttpPort port = new HttpPort(number);
            CACHE.put(number, port);
            return Optional.of(port);
        }
        return Optional.empty();
    }

    public static Optional<HttpPort> of(String number) {
        try {
            return of(Integer.parseInt(number.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private HttpPort(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.valueOf(this.number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpPort)) {
            return false;
        }
        final HttpPort rhs = (HttpPort) o;
        return this.number == rhs.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number);
    }
}
