package servlet.request;

import http.request.HttpRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class HttpServletRequest implements ServletRequest {
    private final HttpRequest httpRequest;

    public HttpServletRequest(final HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getHeader(String key) {
        return httpRequest.getHeader(key);
    }

    public String getCookie(String key) {
        Map<String, String> answer = new HashMap<>();
        String cookies = httpRequest.getHeader("Cookie");
        List<String> cookieValues = asList(cookies.split(";")).stream().map(String::trim).collect(Collectors.toList());
        for (String cookieValue : cookieValues) {
            List<String> keyAndValues = Arrays.asList(cookieValue.split("="));
            answer.put(keyAndValues.get(0).trim(), keyAndValues.get(1).trim());
        }
        return answer.get(key);
    }

    public String getParameter(String key) {
        return httpRequest.getParameter(key);
    }
}
