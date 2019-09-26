package webserver.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class HttpCookie {
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    private Map<String, String> frontField;
    private Map<String, String> pairOption;
    private List<String> singleOption;

    public HttpCookie() {
        this.frontField = new HashMap<>();
        this.pairOption = new HashMap<>();
        this.singleOption = new ArrayList<>();
    }

    public void loginCookie(boolean logined, String path) {
        pairOption.put("Path", path);

        if (logined) {
            frontField.put("logined", TRUE);
            return;
        }

        frontField.put("logined", FALSE);
    }

    public String joinSetCookie() {
        StringJoiner stringJoiner = new StringJoiner("; ");
        frontField.keySet().forEach(key ->stringJoiner.add(key + "=" + frontField.get(key)));
        pairOption.keySet().forEach(key -> stringJoiner.add(key + "=" + pairOption.get(key)));
        singleOption.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
}
