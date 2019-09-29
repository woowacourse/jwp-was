package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.exception.InvalidBodyException;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);
    /**
     * @param br 는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength 는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) {
        try {
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            return String.copyValueOf(body);
        } catch (IOException e) {
            logger.error("{}", e);
            throw new InvalidBodyException();
        }
    }
}
