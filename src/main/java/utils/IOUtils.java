package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

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

    public static List<String> readHeaders(BufferedReader bufferedReader) throws
            IOException {
        List<String> headers = new ArrayList<>();

        String line = bufferedReader.readLine();
        while (isHeaderLine(line)) {
            headers.add(line);
            logger.debug("Request Header: {}", line);
            line = bufferedReader.readLine();
        }
        return headers;
    }

    private static boolean isHeaderLine(String line) {
        return !"".equals(line) && line != null;
    }
}
