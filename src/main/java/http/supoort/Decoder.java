package http.supoort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Decoder {
    private static final Logger logger = LoggerFactory.getLogger(Decoder.class);

    public static String decodeString(String encodedString) {
        try {
            return URLDecoder.decode(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return encodedString;
    }
}