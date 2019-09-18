package webserver;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestParameter {
    private final Map<String, String> requestParameters = new HashMap<>();

    public RequestParameter(String queryString) {
        parse(queryString);
    }

    private void parse(String queryString) {
        for (String query : queryString.split("&")) {
            String[] q = query.split("=");
            putRequestParameter(q);
        }
    }

    private void putRequestParameter(String[] q) {
        if (q.length == 2 && !StringUtils.isEmpty(q[0])) {
            requestParameters.put(q[0], q[1]);
        }
    }

    public String getParameter(String key) {
        return requestParameters.get(key);
    }
}
