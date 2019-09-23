package http.supoort.converter.request;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import utils.IOUtils;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRequestMessageConverter implements RequestMessageConverter {
    private static final String QUERY_STRING_INDICATOR = "?";
    private static final String QUERY_STRING_SEPARATOR = "&";
    private static final String QUERY_STRING_DELIMITER = "=";
    private static final String HEADER_SEPARATOR = ": ";
    private static final String BODY_LENGTH = "Content-Length";

    protected ServletRequest convertWithBody(HttpMethod httpMethod, String uri, String protocol, BufferedReader bufferedReader) {
        List<String> lines = IOUtils.readData(bufferedReader);

        Map<String, String> headers = parseHeaders(lines);
        Map<String, String> params = parseParameter(getRequestBody(headers, bufferedReader));
        uri = parseUri(params, uri);

        return ServletRequest.builder()
                .requestLine(httpMethod, uri, protocol)
                .headers(headers)
                .params(params)
                .build();
    }

    protected ServletRequest convertWithoutBody(HttpMethod httpMethod, String uri, String protocol, BufferedReader bufferedReader) {
        List<String> lines = IOUtils.readData(bufferedReader);

        Map<String, String> headers = parseHeaders(lines);
        Map<String, String> params = new HashMap<>();
        if (containsQueryString(uri)) {
            appendParameterFromUri(params, uri);
            uri = getPureHttpUri(uri);
        }

        return ServletRequest.builder()
                .requestLine(httpMethod, uri, protocol)
                .headers(headers)
                .params(params)
                .build();
    }

    private String getRequestBody(Map<String, String> headers, BufferedReader bufferedReader) {
        int contentLength = Integer.parseInt(headers.get(BODY_LENGTH));
        return IOUtils.readData(bufferedReader, contentLength);

    }

    private Map<String, String> parseHeaders(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();
        for (String header : headerLines) {
            String[] headerTokens = header.split(HEADER_SEPARATOR);
            headers.put(headerTokens[0], headerTokens[1]);
        }
        return headers;
    }

    private String parseUri(Map<String, String> params, String uri) {
        if (containsQueryString(uri)) {
            appendParameterFromUri(params, uri);
            return getPureHttpUri(uri);
        }
        return uri;
    }

    private void appendParameterFromUri(Map<String, String> params, String uri) {
        for (Map.Entry<String, String> entry : parseParameter(getQueryStringFromUri(uri)).entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
    }

    private boolean containsQueryString(String uri) {
        return uri.contains(QUERY_STRING_INDICATOR);
    }

    private Map<String, String> parseParameter(String queryString) {
        Map<String, String> parameters = new HashMap<>();
        putKeyAndValue(queryString, parameters);
        return parameters;
    }

    private void putKeyAndValue(String queryString, Map<String, String> parameters) {
        String[] details = queryString.split(QUERY_STRING_SEPARATOR);
        for (String detail : details) {
            String[] entry = detail.split(QUERY_STRING_DELIMITER);
            parameters.put(entry[0], entry[1]);
        }
    }

    private String getPureHttpUri(String uri) {
        return uri.substring(0, uri.indexOf(QUERY_STRING_INDICATOR));
    }

    private String getQueryStringFromUri(String uri) {
        return uri.substring(uri.indexOf(QUERY_STRING_INDICATOR) + 1);
    }
}
