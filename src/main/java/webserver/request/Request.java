package webserver.request;

import utils.IOUtils;
import webserver.EntityHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String PARAMETER_DELIMITER = "&";
    private static final String NAME_VALUE_DELIMITER = "=";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int NAME_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int REQUEST_LINE_LENGTH = 3;

    private final RequestLine requestLine;
    private final String body;

    public Request(BufferedReader bufferedReader) {
        if (bufferedReader == null) {
            throw new IllegalArgumentException("bufferedReader는 null일 수 없습니다.");
        }
        this.requestLine = createRequestLine(bufferedReader);
        this.body = extractBody(bufferedReader);
    }

    private RequestLine createRequestLine(BufferedReader bufferedReader) {
        String requestLine = readLine(bufferedReader);
        String[] tokens = requestLine.split(REQUEST_LINE_DELIMITER);
        if (tokens.length != REQUEST_LINE_LENGTH) {
            throw new RuntimeException("잘못된 RequetLine입니다." + requestLine);
        }
        HttpMethod httpMethod = HttpMethod.from(tokens[HTTP_METHOD_INDEX]);
        return new RequestLine(httpMethod, tokens[VALUE_INDEX]);
    }

    private String readLine(BufferedReader bufferedReader) {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("bufferedReader IOException In Request.readLine");
        }
    }

    private String extractBody(BufferedReader bufferedReader) {
        String line = readLine(bufferedReader);
        int contentLength = 0;
        while ((line != null) && (!line.isEmpty())) {
            if (EntityHeader.CONTENT_LENGTH.match(line)) {
                String[] tokens = line.split(REQUEST_LINE_DELIMITER);
                contentLength = Integer.parseInt(tokens[VALUE_INDEX]);
            }
            line = readLine(bufferedReader);
        }
        return IOUtils.readData(bufferedReader, contentLength);
    }

    public static Map<String, String> extractQueryString(String queryString) {
        return Arrays.stream(queryString.split(PARAMETER_DELIMITER))
                .map(token -> token.split(NAME_VALUE_DELIMITER))
                .collect(Collectors.toMap(
                        nameAndValue -> nameAndValue[NAME_INDEX],
                        nameAndValue -> nameAndValue[VALUE_INDEX],
                        (a, b) -> b));
    }

    public boolean isMatchHttpMethod(HttpMethod httpMethod) {
        return requestLine.isMatchHttpMethod(httpMethod);
    }

    public boolean containsPath(String path) {
        return requestLine.containsPath(path);
    }

    public String getResource() {
        return this.requestLine.getResource();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getQueryString() {
        return this.requestLine.extractQueryString();
    }

    public String getBody() {
        return body;
    }

}
