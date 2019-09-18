package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private static final String HEADER_SPLIT_TOKEN = ": ";
    private static final String WRONG_REQUEST_HEADER_FORM_MESSAGE = "잘못된 헤더 요청입니다.";
    private static final int HEADER_LENGTH = 2;
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;

    private Map<String, String> header;

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        header = new HashMap<>();
        while (!"".equals(line) && null != line) {
            generateHeader(line);
            line = bufferedReader.readLine();
        }
    }

    private void generateHeader(String line) {
        String[] splitLine = line.split(HEADER_SPLIT_TOKEN);
        if (splitLine.length != HEADER_LENGTH) {
            throw new IllegalArgumentException(WRONG_REQUEST_HEADER_FORM_MESSAGE);
        }
        header.put(splitLine[HEADER_KEY_INDEX], splitLine[HEADER_VALUE_INDEX]);
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
            "header=" + header +
            '}';
    }
}
