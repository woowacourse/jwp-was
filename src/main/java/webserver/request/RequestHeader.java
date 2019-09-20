package webserver.request;

import java.util.Map;

public class RequestHeader {
    private static final String HEADER_SPLIT_TOKEN = ": ";
    private static final String WRONG_REQUEST_HEADER_FORM_MESSAGE = "잘못된 헤더 요청입니다.";
    private static final int HEADER_LENGTH = 2;
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;

    private Map<String, String> header;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
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
