package http.request;

import http.request.exception.NotFoundHttpRequestParameter;
import utils.ParameterParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequestParameter {
    public static final HttpRequestParameter EMPTY_PARAMETER = new HttpRequestParameter(new HashMap<>());

    private Map<String, String> parameters;

    private HttpRequestParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static HttpRequestParameter of(String parameterLine) throws UnsupportedEncodingException {
        if ("".equals(parameterLine) || parameterLine == null) {
            return EMPTY_PARAMETER;
        }
        parameterLine = URLDecoder.decode(parameterLine, "UTF-8");

        return new HttpRequestParameter(ParameterParser.parse(parameterLine));
    }

    public String getParameter(String key) {
        return Optional.ofNullable(parameters.get(key))
                .orElseThrow(() -> new NotFoundHttpRequestParameter(key));
    }

}
