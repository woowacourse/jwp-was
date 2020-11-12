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
import model.general.Header;
import model.general.Method;
import utils.IOUtils;

public class HttpRequest {

    private static final String EMPTY_LINE = "";
    private static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String SESSION_COOKIE_KEY = "JSESSIONID";
    private static final String COOKIE_SEPARATOR = ";";
    private static final String COOKIE_KEY_VALUE_SEPARATOR = "=";
    private static final int FIRST_COOKIE_INDEX = 0;

    private final RequestLine requestLine;
    private final Map<Header, String> headers;
    private final MessageBody messageBody;

    private HttpRequest(RequestLine line, Map<Header, String> headers, MessageBody messageBody) {
        this.requestLine = line;
        this.headers = headers;
        this.messageBody = messageBody;
    }

    public static HttpRequest of(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        RequestLine requestLine = RequestLine.of(bufferedReader.readLine());
        Map<Header, String> headers = addHeaders(bufferedReader);

        if (headers.containsKey(Header.CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(headers.get(Header.CONTENT_LENGTH));
            String requestBody = IOUtils.readData(bufferedReader, contentLength);
            MessageBody messageBody = MessageBody.of(requestBody);

            return new HttpRequest(requestLine, headers, messageBody);
        }
        return new HttpRequest(requestLine, headers, null);
    }

    private static Map<Header, String> addHeaders(BufferedReader bufferedReader)
        throws IOException {
        Map<Header, String> headers = new HashMap<>();

        String line = bufferedReader.readLine();
        while (Objects.nonNull(line) && !EMPTY_LINE.equals(line)) {
            Header key = Header.of(line.split(HEADER_KEY_VALUE_SEPARATOR)[KEY_INDEX]);
            String value = line.split(HEADER_KEY_VALUE_SEPARATOR)[VALUE_INDEX];
            headers.put(key, value);
            line = bufferedReader.readLine();
        }
        return headers;
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
            Map<String, String> uriParameters = requestLine.extractUriParameters();

            return uriParameters;
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

    public Optional<String> getCookie(String key) {
        String cookies = headers.get(Header.COOKIE);
        if (Objects.nonNull(cookies) && cookies.contains(key)) {
            return Optional.of(cookies
                .split(key + COOKIE_KEY_VALUE_SEPARATOR)[VALUE_INDEX]
                .split(COOKIE_SEPARATOR)[FIRST_COOKIE_INDEX]);
        }

        return Optional.empty();
    }

    public String getSessionId() throws NoSessionException {
        String cookies = headers.get(Header.COOKIE);
        if (Objects.nonNull(cookies) && cookies.contains(SESSION_COOKIE_KEY)) {
            return cookies
                .split(SESSION_COOKIE_KEY + COOKIE_KEY_VALUE_SEPARATOR)[VALUE_INDEX]
                .split(COOKIE_SEPARATOR)[FIRST_COOKIE_INDEX];
        }

        throw new NoSessionException("No Session");
    }
}
