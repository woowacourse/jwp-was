package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readBodyData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static List<String> readHeaderData(final BufferedReader reader) throws IOException {
        final List<String> headerData = new ArrayList<>();

        String line = reader.readLine();
        while (!"".equals(line)) {
            headerData.add(line);
            line = reader.readLine();
        }
        return headerData;
    }
}
