package webserver.http.request;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class HttpRequestBody {
    private final Map<String, String> parameters;

    public HttpRequestBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public HttpRequestBody(String line) throws UnsupportedEncodingException {
        this.parameters = ParameterParser.parse(line);
    }
}
