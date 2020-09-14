package webserver.dto;

public class Protocol {

    private final String protocol;

    public Protocol(String protocol) {
        this.protocol = protocol;
    }

    public static Protocol of(String protocol) {
        return new Protocol(protocol);
    }

    public String getProtocol() {
        return protocol;
    }
}
