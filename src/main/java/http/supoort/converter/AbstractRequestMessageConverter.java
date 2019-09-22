package http.supoort.converter;

import http.model.*;
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

    protected HttpRequest convertWithoutBody(HttpMethod httpMethod, String uri, String protocol, BufferedReader bufferedReader) {
        List<String> headerLines = IOUtils.readData(bufferedReader);
        HttpProtocols httpProtocol = HttpProtocols.of(protocol);
        HttpHeaders headers = new HttpHeaders(parseHeaders(headerLines));
        HttpParameters httpParameters = new HttpParameters(parseParameter(getRequestBody(headers, bufferedReader)));
        HttpUri httpUri = parseUri(uri, httpParameters);
        return new HttpRequest(httpMethod, httpUri, httpProtocol, httpParameters, headers);
    }

    protected HttpRequest convertWithBody(HttpMethod httpMethod, String uri, String protocol, BufferedReader bufferedReader) {
        List<String> headerLines = IOUtils.readData(bufferedReader);
        HttpProtocols httpProtocol = HttpProtocols.of(protocol);
        HttpHeaders headers = new HttpHeaders(parseHeaders(headerLines));
        HttpParameters httpParameters = new HttpParameters();
        HttpUri httpUri = parseUri(uri, httpParameters);
        return new HttpRequest(httpMethod, httpUri, httpProtocol, httpParameters, headers);
    }

    protected String getRequestBody(HttpHeaders httpHeaders, BufferedReader bufferedReader) {
        int contentLength = Integer.parseInt(httpHeaders.getHeader(BODY_LENGTH));
        return IOUtils.readData(bufferedReader, contentLength);

    }

    protected Map<String, String> parseHeaders(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();
        for (String header : headerLines) {
            String[] headerTokens = header.split(HEADER_SEPARATOR);
            headers.put(headerTokens[0], headerTokens[1]);
        }
        return headers;
    }

    protected HttpUri parseUri(String uri, HttpParameters httpParameters) {
        if (uri.contains(QUERY_STRING_INDICATOR)) {
            appendParameter(uri, httpParameters);
            return getPureHttpUri(uri);
        }
        return new HttpUri(uri);
    }

    protected void appendParameter(String uri, HttpParameters httpParameters) {
        for (Map.Entry<String, String> entry : parseParameter(getQueryStringFromUri(uri)).entrySet()) {
            httpParameters.addParameter(entry.getKey(), entry.getValue());
        }
    }

    protected Map<String, String> parseParameter(String queryString) {
        Map<String, String> parameters = new HashMap<>();
        putKeyAndValue(queryString, parameters);
        return parameters;
    }

    protected void putKeyAndValue(String queryString, Map<String, String> parameters) {
        String[] details = queryString.split(QUERY_STRING_SEPARATOR);
        for (String detail : details) {
            String[] entry = detail.split(QUERY_STRING_DELIMITER);
            parameters.put(entry[0], entry[1]);
        }
    }

    protected HttpUri getPureHttpUri(String uri) {
        return new HttpUri(uri.substring(0, uri.indexOf(QUERY_STRING_INDICATOR)));
    }

    protected String getQueryStringFromUri(String uri) {
        return uri.substring(uri.indexOf(QUERY_STRING_INDICATOR) + 1);
    }
}
