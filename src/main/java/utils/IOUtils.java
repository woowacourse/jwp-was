package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOUtils {

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static Map<String, String> parseStringToObject(String paramsSequence) {
        return Stream.of(paramsSequence.split("&"))
            .map(param -> param.split("="))
            .collect(Collectors.toMap(pair -> pair[0], x -> x[1]));
    }

    public static String extractExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    public static void writeWithLineSeparator(DataOutputStream dos, String value) throws IOException {
        StringBuilder builder = new StringBuilder(value);
        StringBuilder withLineSeparator = builder.append(System.lineSeparator());

        dos.writeBytes(withLineSeparator.toString());
    }
}
