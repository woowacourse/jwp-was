package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

    public static String readData(BufferedReader br, int contentLength) {
        char[] body = new char[contentLength];
        try {
            br.read(body, 0, contentLength);
            return String.copyValueOf(body);
        } catch (IOException e) {
            throw new RuntimeException("BufferedReader IOException in IOUtils.readData");
        }
    }
}
