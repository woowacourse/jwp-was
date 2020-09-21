package webserver.http.request;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import webserver.http.exception.InvalidHttpRequestException;

public class HttpRequestUrl {
    private static final String PATH_AND_PARAMETER_DIVIDER = "?";
    private static final String PARAMETERS_DIVIDER = "&";
    private static final String PARAMETER_DIVIDER = "=";
    private static final String EMPTY_CONTENT = "";

    private final String requestUrl;
    private final Map<String, String> parameters = new LinkedHashMap<>();

    public HttpRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        String parameterLine = requestUrl.substring(requestUrl.indexOf(PATH_AND_PARAMETER_DIVIDER) + 1);
        parseParameter(requestUrl, parameterLine);
    }

    private void parseParameter(String requestUrl, String parameterLine) {
        if (requestUrl.contains(PATH_AND_PARAMETER_DIVIDER)) {
            String[] splitedParameters = parameterLine.split(PARAMETERS_DIVIDER);

            for (String splitedParameter : splitedParameters) {
                String[] keyAndValue = splitedParameter.split(PARAMETER_DIVIDER);
                parameters.put(keyAndValue[0], keyAndValue[1]);
            }
            validate(parameters, splitedParameters.length);
        }
    }

    private void validate(Map<String, String> parameters, int originalLength) {
        if (parameters.size() != originalLength) {
            throw new InvalidHttpRequestException("겹치는 key가 있습니다.");
        }

        if (parameters.containsKey(EMPTY_CONTENT)) {
            throw new InvalidHttpRequestException("정보가 없는 key가 있습니다.");
        }
    }

    public String getPath() {
        if (hasParameters()) {
            return requestUrl.substring(0, requestUrl.indexOf(PATH_AND_PARAMETER_DIVIDER));
        }
        return requestUrl;
    }

    public boolean isSamePath(String path) {
        return getPath().equals(path);
    }

    public boolean isStaticFile() {
        return StaticFiles.endsWith(requestUrl);
    }

    private boolean hasParameters() {
        return !parameters.isEmpty();
    }

    protected Set<String> getKeySet() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    protected String getValueBy(String key) {
        return parameters.get(key);
    }

    public String getContentType() {
        return StaticFiles.getContentType(requestUrl);
    }
}
