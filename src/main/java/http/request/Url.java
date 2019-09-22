package http.request;

import java.util.Objects;

public class Url {
    private String url;
    private HttpRequestType httpRequestType;

    public Url(final String url) {
        this.url = url;
        httpRequestType = HttpRequestType.of(url);
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
                httpRequestType == url1.httpRequestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, httpRequestType);
    }

    @Override
    public String toString() {
        return "Url{" +
                "url='" + url + '\'' +
                ", httpRequestType=" + httpRequestType +
                '}';
    }
}
