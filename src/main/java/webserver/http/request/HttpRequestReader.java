package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import webserver.http.method.HttpMethod;

import static webserver.http.response.HttpResponseGenerator.HEADER_CONTENT_LENGTH;
import static webserver.http.response.HttpResponseGenerator.HTTP_VERSION;

public class HttpRequestReader {

    public static final String HTTP_METHOD = "HttpMethod";
    public static final String REQUEST_URI = "requestUri";

    public static HttpRequest readHttpRequest(BufferedReader bufferedReader) throws IOException {
        Map<String, String> request = new LinkedHashMap<>();
        String[] line = bufferedReader.readLine().split(" ");
        request.put(HTTP_METHOD, line[0]);
        request.put(REQUEST_URI, line[1]);
        request.put(HTTP_VERSION, line[2]);

        RequestLine requestLine = new RequestLine(request);

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(HttpRequestHeaderReader.readRequest(bufferedReader));

        if (HttpMethod.POST.isSameMethod(requestLine.getElementValue(HTTP_METHOD))) {
            HttpRequestBody httpRequestBody = new HttpRequestBody(HttpRequestBodyReader.readRequestBody(bufferedReader,
                    httpRequestHeader.getRequestElement(HEADER_CONTENT_LENGTH)));
            return new HttpRequest(requestLine, httpRequestHeader, httpRequestBody);
        }
        return new HttpRequest(requestLine, httpRequestHeader);
    }
}
