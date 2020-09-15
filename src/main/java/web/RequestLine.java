package web;

import java.io.BufferedReader;
import java.io.IOException;

import webserver.RequestPath;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " ";

    private final HttpMethod method;
    private final RequestPath path;
    private final String protocol;

    public RequestLine(BufferedReader bufferedReader) throws IOException {
        String url = bufferedReader.readLine();
        String[] splitUrl = url.split(REQUEST_LINE_DELIMITER);
        this.method = HttpMethod.valueOf(splitUrl[0]);
        this.path = new RequestPath(splitUrl[1]);
        this.protocol = splitUrl[2];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getParam(String key) {
        return path.getParameters(key);
    }

    public String getProtocol() {
        return protocol;
    }
}
