package http.supoort;

import http.exceptions.IllegalHttpRequestException;
import http.model.*;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestParser {
    private static final String SEPARATOR = " ";
    private static final String QUERY_STRING_INDICATOR = "?";
    private static final String QUERY_STRING_SEPARATOR = "&";
    private static final String QUERY_STRING_DELIMITER = "=";
    private static final String HEADER_SEPARATOR = ": ";

    public static HttpRequest parse(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine = IOUtils.readFirstLine(bufferedReader);
        try {
            validate(requestLine);
            String[] requestLineTokens = requestLine.split(SEPARATOR);
            HttpMethod method = HttpMethod.of(requestLineTokens[0]);
            if (method == HttpMethod.POST) {
                return parsePost(method, requestLineTokens[1], requestLineTokens[2], bufferedReader);
            }
            if (method == HttpMethod.GET) {
                return parserGet(method, requestLineTokens[1], requestLineTokens[2], bufferedReader);
            }
            if (method == HttpMethod.PUT) {
                return parsePost(method, requestLineTokens[1], requestLineTokens[2], bufferedReader);
            }
            if (method == HttpMethod.DELETE) {
                return parserGet(method, requestLineTokens[1], requestLineTokens[2], bufferedReader);
            }
            throw new IllegalHttpRequestException();

        } catch (IndexOutOfBoundsException e) {
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }

    private static void validate(String requestLine) {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IllegalHttpRequestException();
        }
    }

    private static HttpRequest parsePost(HttpMethod method, String uri, String protocol, BufferedReader bufferedReader) {
        List<String> headerLines = IOUtils.readData(bufferedReader);
        HttpProtocols httpProtocol = HttpProtocols.of(protocol);
        HttpHeaders headers = new HttpHeaders(parseHeaders(headerLines));
        HttpParameters httpParameters = new HttpParameters(parseParameter(getRequestBody(headers, bufferedReader)));
        HttpUri httpUri = parseUri(uri, httpParameters);
        return new HttpRequest(method, httpUri, httpParameters, httpProtocol, headers);
    }

    private static HttpRequest parserGet(HttpMethod method, String uri, String protocol, BufferedReader bufferedReader) {
        List<String> headerLines = IOUtils.readData(bufferedReader);
        HttpProtocols httpProtocol = HttpProtocols.of(protocol);
        HttpHeaders headers = new HttpHeaders(parseHeaders(headerLines));
        HttpParameters httpParameters = new HttpParameters();
        HttpUri httpUri = parseUri(uri, httpParameters);
        return new HttpRequest(method, httpUri, httpParameters, httpProtocol, headers);
    }

    private static String getRequestBody(HttpHeaders httpHeaders, BufferedReader bufferedReader) {
        int contentLength = Integer.parseInt(httpHeaders.getHeader("Content-Length"));
        return IOUtils.readData(bufferedReader, contentLength);

    }

    private static Map<String, String> parseHeaders(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();
        for (String header : headerLines) {
            String[] headerTokens = header.split(HEADER_SEPARATOR);
            headers.put(headerTokens[0], headerTokens[1]);
        }
        return headers;
    }

    private static void appendParameter(String uri, HttpParameters httpParameters) {
        for (Map.Entry<String, String> entry : parseParameter(getQueryStringFromUri(uri)).entrySet()) {
            httpParameters.addParameter(entry.getKey(), entry.getValue());
        }
    }

    private static Map<String, String> parseParameter(String queryString) {
        Map<String, String> parameters = new HashMap<>();
        addKeyAndValue(queryString, parameters);
        return parameters;
    }

    private static void addKeyAndValue(String queryString, Map<String, String> parameters) {
        String[] details = queryString.split(QUERY_STRING_SEPARATOR);
        for (String detail : details) {
            String[] entry = detail.split(QUERY_STRING_DELIMITER);
            parameters.put(entry[0], entry[1]);
        }
    }

    private static String getQueryStringFromUri(String uri) {
        return uri.substring(uri.indexOf(QUERY_STRING_INDICATOR) + 1);
    }

    private static HttpUri parseUri(String uri, HttpParameters httpParameters) {
        if (uri.contains(QUERY_STRING_INDICATOR)) {
            appendParameter(uri, httpParameters);
            return getPureHttpUri(uri);
        }
        return new HttpUri(uri);
    }

    private static HttpUri getPureHttpUri(String uri) {
        return new HttpUri(uri.substring(0, uri.indexOf(QUERY_STRING_INDICATOR)));
    }
}
