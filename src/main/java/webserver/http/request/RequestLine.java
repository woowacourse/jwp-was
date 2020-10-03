package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import webserver.http.Protocol;

public class RequestLine {
    private static final String SPACE = "\\s";

    private final Method method;
    private final Uri uri;
    private final Protocol protocol;

    private RequestLine(Method method, Uri uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public static RequestLine of(BufferedReader bufferedReader) throws IOException {
        if (!bufferedReader.ready()) {
            throw new NotReadableHttpMessageException("HTTP 요청메시지의 요청라인이 비어있습니다.");
        }
        String source = bufferedReader.readLine();

        String[] sources = source.split(SPACE);
        Method method = Method.of(sources[0]);
        Uri uri = Uri.of(sources[1]);
        Protocol protocol = Protocol.of(sources[2]);

        return new RequestLine(method, uri, protocol);
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return uri.getPath();
    }

    public Map<String, String> getParameters() {
        return uri.getParameters();
    }
}
