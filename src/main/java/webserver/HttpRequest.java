package webserver;

import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private static final String CONTENT_LENGTH = "Content-length";
    private static final String BLANK = "";
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final String body;

    public HttpRequest(BufferedReader br) throws IOException {
        requestLine = extractRequestLine(br);
        requestHeader = extractHeader(br);
        body = getBody(br);
    }

    private RequestLine extractRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        return new RequestLine(requestLine);
    }

    private String getBody(BufferedReader br) throws IOException {
        RequestMethod requestMethod = requestLine.getMethod();
        if (requestMethod.hasBody()) {
            return extractBody(br, requestHeader.get(CONTENT_LENGTH));
        }
        return BLANK;
    }

    private String extractBody(BufferedReader br, String size) throws IOException {
        return IOUtils.readData(br, Integer.parseInt(size));
    }

    private RequestHeader extractHeader(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();

        String line = br.readLine();
        while (!StringUtils.isEmpty(line)) {
            lines.add(line);
            line = br.readLine();
        }

        return new RequestHeader(lines);
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public String getBody() {
        return body;
    }

    public String getPath() {
        URL url = requestLine.getUrl();
        return url.getPath();
    }

    public RequestParameter getRequestParameter() {
        return requestLine.getUrl().getRequestParameter();
    }
}
