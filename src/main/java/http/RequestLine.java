package http;

import exception.IllegalRequestException;

import java.io.BufferedReader;

public class RequestLine {
    private RequestMethod method;

    private String url;

    private String protocol;

    public RequestLine(BufferedReader br) throws Exception {
        String requestLine = br.readLine();
        validate(requestLine);
        String[] tokens = requestLine.split(" ");
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
}
