package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    private static final String NEXT_LINE_DELIMITER = "\n";

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


    public static String parseData(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = br.readLine()) != null) {
            sb.append(data);
            sb.append(NEXT_LINE_DELIMITER);
        }
        return sb.toString();
    }
}
