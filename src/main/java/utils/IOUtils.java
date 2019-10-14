package utils;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;

public class IOUtils {
    /**
     * @param BufferedReader는 Request Body를 시작하는 시점이어야
     * @param contentLength는  Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    private static final String HEADER_SEPARATOR = ": ";

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static List<String> readData(InputStream in) {
        List<String> lines = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String readData;
        try {
            while (!StringUtils.isEmpty(readData = bufferedReader.readLine())) {
                lines.add(readData);
            }
            if (bufferedReader.ready()) {
                lines.add("");
                lines.add(readData(bufferedReader, getContentLength(lines)));
            }
        } catch (IOException e) {
            e.getMessage();
            throw new IllegalArgumentException();
        }
        return lines;
    }

    private static int getContentLength(List<String> lines) {
        String contentLength = lines.stream()
                .filter(line -> line.contains(CONTENT_LENGTH))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Header 에 Content-Length 가 존재하지 않습니다."));
        return Integer.parseInt(contentLength.split(HEADER_SEPARATOR)[1]);
    }
}
