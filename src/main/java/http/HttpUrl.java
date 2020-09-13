package http;

import java.util.LinkedHashMap;
import java.util.Map;

import http.exception.InvalidHttpRequestException;

public class HttpUrl {
    private static final String PATH_AND_PARAMETER_DIVIDER = "?";
    private static final String PARAMETERS_DIVIDER = "&";
    private static final String PARAMETER_DIVIDER = "=";
    private static final String EMPTY_CONTENT = "";

    private final String url;

    public HttpUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        if (hasParameters()) {
            return url.substring(0, url.indexOf(PATH_AND_PARAMETER_DIVIDER));
        }
        return url;
    }

    public Map<String, String> getParameters() {
        String parameterLine = url.substring(url.indexOf(PATH_AND_PARAMETER_DIVIDER) + 1);
        String[] splitedParameters = parameterLine.split(PARAMETERS_DIVIDER);

        Map<String, String> parameters = new LinkedHashMap<>();
        for (String splitedParameter : splitedParameters) {
            String[] keyAndValue = splitedParameter.split(PARAMETER_DIVIDER);
            parameters.put(keyAndValue[0], keyAndValue[1]);
        }
        validate(parameters, splitedParameters.length);
        return parameters;
    }

    private void validate(Map<String, String> parameters, int originalLength) {
        if (parameters.size() != originalLength) {
            throw new InvalidHttpRequestException("겹치는 key가 있습니다.");
        }

        if (parameters.containsKey(EMPTY_CONTENT)) {
            throw new InvalidHttpRequestException("정보가 없는 key가 있습니다.");
        }
    }

    public boolean isSamePath(String path) {
        return getPath().equals(path);
    }

    private boolean hasParameters() {
        return url.contains(PATH_AND_PARAMETER_DIVIDER);
    }

    public boolean isStaticFile() {
        return StaticFiles.endsWith(url);
    }

    public String getContentType() {
        return StaticFiles.getContentType(url);
    }
}
