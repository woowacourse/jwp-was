package web.server.domain.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import web.server.utils.IOUtils;

public class HttpRequest {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String COLON = ": ";

    private final RequestLine requestLine;
    private final HeaderParams headerParams;
    private final RequestParams requestParams;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String header = IOUtils.readHeader(bufferedReader);
        String[] lines = header.split(NEW_LINE);
        String requestLine = lines[0];
        this.requestLine = new RequestLine(requestLine);
        this.headerParams = new HeaderParams();
        for (int i = 1; i < lines.length; i++) {
            String[] keyValue = lines[i].split(COLON);
            headerParams.put(keyValue[0], keyValue[1]);
        }

        String params = this.requestLine.getQuery();
        if (this.requestLine.isPostMethod()) {
            params = IOUtils.readBody(bufferedReader, findContentLength());
        }
        this.requestParams = new RequestParams(params);
    }

    public RequestMethod getMethod() {
        return requestLine.getRequestMethod();
    }

    public String getHeader(String headerName) {
        return headerParams.get(headerName);
    }

    public String getParameter(String key) {
        return requestParams.get(key);
    }

    public boolean hasPathOfStaticFile() {
        return this.requestLine.hasPathOfStaticFile();
    }

    public RequestMethod getRequestMethod() {
        return requestLine.getRequestMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public int findContentLength() {
        String length = headerParams.getOrDefault("Content-Length", "0");
        return Integer.parseInt(length);
    }
}
