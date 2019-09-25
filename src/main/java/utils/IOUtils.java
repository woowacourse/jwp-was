package utils;

import http.HttpRequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IOUtils {
    private static final String NEXT_LINE_DELIMITER = "\n";
    private static final String BLANK = "";

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

    public static String readData(BufferedReader br, HttpRequestHeader httpRequestHeader) throws IOException {
        int contentLength = Integer.parseInt(httpRequestHeader.get("Content-Length"));
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static String parseData(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String data = br.readLine();
        while (!BLANK.equals(data) && Objects.nonNull(data)) {
            sb.append(data);
            sb.append(NEXT_LINE_DELIMITER);
            data = br.readLine();
        }
        return sb.toString();
    }

    public static List<String> parseHeader(BufferedReader br) throws IOException {
        List<String> inputs = new ArrayList<>();
        String line = br.readLine();
        while (!BLANK.equals(line) && Objects.nonNull(line)) {
            inputs.add(line);
            line = br.readLine();
        }
        return inputs;
    }
}
