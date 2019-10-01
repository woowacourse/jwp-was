package http.parser;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CookiesParser {

    public static Map<String, String> parse(String line) {
        if (StringUtils.isEmpty(line)) {
            return Maps.newHashMap();
        }
        Map<String, String> cookies = new HashMap<>();

        String[] cookiesTokens = line.split(";");
        Arrays.stream(cookiesTokens).forEach(cookieToken -> {
            String[] cookieTokens = cookieToken.split("=");
            String name = cookieTokens[0].trim();
            String value = cookieTokens[1].trim();
            cookies.put(name, value);
        });
        return cookies;
    }
}
