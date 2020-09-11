package utils;

import static web.HeaderProperty.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestParser {
    public static final String EMPTY = "";
    private static final String KEY_VALUE_DELIMITER = "=";
    public static final String HEADER_DATA_DELIMITER = ": ";
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    public static Map<String, String> parsingRequestHeader(BufferedReader br) throws IOException {
        Map<String, String> request = new HashMap<>();
        request.put(REQUEST_LINE.getName(), br.readLine());
        String line = br.readLine();
        while (line != null && !EMPTY.equals(line)) {
            String[] headerData = line.split(HEADER_DATA_DELIMITER);
            request.put(headerData[KEY_INDEX], headerData[VALUE_INDEX]);
            line = br.readLine();
        }
        return request;
    }

    public static Map<String, String> parsingData(String s) {
        return Arrays.stream(s.split("&"))
                .collect(Collectors.toMap(param -> param.split(KEY_VALUE_DELIMITER)[0],
                        param -> param.split(KEY_VALUE_DELIMITER)[1]));
    }
}
