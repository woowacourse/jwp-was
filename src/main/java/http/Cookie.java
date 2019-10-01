package http;

import utils.KeyValuesParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cookie {
    private static final String DEFAULT_PATH = "/";
    private static final String SEQUENCES_SPLITTER = ";";
    private static final String SEQUENCE_SPLITTER = "=";

    private final Map<String, String> values;

    private static final Function<String, Map<String, String>> COOKIE_PARSER
            = (cookie) -> KeyValuesParser.parse(cookie, SEQUENCES_SPLITTER, SEQUENCE_SPLITTER);

    private Cookie(Map<String, String> values) {
        this.values = values;
    }

    public static Cookie create() {
        return new Cookie(new HashMap<>());
    }

    public static Cookie fromCookie(String cookie) {
        String cookieWithoutSpace = cookie.replace(" ", "");
        return new Cookie(COOKIE_PARSER.apply(cookieWithoutSpace));
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
