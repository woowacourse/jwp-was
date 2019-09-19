package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private RequestMethod requestMethod;
    private RequestPath requestPath;
    private RequestVersion requestVersion;
    private RequestHeader requestHeader;

    public HttpRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] tokens = getTokens(line);

        this.requestHeader = new RequestHeader(parsedBufferedReader(br, line));
        this.requestMethod = RequestMethod.from(tokens[0]);
        this.requestPath = new RequestPath(tokens[1]);
        this.requestVersion = RequestVersion.from(tokens[2]);
    }

    private List<String> parsedBufferedReader(BufferedReader br, String line) throws IOException {
        List<String> requestLines = new ArrayList<>();

        while (!line.equals("")) {
            line = br.readLine();
            requestLines.add(line);
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
