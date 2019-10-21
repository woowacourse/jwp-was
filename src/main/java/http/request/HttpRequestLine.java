package http.request;

import http.HttpVersion;
import http.exception.RequestLineException;

import java.io.UnsupportedEncodingException;

public class HttpRequestLine {
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;
    private static final int START_LINE_SIZE = 3;

    private HttpMethod method;
    private HttpUri path;
    private HttpVersion version;

    HttpRequestLine(HttpMethod method, HttpUri path, HttpVersion version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    static HttpRequestLine parse(String requestLine) throws UnsupportedEncodingException {
        String[] parsedRequestLine = requestLine.split(REQUEST_LINE_DELIMITER);
        checkRequestLine(parsedRequestLine);

        HttpMethod method = HttpMethod.resolve(parsedRequestLine[HTTP_METHOD_INDEX]);
        HttpUri url = new HttpUri(parsedRequestLine[PATH_INDEX]);
        HttpVersion version = HttpVersion.resolve(parsedRequestLine[HTTP_VERSION_INDEX]);
        return new HttpRequestLine(method, url, version);
    }

    private static void checkRequestLine(String[] parsedRequestLine) {
        if (parsedRequestLine.length != START_LINE_SIZE) {
            throw new RequestLineException();
        }
    }

    HttpMethod getHttpMethod() {
        return method;
    }

    HttpUri getPath() {
        return path;
    }

    HttpVersion getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "\n" + method + " " + path + " " + version;
    }
}
