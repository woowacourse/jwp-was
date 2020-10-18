package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BufferedReaderUtils {
    private static final Logger logger = LoggerFactory.getLogger(BufferedReaderUtils.class);
    private static final String COLON_DELIMITER = ": ";

    public static Map<String, String> readHeader(BufferedReader br) throws IOException {
        String line;
        Map<String, String> lines = new HashMap<>();
        while (!StringUtils.isEmpty(line = br.readLine())) {
            logger.debug("request: {}", line);
            lines.put(line.split(COLON_DELIMITER)[0], line.split(COLON_DELIMITER)[1]);
        }
        return lines;
    }
}
