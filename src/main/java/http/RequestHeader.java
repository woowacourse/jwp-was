package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestHeader {
    private RequestMethod requestMethod;
    private RequestPath requestPath;
    private RequestVersion requestVersion;
    private List<String> requestLines;

    public RequestHeader(BufferedReader br) throws IOException {
        requestLines = getRequestLine(br);
        String[] tokens = getTokens(requestLines.get(0));
        this.requestMethod = RequestMethod.from(tokens[0]);
        this.requestPath = new RequestPath(tokens[1]);
        this.requestVersion = RequestVersion.from(tokens[2]);
    }

    private List<String> getRequestLine(BufferedReader br) throws IOException {
        List<String> requestLines = new ArrayList<>();

        String lines = br.readLine();
        while (!lines.equals("")) {
            requestLines.add(lines);
            lines = br.readLine();
        }

        return requestLines;
    }

    private String[] getTokens(String line) {
        return line.split(" ");
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }

    public RequestVersion getRequestVersion() {
        return requestVersion;
    }
}
