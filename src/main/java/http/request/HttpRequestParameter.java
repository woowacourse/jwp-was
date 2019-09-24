package http.request;

import http.request.exception.NotFoundHttpRequestParameter;
import utils.ParameterParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequestParameter {
    static final HttpRequestParameter EMPTY_PARAMETER = new HttpRequestParameter(new HashMap<>());

    private Map<String, String> parameters;

    private HttpRequestParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static HttpRequestParameter of(String parameterLine) throws UnsupportedEncodingException {
        if ("".equals(parameterLine) || parameterLine == null) {
            return EMPTY_PARAMETER;
        }
        return new HttpRequestParameter(ParameterParser.parse(URLDecoder.decode(parameterLine, "UTF-8")));
    }

    public String getParameter(String key) {
        return Optional.ofNullable(parameters.get(key))
                .orElseThrow(() -> new NotFoundHttpRequestParameter(key));
    }

}
