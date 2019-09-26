package http;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpCookie {
    public enum Option {
        MAX_AGE("Max-Age"),
        DOMAIN("Domain"),
        PATH("Path"),
        SECURE("Secure"),
        HTTP_ONLY("HttpOnly"),
        SESSION_ID("SessionId");

        private String phrase;

        Option(String phrase) {
            this.phrase = phrase;
        }

        static Option valueOfPhrase(String phrase) {
            for (Option value : Option.values()) {
                if (value.phrase.equals(phrase)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("지원하지 않는 쿠키옵션입니다.");
        }

        public String getPhrase() {
            return phrase;
        }
    }

    private static final String ATTRIBUTE_DELIMITER = "=";
    private static final String ATTRIBUTE_END = "; ";
    private static final String EMPTY_STRING = "";


    private Map<String, String> contents = Maps.newHashMap();
    private List<Option> options = new ArrayList<>();

    public HttpCookie() {
    }

    public HttpCookie(String line) {
        for (String keyValue : line.split(ATTRIBUTE_END)) {
            String[] tokens = keyValue.split(ATTRIBUTE_DELIMITER);
            if (tokens.length == 2) {
                contents.put(tokens[0], tokens[1]);
                continue;
            }
            options.add(Option.valueOfPhrase(tokens[0]));
        }
    }

    public void setAttribute(String key, String value) {
        contents.put(key, value);
    }

    public void useSecure() {
        options.add(Option.SECURE);
    }

    public void useHttpOnly() {
        options.add(Option.HTTP_ONLY);
    }

    public String getAttribute(String key) {
        return contents.getOrDefault(key, EMPTY_STRING);
    }

    public String getResponse() {
        StringBuffer sb = new StringBuffer();
        for (String key : contents.values()) {
            sb.append(key).append(ATTRIBUTE_DELIMITER).append(contents.get(key)).append(ATTRIBUTE_END);
        }
        for (Option option : options) {
            sb.append(option.phrase).append(ATTRIBUTE_END);
        }

        return sb.toString();
    }
}
