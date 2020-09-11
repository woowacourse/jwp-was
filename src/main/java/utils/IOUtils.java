package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {
    private static final String NEW_LINE = "\n";
    private static final String SEPARATOR = " : ";
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    /**
     * @param BufferedReader는 Request Body를 시작하는 시점이어야
     * @param contentLength는  Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static void printHeader(Map<String, String> headers) {
        StringBuilder stringBuilder = new StringBuilder(NEW_LINE);

        for (String key : headers.keySet()) {
            stringBuilder.append(key + SEPARATOR + headers.get(key) + NEW_LINE);
        }

        logger.debug(stringBuilder.toString());
    }
}
