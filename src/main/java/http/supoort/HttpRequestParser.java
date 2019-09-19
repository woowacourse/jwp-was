package http.supoort;

import http.model.*;
import utils.IOUtils;

import java.io.InputStream;
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
        List<String> requestMessages = IOUtils.readData(inputStream);
        validate(requestMessages);
        String requestLine = requestMessages.get(0);
        List<String> headerLines = requestMessages.subList(1, requestMessages.size());
        try {
            String[] requestLineTokens = requestLine.split(SEPARATOR);
            HttpMethod method = HttpMethod.of(requestLineTokens[0]);
            if (method == HttpMethod.POST) {
                return parsePost(method, requestLineTokens[1], requestLineTokens[2], headerLines);
            }
            if (method == HttpMethod.GET) {
                return parserGet(method, requestLineTokens[1], requestLineTokens[2], headerLines);
            }
            if (method == HttpMethod.PUT) {
                return parsePost(method, requestLineTokens[1], requestLineTokens[2], headerLines);
            }
            if (method == HttpMethod.DELETE) {
                return parserGet(method, requestLineTokens[1], requestLineTokens[2], headerLines);
            }
            throw new IllegalHttpRequestException();

        } catch (IndexOutOfBoundsException e) {
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }

    private static void validate(List<String> requestLines) {
        if (requestLines.size() == 0) {
            throw new IllegalHttpRequestException();
        }
    }

    private static HttpRequest parsePost(HttpMethod method, String uri, String protocol, List<String> headerLines) {
        HttpProtocols httpProtocol = HttpProtocols.of(protocol);
        HttpParameters httpParameters = new HttpParameters(parseParameter(getQueryStringFromBody(headerLines)));
        HttpHeaders headers = new HttpHeaders(parseHeaders(getHeaderWithoutMessageBody(headerLines)));
        HttpUri httpUri = parseUri(uri, httpParameters);
        return new HttpRequest(method, httpUri, httpParameters, httpProtocol, headers);
    }

    private static HttpRequest parserGet(HttpMethod method, String uri, String protocol, List<String> headerLines) {
        HttpProtocols httpProtocol = HttpProtocols.of(protocol);
        HttpHeaders headers = new HttpHeaders(parseHeaders(headerLines));
        HttpParameters httpParameters = new HttpParameters();
        HttpUri httpUri = parseUri(uri, httpParameters);
        return new HttpRequest(method, httpUri, httpParameters, httpProtocol, headers);
    }

    private static List<String> getHeaderWithoutMessageBody(List<String> headerLines) {
        return headerLines.subList(0, headerLines.size() - 2);
    }

    private static String getQueryStringFromBody(List<String> headerLines) {
        return headerLines.get(headerLines.size() - 1);
    }

    private static Map<String, String> parseHeaders(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();
        for (String header : headerLines) {
            String[] tmp = header.split(HEADER_SEPARATOR);
            headers.put(tmp[0], tmp[1]);
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
