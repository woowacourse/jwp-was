package http.response;

public class HttpResponseStartLine {
    private StatusCode statusCode;
    private String version;

    public HttpResponseStartLine(StatusCode statusCode, String version) {
        this.statusCode = statusCode;
        this.version = version;
    }

    public String convertLineToString() {
        return version + " "
                + statusCode.getStatusValue() + " "
                + statusCode.getStatus();
    }
}
