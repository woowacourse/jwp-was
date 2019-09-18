package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private static final int MAX_BODY_SIZE = 1024 * 100; // 100KB
    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_SPLIT_LIMIT = 2;
    private static final String FIRST_LINE_DELIMITER = " ";

    public static Request parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String[] firstLine = br.readLine().split(FIRST_LINE_DELIMITER);

        Map<String, String> headers = new HashMap<>();
        parseHeader(br, headers);

        char[] buf = new char[MAX_BODY_SIZE];
        readToBuffer(br, buf);

        return new Request(firstLine[0], firstLine[1], headers, new String(buf).getBytes());
    }

    private static void readToBuffer(BufferedReader br, char[] buf) throws IOException {
        if (br.ready()) {
            br.read(buf);
        }
    }

    private static void parseHeader(BufferedReader br, Map<String, String> headers) throws IOException {
        String line;
        line = br.readLine();
        while (hasMoreLine(line)) {
            String[] headerTokens = line.split(HEADER_DELIMITER, HEADER_SPLIT_LIMIT);
            headers.put(headerTokens[0], headerTokens[1]);
            line = br.readLine();
        }
    }

    private static boolean hasMoreLine(String line) {
        return !(line == null || line.isEmpty());
    }
}
