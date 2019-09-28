package http.request;

import http.parameter.Parameters;
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

    private Parameters parameters;

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
        log.debug(startLine);

        httpRequestStartLine = HttpRequestStartLine.of(startLine);

        parameters = httpRequestStartLine.getParameters();
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
        // body 존재 여부 확인
        if (!httpRequestHeader.contains(CONTENT_LENGTH)) {
            log.debug("body가 없습니다.");
            return;
        }
        int contentLength = Integer.parseInt(httpRequestHeader.getHeader(CONTENT_LENGTH));
        httpBody = HttpBody.of(IOUtils.readData(br, contentLength));

        // body 가 application/x-www-form-urlencoded 일 경우
        // 이렇게 엔티티 타입이 다양해질텐데... 어떤식으로 구성해놓으면 확장에 유연할까?
        if (httpRequestHeader.getHeader("Content-Type").equals("application/x-www-form-urlencoded")){
            parameters = parameters.plus(Parameters.fromQueryString(httpBody.toString()));
        }
    }

    public boolean hasParameters() {
        return !parameters.isEmpty();
    }

    public boolean hasBody() {
        return !HttpBody.EMPTY_BODY.equals(httpBody);
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
        return parameters.getParameter(key);
    }

    public HttpBody getBody() {
        return httpBody;
    }
}
