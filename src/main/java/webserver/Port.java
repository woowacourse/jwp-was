package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class Port {
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65_535;

    private static final Map<Integer, Port> CACHE = new HashMap<>();
    public static final Port HTTP = of(80).get();
    public static final Port HTTPS = of(443).get();
    public static final Port PORT_8080 = of(8080).get();

    private final int number;

    public static Optional<Port> of(int number) {
        if (CACHE.containsKey(number)) {
            return Optional.of(CACHE.get(number));
        }
        if (PORT_MIN <= number && number <= PORT_MAX) {
            final Port port = new Port(number);
            CACHE.put(number, port);
            return Optional.of(port);
        }
        return Optional.empty();
    }

    public static Optional<Port> of(String number) {
        try {
            return of(Integer.parseInt(number.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private Port(int number) {
        this.number = number;
    }

    public int number() {
        return this.number;
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
        if (!(o instanceof Port)) {
            return false;
        }
        final Port rhs = (Port) o;
        return this.number == rhs.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number);
    }
}
