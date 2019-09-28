package http;

import com.google.common.collect.Maps;
import session.HttpSession;

import java.util.Map;

public class HttpCookie {
    private static final String ATTRIBUTE_DELIMITER = "=";
    private static final String ATTRIBUTE_END = "; ";
    private static final String EMPTY_STRING = "";

    private Map<Option, String> options = Maps.newHashMap();
    private String key;
    private String value;

    public HttpCookie(String line) {
        String[] tokens = line.split(ATTRIBUTE_DELIMITER);
        this.key = tokens[0];
        this.value = tokens[1];
    }

    public HttpCookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public HttpCookie(HttpSession httpSession) {
        this.key = HttpSession.SESSION_ID;
        this.value = httpSession.getId();
    }

    public static HttpCookie emptyInstance() {
        return new HttpCookie(EMPTY_STRING, EMPTY_STRING);
    }

    public void setPath(String path) {
        options.put(Option.PATH, path);
    }

    public String getAttribute(Option option) {
        return options.getOrDefault(option, EMPTY_STRING);
    }

    public boolean checkName(String key) {
        return this.key.equals(key);
    }

    public String getValue() {
        return value;
    }

    public String getResponse() {
        StringBuffer sb = new StringBuffer();
        sb.append(key).append(ATTRIBUTE_DELIMITER).append(value).append(ATTRIBUTE_END);

        for (Option key : options.keySet()) {
            sb.append(key.phrase).append(ATTRIBUTE_DELIMITER).append(options.get(key)).append(ATTRIBUTE_END);
        }

        return sb.toString();
    }

    public enum Option {
        PATH("Path");

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
}
