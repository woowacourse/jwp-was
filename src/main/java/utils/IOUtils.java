package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

    private static final String NEW_LINE = System.lineSeparator();

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

    public static String readHeader(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = bufferedReader.readLine();

        while (line != null && !"".equals(line)) {
            sb.append(line).append(NEW_LINE);
            line = bufferedReader.readLine();
        }
        return sb.toString();
    }

    public static String readBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        if (contentLength == 0) {
            return "";
        }
        return readData(bufferedReader, contentLength);
    }
}
