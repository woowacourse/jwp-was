package http.request;

import exception.IllegalRequestException;

import java.io.BufferedReader;

public class RequestLine {
    private static final String DELIMITER = " ";

    private RequestMethod method;
    private String url;
    private String httpVersion;

    public RequestLine(BufferedReader br) throws Exception {
        String requestLine = br.readLine();
        validate(requestLine);
        String[] tokens = requestLine.split(DELIMITER);
        this.method = RequestMethod.valueOf(tokens[0]);
        this.url = tokens[1];
        this.httpVersion = tokens[2];
    }

    private void validate(String requestLine) throws IllegalRequestException {
        if (requestLine == null) {
            throw new IllegalRequestException();
        }
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return method + " " + url + " " + httpVersion;
    }
}
