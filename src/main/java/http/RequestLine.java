package http;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private final HttpMethod method;
    private final String uri;
    private final String httpVersion;

    public RequestLine(final BufferedReader bufferedReader) throws IOException {
        String rawLine = bufferedReader.readLine();
        String[] line = rawLine.split(REQUEST_LINE_DELIMITER);
        this.method = HttpMethod.valueOf(line[0]);
        this.uri = line[1];
        this.httpVersion = line[2];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", uri='" + uri + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }
}
