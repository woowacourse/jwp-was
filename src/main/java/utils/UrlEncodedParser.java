package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UrlEncodedParser {

    private static final Logger logger = LoggerFactory.getLogger(UrlEncodedParser.class);
    private static final String PAIR_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String ENCODE_CHARSET = "UTF-8";

    public static Map<String, String> parse(String urlEncodedString) {
        String[] tokenPairs = urlEncodedString.split(PAIR_DELIMITER);
        Map<String, String> parsedPairs = new HashMap<>();
        Arrays.stream(tokenPairs)
                .map(UrlEncodedParser::parsePair)
                .filter(Objects::nonNull)
                .forEach(tuple -> parsedPairs.put(tuple.getKey(), tuple.getValue()));

        return parsedPairs;
    }

    public static Tuple<String, String> parsePair(String pairToken) {
        String[] tokens = pairToken.split(KEY_VALUE_DELIMITER);
        try {
            return new Tuple<>(tokens[0], URLDecoder.decode(tokens[1], ENCODE_CHARSET));
        } catch (UnsupportedEncodingException e) {
            logger.error("error : ", e);
        }
        return null;
    }
}
