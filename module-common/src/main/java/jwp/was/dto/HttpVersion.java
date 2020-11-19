package jwp.was.dto;

public class HttpVersion {

    private final String httpVersion;

    public HttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public static HttpVersion of(String httpVersion) {
        return new HttpVersion(httpVersion);
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
