package utils;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
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

    public static List<String> readBeforeBlankLine(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();

        String line = bufferedReader.readLine();
        while (!StringUtils.isEmpty(line)) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
        return lines;
    }
}
