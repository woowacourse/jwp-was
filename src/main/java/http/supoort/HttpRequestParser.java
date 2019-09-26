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
        String requestLineMessage = requestMessages.get(0);
        List<String> headerLines = requestMessages.subList(1, requestMessages.size());
        try {
            String[] requestLineTokens = requestLineMessage.split(SEPARATOR);
            HttpMethod method = HttpMethod.of(requestLineTokens[0]);
            HttpProtocols httpProtocol = HttpProtocols.of(requestLineTokens[2]);
            HttpParameters httpParameters;
            HttpHeaders headers;

            if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                httpParameters = parsePostParameters(headerLines);
                headers = new HttpHeaders(parseHeaders(getHeaderWithoutMessageBody(headerLines)));
            } else if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
                httpParameters = new HttpParameters();
                headers = new HttpHeaders(parseHeaders(headerLines));
            } else {
                throw new IllegalHttpRequestException();
            }

            HttpUri httpUri = parseUri(requestLineTokens[1], httpParameters);
            RequestLine requestLine = new RequestLine(method, httpProtocol, httpUri);
            return new HttpRequest(requestLine, httpParameters, headers);

        } catch (IndexOutOfBoundsException e) {
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }

    private static void validate(List<String> requestLines) {
        if (requestLines.isEmpty()) {
            throw new IllegalHttpRequestException();
        }
    }

    private static HttpParameters parsePostParameters(List<String> headerLines) {
        return new HttpParameters(parseParameter(getQueryStringFromHeaderLines(headerLines)));
    }

    private static String getQueryStringFromHeaderLines(List<String> headerLines) {
        return headerLines.get(headerLines.size() - 1);
    }

    private static List<String> getHeaderWithoutMessageBody(List<String> headerLines) {
        return headerLines.subList(0, headerLines.size() - 2);
    }

    private static Map<String, String> parseHeaders(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();
        for (String headerLine : headerLines) {
            String[] header = headerLine.split(HEADER_SEPARATOR);
            headers.put(header[0], header[1]);
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
