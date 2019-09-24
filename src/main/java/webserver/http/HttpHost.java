package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HttpHost implements HttpHeaderField {
    private static final Map<String, HttpHost> CACHE = new HashMap<>();

    private final String name;
    private final HttpPort port;

    public static Optional<HttpHost> of(String input) {
        final String trimmed = input.trim();
        if (CACHE.containsKey(trimmed)) {
            return Optional.of(CACHE.get(trimmed));
        }
        final String[] hostnameAndPort = trimmed.contains("://")
                ? trimmed.substring(trimmed.indexOf("://") + 3).split("\\s*:\\s*")
                : trimmed.split("\\s*:\\s*");
        if (hostnameAndPort.length == 1) {
            if (hostnameAndPort[0].split("\\s+").length > 1) {
                return Optional.empty();
            }
            final HttpHost host = new HttpHost(trimmed, HttpPort.PORT_80);
            CACHE.put(trimmed, host);
            return Optional.of(host);
        }
        return HttpPort.of(hostnameAndPort[1]).map(port -> {
            final HttpHost host = new HttpHost(hostnameAndPort[0], port);
            CACHE.put(trimmed, host);
            return host;
        });
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
        return Objects.equals(this.name, rhs.name) &&
                Objects.equals(this.port, rhs.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.port);
    }
}