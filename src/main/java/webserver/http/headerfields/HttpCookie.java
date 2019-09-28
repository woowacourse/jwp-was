package webserver.http.headerfields;

import utils.parser.KeyValueParser;

import java.util.*;

public class HttpCookie {
    private static final Map<Boolean, String> LOGINED_VALUE = new HashMap<>();

    static {
        LOGINED_VALUE.put(true, "true");
        LOGINED_VALUE.put(false, "false");
    }

    private Map<String, String> pair;
    private Map<String, String> pairOption;
    private List<String> singleOption;

    public HttpCookie() {
        this.pair = new HashMap<>();
        this.pairOption = new HashMap<>();
        this.singleOption = new ArrayList<>();
    }

    public void loginCookie(boolean login, String path) {
        this.pair.put("logined", LOGINED_VALUE.get(login));
        this.pairOption.put("Path", path);
    }

    public void sessionCookie(String sessionId) {
        this.pair.put("JSESSIONID", sessionId);
    }

    public String line() {
        StringJoiner stringJoiner = new StringJoiner("; ");

        pair.keySet().forEach(key -> stringJoiner.add(KeyValueParser.line(key, pair.get(key))));
        pairOption.keySet().forEach(key -> stringJoiner.add(KeyValueParser.line(key, pairOption.get(key))));
        singleOption.forEach(stringJoiner::add);

        return stringJoiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpCookie that = (HttpCookie) o;
        return Objects.equals(pair, that.pair) &&
                Objects.equals(pairOption, that.pairOption) &&
                Objects.equals(singleOption, that.singleOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pair, pairOption, singleOption);
    }
}
