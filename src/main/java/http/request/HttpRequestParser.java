package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import exceptions.InvalidHttpRequestException;
import http.HttpMethod;

public class HttpRequestParser {

    private static final String COOKIE_HEADER = "Cookie";
    private static final String COOKIE_SEPARATOR = ";";
    private static final String COOKIE_DELIMITER = "=";

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        Map<String, String> headers = requestHeader.getHeaders();
        Map<String, String> cookies = parseCookie(headers);

        HttpMethod method = requestLine.getMethod();
        if (HttpMethod.GET == method) {
            return HttpRequest.of(requestLine, requestHeader, null, cookies, requestLine.getParam());
        }
        if (HttpMethod.POST == method) {
            RequestBody requestBody = new RequestBody(bufferedReader, requestHeader.getContentLength());
            Map<String, String> parameters = requestBody.parseBody();
            return HttpRequest.of(requestLine, requestHeader, requestBody, cookies, parameters);
        }
        throw new InvalidHttpRequestException();
    }

    private static Map<String, String> parseCookie(Map<String, String> headers) {
        Map<String, String> cookies = new HashMap<>();
        if (headers.containsKey(COOKIE_HEADER)) {
            String cookieValues = headers.get(COOKIE_HEADER);
            headers.remove(COOKIE_HEADER);
            addCookies(cookies, cookieValues);
        }
        return cookies;
    }

    private static void addCookies(Map<String, String> cookies, String cookieValues) {
        if (cookieValues.contains(COOKIE_SEPARATOR)) {
            addMultipleValues(cookies, cookieValues);
            return;
        }
        addSingleValue(cookies, cookieValues);
    }

    private static void addSingleValue(Map<String, String> cookies, String cookieValues) {
        String[] cookieKeyValue = cookieValues.split(COOKIE_DELIMITER);
        cookies.put(cookieKeyValue[0].trim(), cookieKeyValue[1].trim());
    }

    private static void addMultipleValues(Map<String, String> cookies, String cookieValues) {
        for (String cookieValue : cookieValues.split(COOKIE_SEPARATOR)) {
            addCookies(cookies, cookieValue);
        }
    }

}
