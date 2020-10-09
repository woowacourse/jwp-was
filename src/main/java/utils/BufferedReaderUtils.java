package utils;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BufferedReaderUtils {
    private static final String COLON_DELIMITER = ": ";

    public static Map<String, String> readHeader(BufferedReader br) throws IOException {
        String line;
        Map<String, String> lines = new HashMap<>();
        while (!StringUtils.isEmpty(line = br.readLine())) {
            lines.put(line.split(COLON_DELIMITER)[0], line.split(COLON_DELIMITER)[1]);
        }
        return lines;
    }
}
