package webserver;

public class StatusLine {
    private final String version;
    private final ResponseStatus responseStatus;

    public StatusLine(String version, ResponseStatus responseStatus) {
        this.version = version;
        this.responseStatus = responseStatus;
    }

    public String serialize() {
        return String.format("%s %s", version, responseStatus.serialize());
    }
}
