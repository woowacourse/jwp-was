package web.server.domain.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import web.server.utils.IOUtils;
import web.server.utils.StaticFileType;

public class HttpRequest {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String COLON = ": ";

    private final RequestLine requestLine;
    private final HeaderParams headerParams;
    private final RequestParams requestParams;
    private final HttpSessionStorage httpSessionStorage;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String header = IOUtils.readHeader(bufferedReader);
        String[] lines = header.split(NEW_LINE);
        this.requestLine = new RequestLine(lines[0]);
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
        this.httpSessionStorage = HttpSessionStorage.getInstance();
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

    public StaticFileType findExtension() {
        return requestLine.findExtension();
    }

    public int findContentLength() {
        String length = headerParams.getOrDefault("Content-Length", "0");
        return Integer.parseInt(length);
    }

    public HttpSession getSession() {
        String cookies = getHeader("Cookie");
        String[] split = cookies.split("; ");

        String key = Arrays.stream(split)
            .filter(value -> value.startsWith("JSESSIONID="))
            .map(value -> value.split("=")[1])
            .findFirst()
            .orElse("");

        if (key.isEmpty()) {
            return httpSessionStorage.createSession();
        }
        return httpSessionStorage.getSession(key);
    }
}
