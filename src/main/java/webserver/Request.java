package webserver;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Request {
    private static final String NAME_AND_VALUE_DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int REQUEST_LINE_LENGTH = 3;

    private final BufferedReader bufferedReader;
    private final RequestLine requestLine;

    public Request(BufferedReader bufferedReader) {
        if (bufferedReader == null) {
            throw new IllegalArgumentException("bufferedReader는 null일 수 없습니다.");
        }
        this.bufferedReader = bufferedReader;
        this.requestLine = createRequestLine();
    }

    private RequestLine createRequestLine() {
        String requestLine = readLine();
        String[] tokens = requestLine.split(NAME_AND_VALUE_DELIMITER);
        if (tokens.length != REQUEST_LINE_LENGTH) {
            throw new RuntimeException("잘못된 RequetLine입니다." + requestLine);
        }
        HttpMethod httpMethod = HttpMethod.from(tokens[HTTP_METHOD_INDEX]);
        String resource = decode(tokens[VALUE_INDEX]);
        return new RequestLine(httpMethod, resource);
    }

    private String decode(String data) {
        try {
            return URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException" + data);
        }
    }

    private String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("bufferedReader IOException In Request.readLine");
        }
    }

    public String extractQueryString() {
        return this.requestLine.extractQueryString();
    }

    public String extractBody() {
        String line = readLine();
        int contentLength = 0;
        while ((line != null) && (!line.isEmpty())) {
            if (line.contains("Content-Length")) {
                String[] tokens = line.split(NAME_AND_VALUE_DELIMITER);
                contentLength = Integer.parseInt(tokens[VALUE_INDEX]);
            }
            line = readLine();
        }

        String body = IOUtils.readData(bufferedReader, contentLength);
        return decode(body);
    }

    public boolean isMatchRequestLine(RequestLine requestLine) {
        return this.requestLine.isMatch(requestLine);
    }

    public String getResource() {
        return this.requestLine.getResource();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }
}
