package http.model;

import http.controller.NotFoundException;
import http.supoort.Decoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpParameters {
    private static final String EMPTY_LINE = "";
    private static final String QUERY_STRING_INDICATOR = "?";
    private static final String QUERY_STRING_SEPARATOR = "&";
    private static final String QUERY_STRING_DELIMITER = "=";

    private Map<String, String> parameters;

    public HttpParameters() {
        parameters = new HashMap<>();
    }

    public HttpParameters(String uri, HttpMethod method, List<String> headerLines) {
        parameters = new HashMap<>();

        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            parseRequestBody(headerLines);
            addParametersFromUri(uri);
        } else if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
            addParametersFromUri(uri);
        } else {
            throw new NotFoundException();
        }
    }

    private void parseRequestBody(List<String> headerLines) {
        getQueryStringFromHeaderLines(headerLines);
    }

    private void getQueryStringFromHeaderLines(List<String> headerLines) {
        if (EMPTY_LINE.equals(headerLines.get(headerLines.size() - 2))) {
            addKeyAndValue(headerLines.get(headerLines.size() - 1));
            return;
        }
        parameters = new HashMap<>();
    }

    private void addParametersFromUri(String uri) {
        if (uri.contains(QUERY_STRING_INDICATOR)) {
            addKeyAndValue(uri.substring(uri.indexOf(QUERY_STRING_INDICATOR) + 1));
        }
    }

    private void addKeyAndValue(String queryString) {
        queryString = Decoder.decodeString(queryString);

        String[] paramPairs = queryString.split(QUERY_STRING_SEPARATOR);
        for (String paramPair : paramPairs) {
            String[] entry = paramPair.split(QUERY_STRING_DELIMITER);
            parameters.put(entry[0], entry[1]);
        }
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpParameters that = (HttpParameters) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }
}
