package utils;

public class HttpRequestUtils {

    public static final int REQUEST_URI_LENGTH = 3;
    public static final int REQUEST_URI_PATH = 1;
    public static final String URL_NOT_EXISTS_MESSAGE = "요청에 url이 존재하지 않습니다";

    public static String parseURLPath(String line) {
        String[] splitedLine = line.split(" ");
        if (splitedLine.length != REQUEST_URI_LENGTH) {
            throw new IllegalArgumentException(URL_NOT_EXISTS_MESSAGE);
        }
        return splitedLine[REQUEST_URI_PATH];
    }
}
