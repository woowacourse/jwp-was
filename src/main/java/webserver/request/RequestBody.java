package webserver.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestBody {
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String ATTRIBUTE_SEPARATOR = "=";
    private static final String BLANK = "";

    private final Map<String, String> parameters;

    private RequestBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static RequestBody of(BufferedReader br, int contentLength) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        String body = IOUtils.readData(br, contentLength);
        body = URLDecoder.decode(body, "UTF-8");

        String[] attributes = body.split(PARAMETER_SEPARATOR);
        for (String attribute : attributes) {
            addAttribute(parameters, attribute);
        }
        return new RequestBody(parameters);
    }

    private static void addAttribute(Map<String, String> parameters, String attribute) {
        String[] splitAttribute = attribute.split(ATTRIBUTE_SEPARATOR);
        String key = splitAttribute[0];
        String value = determineValue(splitAttribute);
        parameters.put(key, value);
    }

    private static String determineValue(String[] attribute) {
        if (isNotBlankValue(attribute)) {
            return attribute[1];
        }
        return BLANK;
    }

    private static boolean isNotBlankValue(String[] attribute) {
        return attribute.length == 2;
    }

    public String getParameterValue(String parameterName) {
        if (parameters.containsKey(parameterName)) {
            return parameters.get(parameterName);
        }
        throw new IllegalArgumentException("Not Found Body parameter");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBody that = (RequestBody) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }
}
