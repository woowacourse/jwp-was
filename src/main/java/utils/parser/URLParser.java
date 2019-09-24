package utils.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class URLParser extends SimpleStringParser {
    private static final Logger logger = LoggerFactory.getLogger(URLParser.class);

    protected URLParser(String pairDelimiter, String keyValueDelimiter) {
        super(pairDelimiter, keyValueDelimiter);
    }

    @Override
    public Map<String, String> toMap(String input) {
        try {
            return super.toMap(URLDecoder.decode(input, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            logger.debug(e.getMessage());
            return new HashMap<>();
        }
    }
}