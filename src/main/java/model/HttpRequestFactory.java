package model;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestFactory {
    private static final String SP = " ";
    private static final int HTTP_METHOD = 0;
    private static final int REQUEST_URI = 1;
    private static final int HTTP_VERSION = 2;

    public HttpRequest create(BufferedReader br) throws IOException {
        RequestLine requestLine = createRequestLine(br.readLine());
        return new HttpRequest(new HttpHeader(requestLine));
    }

    private RequestLine createRequestLine(String requestLine) {
        String[] tokens = requestLine.split(SP);
        HttpMethod method = HttpMethod.valueOf(tokens[HTTP_METHOD]);
        RequestURI uri = new RequestURI(tokens[REQUEST_URI]);
        HttpVersion version = HttpVersion.of(tokens[HTTP_VERSION]);

        return new RequestLine(method, uri, version);
    }
}
