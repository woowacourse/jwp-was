package utils;

import http.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    private static final String NEW_LINE = System.lineSeparator();
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

    public static void printRequest(Request request) {
        StringBuilder stringBuilder = new StringBuilder(NEW_LINE);
        stringBuilder.append(request.getRequestLine().toString() + NEW_LINE);
        for (String key : request.getRequestHeaders().keySet()) {
            stringBuilder.append(key)
                    .append(SEPARATOR)
                    .append(request.getHeader(key))
                    .append(NEW_LINE);
        }

        stringBuilder.append(NEW_LINE);
        stringBuilder.append(request.getRequestBody().getBody());

        logger.debug(stringBuilder.toString());
    }
}
