package http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

public class HttpRequestParameter {
    private static final String PARAMETER_LINE_SPLITTER = "&";
    private static final String PARAMETER_SPLITTER = "=";
    private static final HttpRequestParameter EMPTY_PARAMETER = new HttpRequestParameter(new HashMap<>());
    private Map<String, String> parameters;

    private HttpRequestParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static HttpRequestParameter of(String parameterLine) throws UnsupportedEncodingException {
        if("".equals(parameterLine) || parameterLine == null) {
            return EMPTY_PARAMETER;
        }
        parameterLine = URLDecoder.decode(parameterLine, "UTF-8");
        List<String> parameterLines = Arrays.asList(parameterLine.split(PARAMETER_LINE_SPLITTER));

        Map<String, String> parameters = parameterLines.stream().collect(Collectors.toMap(
                parameter -> parameter.split(PARAMETER_SPLITTER)[0],
                parameter -> parameter.split(PARAMETER_SPLITTER)[1]));

        return new HttpRequestParameter(parameters);
    }

    public String getParameter(String key) {
        return Optional.ofNullable(parameters.get(key))
                .orElseThrow(() -> new NotFoundHttpRequestParameter(key));
    }

}
