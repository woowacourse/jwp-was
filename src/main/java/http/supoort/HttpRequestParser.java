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
        List<String> requestLines = IOUtils.readData(inputStream);
        validate(requestLines);
        String requestHeadline = requestLines.get(0);
        List<String> headerLines = requestLines.subList(1, requestLines.size());
        try {
            String[] headlines = requestHeadline.split(SEPARATOR);
            HttpMethod method = HttpMethod.of(headlines[0]);
            if (method == HttpMethod.POST) {
                return parsePost(method, headlines[1], headlines[2], headerLines);
            }
            if (method == HttpMethod.GET) {
                return parserGet(method, headlines[1], headlines[2], headerLines);
            }
            if (method == HttpMethod.PUT) {
                return parsePost(method, headlines[1], headlines[2], headerLines);
            }
            if (method == HttpMethod.DELETE) {
                return parserGet(method, headlines[1], headlines[2], headerLines);
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
        HttpUri httpUri = new HttpUri(uri);
        HttpProtocols httpProtocol = HttpProtocols.of(protocol);
        HttpParameters httpParameters = new HttpParameters(parseParameter(headerLines.get(headerLines.size() - 1)));
        Map<String, String> headers = parseHeaders(headerLines);
        if (uri.contains(QUERY_STRING_INDICATOR)) {
            appendParameter(uri, httpParameters);
            httpUri = getPureHttpUri(uri);
        }
        return new HttpRequest(method, httpUri, httpParameters, httpProtocol, headers);
    }

    private static HttpRequest parserGet(HttpMethod method, String uri, String protocol, List<String> headerLines) {
        HttpUri httpUri = new HttpUri(uri);
        HttpParameters httpParameters = null;
        HttpProtocols httpProtocol = HttpProtocols.of(protocol);
        Map<String, String> headers = parseHeaders(headerLines);
        if (uri.contains(QUERY_STRING_INDICATOR)) {
            httpParameters = new HttpParameters(parseParameter(uri.substring(uri.indexOf(QUERY_STRING_INDICATOR) + 1)));
            httpUri = getPureHttpUri(uri);
        }
        return new HttpRequest(method, httpUri, httpParameters, httpProtocol, headers);
    }

    private static Map<String, String> parseHeaders(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();
        for (String header : headerLines) {
            String[] tmp = header.split(HEADER_SEPARATOR);
            if (tmp.length == 1) {
                return headers;
            }
            headers.put(tmp[0], tmp[1]);
        }
        return headers;
    }

    private static Map<String, String> parseParameter(String payload) {
        Map<String, String> parameters = new HashMap<>();
        String[] details = payload.split(QUERY_STRING_SEPARATOR);
        for (String detail : details) {
            String[] entry = detail.split(QUERY_STRING_DELIMITER);
            parameters.put(entry[0], entry[1]);
        }
        return parameters;
    }

    private static void appendParameter(String uri, HttpParameters httpParameters) {
        for (Map.Entry<String, String> entry : parseParameter(uri.substring(uri.indexOf(QUERY_STRING_INDICATOR) + 1)).entrySet()) {
            httpParameters.addParameter(entry.getKey(), entry.getValue());
        }
    }

    private static HttpUri getPureHttpUri(String uri) {
        return new HttpUri(uri.substring(0, uri.indexOf(QUERY_STRING_INDICATOR)));
    }
}
