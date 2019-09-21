package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UrlEncodedParser {

    private static final Logger logger = LoggerFactory.getLogger(UrlEncodedParser.class);
    private static final String PAIR_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String ENCODE_CHARSET = "UTF-8";

    public static Map<String, String> parse(String urlEncodedString) {
        String[] tokenPairs = urlEncodedString.split(PAIR_DELIMITER);
        Map<String, String> parsed = new HashMap<>();
        Arrays.stream(tokenPairs)
            .forEach(pairToken -> parsePair(parsed, pairToken));

        return parsed;
    }

    private static void parsePair(Map<String, String> parsed, String pairToken) {
        String[] tokens = pairToken.split(KEY_VALUE_DELIMITER);
        try {
            parsed.put(tokens[0], URLDecoder.decode(tokens[1], ENCODE_CHARSET));
        } catch (UnsupportedEncodingException e) {
            logger.error("error : ", e);
        }
    }
}
