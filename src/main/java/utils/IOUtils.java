package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class IOUtils {

    public static String transformInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader br = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = br.readLine();

        while (!line.equals("")) {
            stringBuilder.append(line)
                .append("\n");
            line = br.readLine();
        }
        return stringBuilder.toString();
    }

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
}
