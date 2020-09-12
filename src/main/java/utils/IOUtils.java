package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

    public static String readDataBeforeEmptyLine(BufferedReader br) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = br.readLine();

        while (!line.equals("")) {
            stringBuilder.append(line)
                .append("\n");
            line = br.readLine();
        }
        return stringBuilder.toString();
    }

    /**
     * @param bufferedReader
     *            Request Body를 시작하는 시점이어야
     * @param contentLength
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        bufferedReader.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
