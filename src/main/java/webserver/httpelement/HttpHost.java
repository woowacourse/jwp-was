package webserver.httpelement;

import webserver.Port;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class HttpHost implements HttpHeaderField {
    private static final Map<String, HttpHost> CACHE = new HashMap<>();

    private final String protocol;
    private final String name;
    private final Port port;

    public static Optional<HttpHost> of(String input) {
        final String trimmed = input.trim();
        if (CACHE.containsKey(trimmed)) {
            return Optional.of(CACHE.get(trimmed));
        }
        final String protocol = input.contains("://") ? trimmed.split("://")[0] : null;
        final String[] hostnameAndPort = (protocol != null)
                ? trimmed.substring(trimmed.indexOf("://") + 3).split(":")
                : trimmed.split(":");
        if (hostnameAndPort.length == 1) {
            if (hostnameAndPort[0].split("\\s+").length > 1) {
                return Optional.empty();
            }
            final HttpHost host = new HttpHost(protocol, hostnameAndPort[0], Port.HTTP);
            CACHE.put(hostnameAndPort[0], host);
            return Optional.of(host);
        }
        return Port.of(hostnameAndPort[1]).map(port -> {
            final HttpHost host = new HttpHost(protocol, hostnameAndPort[0], port);
            CACHE.put(trimmed, host);
            return host;
        });
    }

    private HttpHost(String protocol, String name, Port port) {
        this.protocol = protocol;
        this.name = name;
        this.port = port;
    }

    @Override
    public String toString() {
        return (this.protocol != null ? protocol + "://" : "") + this.name + ":" + this.port;
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