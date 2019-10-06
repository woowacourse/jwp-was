package http;

import utils.KeyValuesParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cookie {
    private static final String DEFAULT_PATH = "/";
    private static final String SEQUENCES_SPLITTER = ";";
    private static final String SEQUENCE_SPLITTER = "=";

    private static final String SESSION_ID_NAME = "JWP_WAS_SESSION_ID";

    private final Map<String, String> values;

    private Cookie(Map<String, String> values) {
        this.values = values;
    }

    public static Cookie create() {
        return new Cookie(new HashMap<>());
    }

    public static Cookie fromCookie(String cookie) {
        String cookieWithoutSpace = cookie.replace(" ", "");
        return new Cookie(parse(cookieWithoutSpace));
    }

    public static Cookie fromSession(Session session) {
        Cookie cookie = create();
        cookie.add(SESSION_ID_NAME, session.getId());
        return cookie;
    }

    private static Map<String, String> parse(String cookie) {
        return KeyValuesParser.parse(cookie, SEQUENCES_SPLITTER, SEQUENCE_SPLITTER);
    }

    public Optional<String> getValue(String name) {
        return Optional.ofNullable(values.get(name));
    }

    public void add(String name, String value) {
        values.put(name, value);
    }

    public int size() {
        return values.size();
    }

    public String toHeaderValue() {
        List<String> sequences = values.keySet().stream()
                .map(name -> name + SEQUENCE_SPLITTER + values.get(name))
                .collect(Collectors.toList());

        return String.format("%s; path=%s", "".join(SEQUENCES_SPLITTER, sequences), DEFAULT_PATH);
    }
}
