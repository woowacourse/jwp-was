package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestUtils {
    public static final String ROOT_TEMPLATE_FILE_PATH = "./templates";
    private static final String ROOT_STATIC_FILE_PATH = "./static";
    private static final String DEFAULT_INDEX = "/index.html";
    private static final String ROOT = "/";
    private static final String REQUEST_SPLIT_TOKEN = " ";
    private static final int DEFAULT_REQUEST_LENGTH = 3;
    private static final String WRONG_REQUEST_FORM_MESSAGE = "잘못된 형식의 요청입니다.";
    private static final String PARAMS_TOKEN = "&";
    private static final String KEY_VALUE_TOKEN = "=";

    public static String generateTemplateFilePath(String absPath) {
        if (ROOT.equals(absPath)) {
            return ROOT_TEMPLATE_FILE_PATH + DEFAULT_INDEX;
        }
        return ROOT_TEMPLATE_FILE_PATH + absPath;
    }

    public static String generateStaticFilePath(String absPath) {
        return ROOT_STATIC_FILE_PATH + absPath;
    }

    public static String[] splitRequest(String request) {
        String[] splitRequest = request.split(REQUEST_SPLIT_TOKEN);
        if (splitRequest.length != DEFAULT_REQUEST_LENGTH) {
            throw new IllegalArgumentException(WRONG_REQUEST_FORM_MESSAGE);
        }
        return splitRequest;
    }

    public static Map<String, String> parseParamToMap(String params) {
        return Arrays.stream(params.trim().split(PARAMS_TOKEN))
            .map(s -> s.split(KEY_VALUE_TOKEN, 2))
            .collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : ""));
    }

}
