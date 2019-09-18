package http;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLine {
    private final String requestLine;

    public RequestLine(BufferedReader bufferedReader) {
        try {
            requestLine = bufferedReader.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        if (requestLine == null) {
            throw new IllegalArgumentException();
        }
    }
}
