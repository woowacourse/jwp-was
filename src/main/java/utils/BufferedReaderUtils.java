package utils;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BufferedReaderUtils {
    public static List<String> readHeader(BufferedReader br) throws IOException {
        String line;
        List<String> lines = new ArrayList<>();
        while (!StringUtils.isEmpty(line = br.readLine())) {
            lines.add(line);
        }
        return lines;
    }
}
