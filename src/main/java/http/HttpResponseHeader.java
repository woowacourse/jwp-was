package http;

import java.util.Objects;

public class HttpResponseHeader {
    private static final int OK_STATUS_CODE = 200;

    private int statusCode;
    private String type;
    private String location;
    private String url;

    public HttpResponseHeader() {
    }

    public HttpResponseHeader(int statusCode, String type, String location, String url) {
        this.statusCode = statusCode;
        this.type = String.format("text/%s", type);
        this.location = location;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public boolean isOk() {
        return (statusCode == OK_STATUS_CODE);
    }

    public String getUrl() {
        return isOk() ? url : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setType(String type) {
        this.type = String.format("text/%s", type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponseHeader that = (HttpResponseHeader) o;
        return statusCode == that.statusCode &&
                Objects.equals(type, that.type) &&
                Objects.equals(location, that.location) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, type, location, url);
    }
}
