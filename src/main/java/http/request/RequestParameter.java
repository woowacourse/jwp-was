package http.request;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParameter {
    private final Map<String, String> requestParameters = new HashMap<>();

    public RequestParameter(String queryString) {
        parse(queryString);
    }

    private void parse(String queryString) {
        for (String requestParameter : queryString.split("&")) {
            putRequestParameter(requestParameter.split("="));
        }
    }

    private void putRequestParameter(String[] requestParameter) {
        if (requestParameter.length == 2 && !StringUtils.isEmpty(requestParameter[0])) {
            requestParameters.put(decodeUTF8(requestParameter[0]), decodeUTF8(requestParameter[1]));
        }
    }

    private String decodeUTF8(String encodedString) {
        try {
            return URLDecoder.decode(encodedString, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return encodedString;
        }
    }

    public String getParameter(String key) {
        return requestParameters.get(key);
    }
}
