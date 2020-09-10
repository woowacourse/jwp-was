package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);
    private static final int REQUEST_URL_INDEX = 1;

    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static Map<String, String> readRequestHeader(BufferedReader bufferedReader) throws
            IOException {
        String line = null;
        Map<String, String> requestHeaders = new HashMap<>();

        while (!"".equals(line = bufferedReader.readLine())) {
            if (line == null) {
                break;
            }
            String[] split = line.split(": ");
            requestHeaders.put(split[0], split[1]);
            logger.debug("Request Header: {}", line);
        }
        
        return requestHeaders;
    }

}
