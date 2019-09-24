package http.response;

public class StatusLine {
    private static final String OK_REASON_PHRASE = "OK";

    private String httpVersion;
    private int statusCode;
    private String reasonPhrase;

    public StatusLine(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public boolean isOk() {
        return OK_REASON_PHRASE.equals(reasonPhrase);
    }
}
