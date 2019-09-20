package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private static final String LAST_LINE = "";
    private static final String CONTENT_LENGTH = "Content-Length";

    private HttpRequestStartLine httpRequestStartLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpBody httpBody;

    private HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        initializeStart(br);

        initializeHeader(br);

        initializeBody(br);
    }

    public static HttpRequest of(InputStream in) throws IOException {
        return new HttpRequest(in);
    }

    private void initializeStart(BufferedReader br) throws IOException {
        String startLine = br.readLine();

        httpRequestStartLine = HttpRequestStartLine.of(startLine);
    }

    private void initializeHeader(BufferedReader br) throws IOException {
        List<String> httpRequestHeaderLines = new ArrayList<>();

        while (true) {
            String line = br.readLine();
            log.debug(line);

            if (line == null || line.equals(LAST_LINE)) {
                break;
            }
            httpRequestHeaderLines.add(line);
        }
        httpRequestHeader = HttpRequestHeader.of(httpRequestHeaderLines);
    }

    private void initializeBody(BufferedReader br) throws IOException {
        if(!httpRequestHeader.contains(CONTENT_LENGTH)) {
            log.debug("body가 없습니다.");
            return;
        }
        int contentLength = Integer.parseInt(httpRequestHeader.getHeader(CONTENT_LENGTH));
        httpBody = HttpBody.of(IOUtils.readData(br, contentLength));
    }

    public boolean hasParameters() {
        return httpRequestStartLine.hasParamaters();
    }

    public HttpMethod getHttpMethod() {
        return httpRequestStartLine.getHttpMethod();
    }

    public String getPath() {
        return httpRequestStartLine.getPath();
    }

    public String getHeader(String key) {
        return httpRequestHeader.getHeader(key);
    }

    public String getParameter(String key) {
        return httpRequestStartLine.getParameter(key);
    }

    public HttpBody getBody() {
        return httpBody;
    }
}
