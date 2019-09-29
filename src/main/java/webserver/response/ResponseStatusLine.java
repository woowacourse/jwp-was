package webserver.response;

import webserver.HttpStatus;
import webserver.request.HttpRequest;

import java.util.Objects;

public class ResponseStatusLine {
    private static final String STATUS_LINE_SEPARATOR = " ";

    private final String httpVersion;
    private final String statusCode;
    private final String statusText;

    private ResponseStatusLine(String httpVersion, String statusCode, String statusText) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public static ResponseStatusLine of(HttpRequest httpRequest, HttpStatus httpStatus) {
        String version = httpRequest.getHttpVersion();
        return new ResponseStatusLine(version, httpStatus.getCode(), httpStatus.getMessage());
    }

    public String response() {
        return httpVersion + STATUS_LINE_SEPARATOR + statusCode + STATUS_LINE_SEPARATOR + statusText + "\r\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseStatusLine that = (ResponseStatusLine) o;
        return Objects.equals(httpVersion, that.httpVersion) &&
                Objects.equals(statusCode, that.statusCode) &&
                Objects.equals(statusText, that.statusText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpVersion, statusCode, statusText);
    }
}
