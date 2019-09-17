package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestParseUtils {
    public static List<String> parseRequestHeader(BufferedReader buffer) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while (!"".equals(line = buffer.readLine())) {
            if (line == null) {
                break;
            }
            lines.add(line);
        }
        return lines;
    }

}
