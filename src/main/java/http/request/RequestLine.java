package http.request;

import java.io.BufferedReader;

import exception.IllegalRequestException;

public class RequestLine {
    private static final String DELIMITER = " ";
    private RequestMethod method;

    private String url;

    private String protocol;

    public RequestLine(BufferedReader br) throws Exception {
        String requestLine = br.readLine();
        validate(requestLine);
        String[] tokens = requestLine.split(DELIMITER);
        this.method = RequestMethod.valueOf(tokens[0]);
        this.url = tokens[1];
        this.protocol = tokens[2];
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

    public String getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return method + " " + url + " " + protocol;
    }
}
