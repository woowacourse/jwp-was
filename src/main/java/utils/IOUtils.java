package utils;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.exceptions.IllegalHttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    private static final Logger log = LoggerFactory.getLogger(IOUtils.class);

    public static String readData(BufferedReader br, int contentLength) {
        try {
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            return String.copyValueOf(body);

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }

    public static List<String> readData(BufferedReader bufferedReader) {
        List<String> lines = new ArrayList<>();
        String readData;
        try {
            while (!StringUtils.isEmpty((readData = bufferedReader.readLine()))) {
                lines.add(readData);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalHttpRequestException(e.getMessage());
        }
        return lines;
    }

    public static String readFirstLine(BufferedReader bufferedReader) {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }
}
