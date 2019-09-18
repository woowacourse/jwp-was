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
    private static final String PAIR_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String ENCODE_CHARSET = "UTF-8";

    public static Map<String, String> parse(String body) {
        String[] tokenPairs = body.split(PAIR_DELIMITER);
        Map<String, String> parsedBody = new HashMap<>();
        Arrays.stream(tokenPairs)
            .forEach(pairToken -> parsePair(parsedBody, pairToken));

        return parsedBody;
    }

    private static void parsePair(Map<String, String> parsedBody, String pairToken) {
        String[] tokens = pairToken.split(KEY_VALUE_DELIMITER);
        try {
            parsedBody.put(tokens[0], URLDecoder.decode(tokens[1], ENCODE_CHARSET));
        } catch (UnsupportedEncodingException e) {
            logger.error("error : ", e);
        }
    }
}
