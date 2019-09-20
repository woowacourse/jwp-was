package webserver.http.headerfields;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HttpHost {
    private static final Map<String, HttpHost> CACHE = new HashMap<>();

    private final String name;
    private final HttpPort port;

    public Optional<HttpHost> of(String host) {
        final String key = host.trim();
        final String[] args = host.split(":");
        final String name = args[0].trim();
        if (name.isEmpty()) {
            return Optional.empty();
        }
        return (args.length == 1)
                ? Optional.of(CACHE.computeIfAbsent(key, k -> new HttpHost(name, new HttpPort())))
                : HttpPort.of(args[1].trim()).map(port -> CACHE.computeIfAbsent(key, k -> new HttpHost(name, port)));
    }

    private HttpHost(String name, HttpPort port) {
        this.name = name;
        this.port = port;
    }

    public String name() {
        return this.name;
    }

    public HttpPort port() {
        return this.port;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpHost)) {
            return false;
        }
        final HttpHost rhs = (HttpHost) o;
        return this.name.equals(rhs.name) &&
                Objects.equals(this.port, rhs.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.port);
    }
}