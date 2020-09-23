package webserver;

import java.io.BufferedReader;
import java.io.IOException;

import utils.RequestUtils;

public class HttpRequest {
    public static final String EMPTY = "";
    public static final String SPACE_REGEX = " ";
    public static final int VALUE_INDEX = 1;

    private String header;
    private final String path;
    private final HttpMethod httpMethod;
    private int contentLength;

    public HttpRequest(BufferedReader br) throws IOException {
        String firstLine = readAndPrintRequest(br);
        this.httpMethod = HttpMethod.valueOf(RequestUtils.extractMethod(firstLine));
        this.path = RequestUtils.extractPath(firstLine);
    }

    private String readAndPrintRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        printRequest(br, line);

        return line;
    }

    private void printRequest(BufferedReader br, String line) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (!EMPTY.equals(line) && line != null) {
            sb.append(line).append(System.lineSeparator());
            assignContentLengthIfPresent(line);
            line = br.readLine();
        }
        this.header = sb.toString();
    }

    private void assignContentLengthIfPresent(String line) {
        if (line.startsWith("Content-Length")) {
            this.contentLength = Integer.parseInt(line.split(SPACE_REGEX)[VALUE_INDEX]);
        }
    }

    public String getHeader() {
        return header;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
