package http;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private final HttpMethod method;
    private final Uri uri;
    private final String httpVersion;

    public RequestLine(final BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        String[] splitLine = line.split(REQUEST_LINE_DELIMITER);
        this.method = HttpMethod.valueOf(splitLine[0]);
        this.uri = new Uri(splitLine[1]);
        this.httpVersion = splitLine[2];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Uri getUri() {
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
