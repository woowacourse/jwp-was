package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.RequestHandler;

public class IOUtils {
    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static void displayHeaderLog(final BufferedReader bufferedReader, String line,
            final HashMap<String, String> requestUrl) throws IOException {
        while(!"".equals(line)) {
            logger.debug("header Line: {} " + line);
            String[] headerToken = line.split(": ");
            if(headerToken.length == 2) {
                requestUrl.put(headerToken[0], headerToken[1]);
            }
            line = bufferedReader.readLine();
        }
    }
}
