package model;

import static jdk.internal.joptsimple.internal.Strings.*;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestFactory {
    private static final String SP = " ";
    private static final int HTTP_METHOD = 0;
    private static final int REQUEST_URI = 1;
    private static final int HTTP_VERSION = 2;

    public HttpRequest create(BufferedReader br) throws IOException {
        HttpHeader header = extractHeader(br);
        consumeBufferedReader(br);
        HttpBody body = extractBody(br);
        return new HttpRequest(header, body);
    }

    private HttpHeader extractHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        RequestLine requestLine = createRequestLine(line);

        return new HttpHeader(requestLine);
    }

    private RequestLine createRequestLine(String requestLine) {
        String[] tokens = requestLine.split(SP);
        HttpMethod method = HttpMethod.valueOf(tokens[HTTP_METHOD]);
        RequestURI uri = RequestURIType.of(tokens[REQUEST_URI])
                .getFactory()
                .create(tokens[REQUEST_URI]);
        HttpVersion version = HttpVersion.of(tokens[HTTP_VERSION]);
        return new RequestLine(method, uri, version);
    }

    private void consumeBufferedReader(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (isNotEmpty(line)) {
            line = br.readLine();
        }
    }

    private boolean isNotEmpty(String line) {
        return !EMPTY.equals(line);
    }

    private HttpBody extractBody(BufferedReader br) throws IOException {
        if (br.ready()) {
            return new HttpBody(br.readLine());
        }
        return HttpBody.EMPTY_BODY;
    }
}
