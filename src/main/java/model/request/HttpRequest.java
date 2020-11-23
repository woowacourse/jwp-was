package model.request;

import exception.NoSessionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import model.general.Cookie;
import model.general.Cookies;
import model.general.Header;
import model.general.Headers;
import model.general.Method;
import model.session.HttpSession;
import model.session.HttpSessions;
import utils.CookieUtils;
import utils.HeaderUtils;
import utils.IOUtils;

public class HttpRequest {

    private static final int VALUE_INDEX = 1;
    private static final String SESSION_COOKIE_KEY = "JSESSIONID";
    private static final String COOKIE_SEPARATOR = ";";
    private static final String COOKIE_KEY_VALUE_SEPARATOR = "=";
    private static final int FIRST_COOKIE_INDEX = 0;

    private final RequestLine requestLine;
    private final Headers headers;
    private final MessageBody messageBody;

    private HttpRequest(RequestLine line, Headers headers, MessageBody messageBody) {
        this.requestLine = line;
        this.headers = headers;
        this.messageBody = messageBody;
    }

    public static HttpRequest of(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        RequestLine requestLine = RequestLine.of(bufferedReader.readLine());
        Headers headers = new Headers(HeaderUtils.generateHeaders(bufferedReader));

        if (headers.hasKey(Header.CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(headers.getValue(Header.CONTENT_LENGTH));
            String requestBody = IOUtils.readData(bufferedReader, contentLength);
            MessageBody messageBody = MessageBody.of(requestBody);

            return new HttpRequest(requestLine, headers, messageBody);
        }
        return new HttpRequest(requestLine, headers, null);
    }

    public boolean isSameMethod(Method method) {
        return requestLine.isSameMethod(method);
    }

    public boolean isSameUri(String uri) {
        return requestLine.isSameUri(uri);
    }

    public boolean whetherUriHasExtension() {
        return requestLine.whetherUriHasExtension();
    }

    public Optional<String> extractRequestUriExtension() {
        return requestLine.extractRequestUriExtension();
    }

    public Map<String, String> extractParameters() {
        if (requestLine.isSameMethod(Method.GET)) {
            return requestLine.extractUriParameters();
        }
        if (requestLine.isSameMethod(Method.POST)) {
            Map<String, String> uriParameters = requestLine.extractUriParameters();
            Map<String, String> bodyParameters = messageBody.extractBodyParameters();
            Map<String, String> postParameters = new HashMap<>();

            postParameters.putAll(uriParameters);
            postParameters.putAll(bodyParameters);

            return postParameters;
        }

        return Collections.emptyMap();
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public String getRequestUri() {
        return requestLine.getRequestUri();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public Cookies getCookies() {
        return new Cookies(CookieUtils.generateCookies(headers));
    }

    public HttpSession getSession() throws NoSessionException {
        return HttpSessions.getHttpSession(getSessionId());
    }

    private String getSessionId() throws NoSessionException {
        if (hasSession()) {
            return headers.getValue(Header.COOKIE)
                .split(SESSION_COOKIE_KEY + COOKIE_KEY_VALUE_SEPARATOR)[VALUE_INDEX]
                .split(COOKIE_SEPARATOR)[FIRST_COOKIE_INDEX];
        }

        throw new NoSessionException("No Session");
    }

    public boolean hasSession() {
        String cookies = headers.getValue(Header.COOKIE);

        return Objects.nonNull(cookies) && cookies.contains(SESSION_COOKIE_KEY);
    }
}
