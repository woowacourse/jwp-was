package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpPort {
    private static final int DEFAULT_PORT = 80;
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65535;

    private static final Map<Integer, HttpPort> CACHE = new HashMap<Integer, HttpPort>() {{
        put(80, new HttpPort(80));
        put(8080, new HttpPort(8080));
    }};

    private final int number;

    public static Optional<HttpPort> of(int number) {
        if (PORT_MIN <= number && number <= PORT_MAX) {
            return Optional.of(CACHE.computeIfAbsent(number, HttpPort::new));
        }
        return Optional.empty();
    }

    public static Optional<HttpPort> of(String number) {
        try {
            return of(Integer.parseInt(number));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private HttpPort(int number) {
        this.number = number;
    }

    public HttpPort() {
        this.number = DEFAULT_PORT;
    }

    public int number() {
        return this.number;
    }
}
