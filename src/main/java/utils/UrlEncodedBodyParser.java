package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UrlEncodedBodyParser {

    private static final Logger logger = LoggerFactory.getLogger(UrlEncodedBodyParser.class);

    public static Map<String, String> parse(String body) {
        String[] tokenPairs = body.split("&");
        Map<String, String> parsedBody = new HashMap<>();
        Arrays.stream(tokenPairs)
            .forEach(t -> parsePair(parsedBody, t));

        return parsedBody;
    }

    private static void parsePair(Map<String, String> parsedBody, String t) {
        String[] tokens = t.split("=");
        try {
            parsedBody.put(tokens[0], URLDecoder.decode(tokens[1], "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("error : ", e);
        }
    }
}
