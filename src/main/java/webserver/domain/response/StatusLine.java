package webserver.domain.response;

import webserver.domain.Protocol;

public class StatusLine {
    private final Protocol protocol;
    private final StatusCode statusCode;

    public StatusLine(Protocol protocol, StatusCode statusCode) {
        this.protocol = protocol;
        this.statusCode = statusCode;
    }

    public String getValue() {
        return String.format("%s %s", protocol.getValue(), statusCode.getValue());
    }
}
