package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class IOUtils {

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static Stream<String[]> parseParamsSequence(String paramsSequence) {
        return Stream.of(paramsSequence.split("&"))
            .map(param -> param.split("="));
    }

    public static Map<String, String> parseStringToObject(String paramsSequence) {
        Map<String, String> object = new HashMap<>();
        parseParamsSequence(paramsSequence)
            .forEach(pair -> object.put(pair[0], pair[1]));

        return object;
    }
}
