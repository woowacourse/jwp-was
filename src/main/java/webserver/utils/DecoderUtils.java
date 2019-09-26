package webserver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DecoderUtils {
    private static final Logger logger = LoggerFactory.getLogger(DecoderUtils.class);

    public static String decodeString(String encodedString) {
        try {
            return URLDecoder.decode(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return encodedString;
    }
}
