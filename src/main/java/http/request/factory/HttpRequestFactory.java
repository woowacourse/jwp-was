package http.request.factory;

import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;

import java.util.ArrayList;
import java.util.List;

public class HttpRequestFactory {
    public static HttpRequest create(List<String> lines) {
        int emptyLineIndex = lines.indexOf("");

        HttpRequestBody httpRequestBody = HttpRequestBodyFactory.create(new ArrayList<>());;
        if (emptyLineIndex == -1) {
            emptyLineIndex = lines.size();
        }

        HttpRequestLine httpRequestLine = HttpRequestLineFactory.create(lines.get(0));
        HttpRequestHeader httpRequestHeader = HttpRequestHeaderFactory.create(lines.subList(1, emptyLineIndex));

        if (emptyLineIndex != lines.size()) {
            httpRequestBody = HttpRequestBodyFactory.create(lines.subList(emptyLineIndex + 1, lines.size()));
        }

        return new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
    }
}
