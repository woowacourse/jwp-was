package http.request;

import http.Cookie;
import http.Session;
import http.SessionManager;
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
import java.util.Optional;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private static final String LAST_LINE = "";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final SessionManager sessionManager;

    private HttpRequestStartLine httpRequestStartLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpBody httpBody;

    private Parameters parameters;

    private HttpRequest(InputStream in, SessionManager sessionManager) throws IOException {
        this.sessionManager = sessionManager;

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        initializeStart(br);
        initializeHeader(br);
        initializeBody(br);
    }

    public static HttpRequest of(InputStream in) throws IOException {
        return new HttpRequest(in, SessionManager.getInstance());
    }

    public static HttpRequest withSessionManager(InputStream in, SessionManager sessionManager) throws IOException {
        return new HttpRequest(in, sessionManager);
    }

    public Optional<Session> getSession(boolean canBeCreated) {
        if (!httpRequestHeader.contains("Cookie")) {
            return Optional.ofNullable(canBeCreated ? sessionManager.create() : null);
        }

        Cookie cookie = Cookie.fromCookie(httpRequestHeader.getHeader("Cookie"));
        String sessionID = cookie.getValue("JWP_WAS_SESSION_ID").orElse(SessionManager.EMPTY_ID);

        return sessionManager.getSession(sessionID, canBeCreated);
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
        if (httpRequestHeader.getHeader("Content-Type").equals("application/x-www-form-urlencoded")) {
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
