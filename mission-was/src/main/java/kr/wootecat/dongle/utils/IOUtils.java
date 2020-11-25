package kr.wootecat.dongle.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

    private static final String LINE_FEED = "\r\n";

    public static String readAllHeaders(BufferedReader br) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String singleLine = br.readLine();
        while (singleLine != null && !singleLine.isEmpty()) {
            stringBuilder.append(singleLine);
            stringBuilder.append(LINE_FEED);
            singleLine = br.readLine();
        }
        return stringBuilder.toString();
    }

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
