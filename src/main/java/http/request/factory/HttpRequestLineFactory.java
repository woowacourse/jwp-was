package http.request.factory;

import http.HttpMethod;
import http.HttpVersion;
import http.request.HttpRequestLine;

public class HttpRequestLineFactory {

    public static HttpRequestLine create(String line) {
        String[] requestLine = line.split(" ");
        HttpMethod httpMethod = HttpMethod.of(requestLine[0]);
        String uri = requestLine[1];
        HttpVersion httpVersion = HttpVersion.of(requestLine[2]);

        return new HttpRequestLine(httpMethod, uri, httpVersion);
    }
}
