package http;

import java.util.Optional;

public class HttpHost {
    private final String name;
    private final HttpPort port;

    public Optional<HttpHost> of(String host) {
        final String[] args = host.split(":");
        final String name = args[0];
        return (args.length > 1)
                ? HttpPort.of(args[1]).map(x -> new HttpHost(name, port))
                : Optional.of(new HttpHost(name, new HttpPort()));
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
}