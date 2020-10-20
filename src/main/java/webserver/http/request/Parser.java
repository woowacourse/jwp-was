package webserver.http.request;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private static final String AND = "&";
    private static final String EQUAL = "=";
    private static final String COLON = ":";

    public static Map<String, String> parseParameter(String line) throws UnsupportedEncodingException {
        String[] queryStrings = line.split(AND);

        Map<String, String> parameters = new HashMap<>();
        for (String queryString : queryStrings) {
            String[] splited = queryString.split(EQUAL);
            if (splited.length >= 2) {
                parameters.put(splited[0], splited[1]);
            }
        }
        return parameters;
    }

    public static Pair parseHeader(String line) throws UnsupportedEncodingException {
        String[] header = line.split(COLON, 2);
        return new Pair(header[0].trim(), header[1].trim());
    }

    public static class Pair {
        private final String key;
        private final String value;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
