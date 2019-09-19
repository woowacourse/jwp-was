package http.request;

import http.common.HttpHeader;
import http.common.URL;
import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String BLANK = "";
    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final RequestParameter requestParameter;
    private final String body;

    public HttpRequest(BufferedReader br) throws IOException {
        requestLine = extractRequestLine(br);
        httpHeader = extractHeader(br);
        body = extractBody(br);
        requestParameter = createRequestParameter();
    }

    private RequestLine extractRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        return new RequestLine(requestLine);
    }

    private HttpHeader extractHeader(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();

        String line = br.readLine();
        while (!StringUtils.isEmpty(line)) {
            lines.add(line);
            line = br.readLine();
        }

        return new HttpHeader(lines);
    }

    private String extractBody(BufferedReader br) throws IOException {
        RequestMethod requestMethod = requestLine.getMethod();
        if (requestMethod.hasBody()) {
            int contentLength = Integer.parseInt(httpHeader.get(CONTENT_LENGTH));
            return IOUtils.readData(br, contentLength);
        }
        return BLANK;
    }

    private RequestParameter createRequestParameter() {
        String queryString = requestLine.getUrl().getQueryString();
        if (requestLine.getMethod().hasBody() && "application/x-www-form-urlencoded".equals(httpHeader.get(CONTENT_TYPE))) {
            queryString += "&" + body;
        }
        return new RequestParameter(queryString);
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public String getBody() {
        return body;
    }

    public String getPath() {
        URL url = requestLine.getUrl();
        return url.getPath();
    }

    public RequestParameter getRequestParameter() {
        return requestParameter;
    }
}
