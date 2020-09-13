package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IOUtils {

    private static final String HEADER_DELIMITER = "";

    /**
     * @param bufferedReader 는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength 는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        bufferedReader.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static List<String> readHeader(BufferedReader bufferedReader) throws IOException {
        List<String> header = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (Objects.nonNull(line) && !HEADER_DELIMITER.equals(line)) {
            header.add(line);
            line = bufferedReader.readLine();
        }
        return header;
    }
}
