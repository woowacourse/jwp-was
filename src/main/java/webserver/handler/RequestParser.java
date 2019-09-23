package webserver.handler;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {
    private static final String BLANK = "";
    private static final String SEPARATOR = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";

    public static List<String> parse(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();

        String line;
        while (!BLANK.equals(line = bufferedReader.readLine())) {
            lines.add(line);
        }

        lines.add("");

        if (bufferedReader.ready()) {
            lines.add(IOUtils.readData(bufferedReader, getContentLength(lines)));
        }
        return lines;
    }

    private static int getContentLength(List<String> lines) {
        String contentLength = lines.stream()
                .filter(line -> line.contains(CONTENT_LENGTH))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Header 에 Content-Length 가 존재하지 않습니다."));
        return Integer.parseInt(contentLength.split(SEPARATOR)[1]);
    }
}
