package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import model.general.Header;

public class HeaderUtils {

    private static final String EMPTY_LINE = "";
    private static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static Map<Header, String> generateHeaders(BufferedReader bufferedReader)
        throws IOException {
        Map<Header, String> headers = new HashMap<>();
        String line = bufferedReader.readLine();

        while (Objects.nonNull(line) && !EMPTY_LINE.equals(line)) {
            Header key = Header.of(line.split(HEADER_KEY_VALUE_SEPARATOR)[KEY_INDEX]);
            String value = line.split(HEADER_KEY_VALUE_SEPARATOR)[VALUE_INDEX];

            headers.put(key, value);
            line = bufferedReader.readLine();
        }

        return headers;
    }
}
