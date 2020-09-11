package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestParser {
    public static final String EMPTY = "";
    private static final String KEY_VALUE_DELIMITER = "=";

    public static List<String> parsingRequestHeader(BufferedReader br) throws IOException {
        List<String> request = new ArrayList<>();
        String line = br.readLine();
        do {
            request.add(line);
            line = br.readLine();
        } while (line != null && !EMPTY.equals(line));
        return request;
    }

    public static Map<String, String> parsingData(String s) {
        return Arrays.stream(s.split("&"))
                .collect(Collectors.toMap(param -> param.split(KEY_VALUE_DELIMITER)[0],
                        param -> param.split(KEY_VALUE_DELIMITER)[1]));
    }
}
