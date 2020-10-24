package web.response;

public class ResponseLine {
    private final ResponseStatus responseStatus;
    private final String version;

    public ResponseLine(ResponseStatus responseStatus, String version) {
        this.responseStatus = responseStatus;
        this.version = version;
    }

    @Override
    public String toString() {
        return version + " " + responseStatus;
    }
}
