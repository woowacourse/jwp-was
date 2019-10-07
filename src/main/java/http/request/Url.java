package http.request;

import java.util.Objects;

import static http.request.HttpRequestParser.QUERY_STRING_DELIMITER;

public class Url {
    private String url;
    private String rawUrl;
    private HttpRequestType httpRequestType;

    public Url(final String rawUrl) {
        this.rawUrl = rawUrl;
        this.url = rawUrl.split(QUERY_STRING_DELIMITER)[0];
        httpRequestType = HttpRequestType.of(url);
    }

    public String getUrl() {
        return url;
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public String getFullUrl() {
        return httpRequestType.getPrefix() + url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url1 = (Url) o;
        return Objects.equals(url, url1.url) &&
                Objects.equals(rawUrl, url1.rawUrl) &&
                httpRequestType == url1.httpRequestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, rawUrl, httpRequestType);
    }

    @Override
    public String toString() {
        return "Url{" +
                "url='" + url + '\'' +
                ", rawUrl='" + rawUrl + '\'' +
                ", httpRequestType=" + httpRequestType +
                '}';
    }
}
