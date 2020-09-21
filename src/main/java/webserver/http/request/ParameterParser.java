package webserver.http.request;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ParameterParser {
    public static Map<String, String> parse(String line) throws UnsupportedEncodingException {
        String[] queryStrings = line.split("&");

        Map<String, String> parameters = new HashMap<>();
        for (String queryString : queryStrings) {
            String[] splited = queryString.split("=");
            if (splited.length >= 2) {
                parameters.put(splited[0], splited[1]);
            }
        }
        return parameters;
    }
}
