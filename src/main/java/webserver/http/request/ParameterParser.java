package webserver.http.request;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ParameterParser {
    private static final String AND = "&";
    private static final String EQUAL = "=";

    public static Map<String, String> parse(String line) throws UnsupportedEncodingException {
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
}
