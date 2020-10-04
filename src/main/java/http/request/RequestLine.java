package http.request;

import exception.IllegalRequestException;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLine {
    private static final String DELIMITER = " ";

    private RequestMethod method;
    private RequestUri requestUri;
    private String httpVersion;

    public RequestLine(BufferedReader br) throws IllegalRequestException, IOException {
        String requestLine = br.readLine();
        validate(requestLine);
        String[] tokens = requestLine.split(DELIMITER);
        this.method = RequestMethod.valueOf(tokens[0]);
        this.requestUri = new RequestUri(tokens[1]);
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

    public RequestUri getRequestUri() {
        return requestUri;
    }

    public String getPath() {
        return requestUri.getPath();
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return method + " " + requestUri.toString() + " " + httpVersion;
    }
}
