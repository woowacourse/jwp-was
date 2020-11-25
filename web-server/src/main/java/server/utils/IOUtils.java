package server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    /**
     * @param bufferedReader 는 Request Body를 시작하는 시점이어야
     * @param contentLength  는  Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        bufferedReader.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }


    /**
     * @param bufferedReader 는 Request Header 시작하는 시점이어야 한다.
     * @return
     * @throws IOException
     */
    public static List<String> readHeader(BufferedReader bufferedReader) throws IOException {
        List<String> headers = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (!isEmpty(line)) {
            headers.add(line);
            line = bufferedReader.readLine();
        }
        return headers;
    }

    private static boolean isEmpty(String line) {
        return line == null || "".equals(line);
    }
}
