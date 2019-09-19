package http;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpRequestParameter {
    private static final String PARAMETER_LINE_SPLITTER = "&";
    private static final String PARAMETER_SPLITTER = "=";

    private Map<String, String> parameters;

    private HttpRequestParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static HttpRequestParameter of(String parameterLine) {
        List<String> parameterLines = Arrays.asList(parameterLine.split(PARAMETER_LINE_SPLITTER));

        Map<String, String> parameters = parameterLines.stream().collect(Collectors.toMap(
                parameter -> parameter.split(PARAMETER_SPLITTER)[0],
                parameter -> parameter.split(PARAMETER_SPLITTER)[1]));

        return new HttpRequestParameter(parameters);
    }

    public String getParameter(String key) {
        return Optional.ofNullable(parameters.get(key))
                .orElseThrow(() -> new NotFoundHttpRequestHeader(key));
    }

}
