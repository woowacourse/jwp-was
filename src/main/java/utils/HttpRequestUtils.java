package utils;

public class HttpRequestUtils {

    private static final int REQUEST_URI_LENGTH = 3;
    private static final int REQUEST_ABS_PATH = 1;
    private static final String URL_NOT_EXISTS_MESSAGE = "요청에 url이 존재하지 않습니다";
    public static final String ROOT_FILE_PATH = "./templates";
    private static final String DEFAULT_INDEX = "/index.html";
    private static final String ROOT = "/";
    private static final String REQUEST_URI_SPLIT_TOKEN = " ";

    public static String generateFilePath(String absPath) {
        if (ROOT.equals(absPath)) {
            return ROOT_FILE_PATH + DEFAULT_INDEX;
        }
        return ROOT_FILE_PATH + absPath;
    }

    public static String parseAbsPathAndQuery(String line) {
        String[] splitLine = line.split(REQUEST_URI_SPLIT_TOKEN);
        if (splitLine.length != REQUEST_URI_LENGTH) {
            throw new IllegalArgumentException(URL_NOT_EXISTS_MESSAGE);
        }
        return splitLine[REQUEST_ABS_PATH];
    }
}
