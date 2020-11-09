package web.http;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;
import utils.HttpRequestUtils;

public class HttpRequestParams {

    private static final String QUESTION_MARK = "?";

    private final Map<String, String> params = new HashMap<>();

    public void addQueryString(String queryString) {
        int index = queryString.indexOf(QUESTION_MARK);
        if (index == -1) {
            return;
        }
        putParams(queryString.substring(index + 1));
    }

    public void addBody(String body) {
        putParams(body);
    }

    private void putParams(String data) {
        if (Strings.isNullOrEmpty(data)) {
            return;
        }

        params.putAll(HttpRequestUtils.parseQueryString(data));
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getParameter(String key) {
        return params.get(key);
    }
}
