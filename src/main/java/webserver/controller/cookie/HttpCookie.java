package webserver.controller.cookie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

public class HttpCookie {
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    private Map<String, String> fields;
    private List<String> singleOption;

    public HttpCookie(UUID sessionId) {
        this.fields = new LinkedHashMap<>();
        this.singleOption = new ArrayList<>();
        fields.put("logined", FALSE);
        fields.put("sessionid", sessionId.toString());
    }

    public HttpCookie(String cookieValue) {
        this.fields = new LinkedHashMap<>();
        this.singleOption = new ArrayList<>();
        Arrays.stream(cookieValue.split("; "))
            .forEach(s -> {
                String[] cookieFields = s.split("=");
                fields.put(cookieFields[0], cookieFields[1]);
            });
    }

    public String getCookieValues() {
        StringJoiner stringJoiner = new StringJoiner("; ");
        fields.keySet()
            .forEach(key -> stringJoiner.add(key + "=" + fields.get(key)));
        singleOption.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    public Map<String, String> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public String getSessionId() {
        return this.fields.get("sessionid");
    }

    public void setSessionId(String sessionId) {
        this.fields.put("sessionid", sessionId);
        this.fields.put("logined", TRUE);
    }

    public boolean isLogined() {
        return this.fields.get("logined").equals(TRUE);
    }
}
