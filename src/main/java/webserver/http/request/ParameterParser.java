package webserver.http.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ParameterParser {
    public static Map<String, String> parse(String line) throws UnsupportedEncodingException {
        line = URLDecoder.decode(line, "UTF-8");
        String[] queryStrings = line.split("&");

        Map<String, String> parameters = new HashMap<>();
        for (String queryString : queryStrings) {
            parameters.put(queryString.split("=")[0],
                    queryString.split("=")[1]);
        }
        return parameters;
    }
}
