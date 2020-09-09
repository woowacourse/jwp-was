package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static final String BLANK = " ";
    public static final int URL_INDEX = 1;

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

    public static String extractURL(String requestLine) {
        String[] tokens = requestLine.split(BLANK);
        return tokens[URL_INDEX];
    }

    public static void printRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        if (br == null) {
            return;
        }
        String result = "\n";
        String line = " ";
        while (!"".equals(line)) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            result += line + "\n";
        }
        logger.debug(result);
    }
}
