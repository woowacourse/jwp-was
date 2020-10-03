package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class IOUtils {

    public static String readDataBeforeEmptyLine(BufferedReader br) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        String line = br.readLine();
        validateLineIsNull(line);

        while (!line.equals("")) {
            stringBuilder.append(line)
                .append("\n");
            line = br.readLine();
            validateLineIsNull(line);
        }
        return stringBuilder.toString();
    }

    private static void validateLineIsNull(String line) {
        if (Objects.isNull(line)) {
            throw new IllegalArgumentException("an empty line must exist at the end of the request.");
        }
    }

    /**
     * @param bufferedReader
     *            HttpRequest Body를 시작하는 시점이어야
     * @param contentLength
     *            HttpRequest Header의 Content-Length 값이다.
     */
    public static String readData(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        bufferedReader.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
