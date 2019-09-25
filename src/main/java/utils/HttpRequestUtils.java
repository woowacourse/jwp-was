package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestUtils {
    public static final String ROOT_TEMPLATE_FILE_PATH = "./templates";
    private static final String ROOT_STATIC_FILE_PATH = "./static";
    private static final String PARAMS_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String ABS_PATH_QUERY_DELIMITER = "\\?";
    private static final int ABS_PATH_QUERY_DELIMITER_LENGTH = 2;
    private static final int QUERY_STRING_INDEX = 1;

    public static String generateTemplateFilePath(String absPath) {
        return ROOT_TEMPLATE_FILE_PATH + absPath;
    }

    public static String generateStaticFilePath(String absPath) {
        return ROOT_STATIC_FILE_PATH + absPath;
    }

    public static Map<String, String> parseParamToMap(String params) {
        return Arrays.stream(params.trim().split(PARAMS_DELIMITER))
            .map(param -> param.split(KEY_VALUE_DELIMITER, 2))
            .collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : ""));
    }

    public static Map<String, String> parseQueryString(String absPathAndQuery) {
        String[] splitAbsPath = parseAbsPathAndQuery(absPathAndQuery);
        if (splitAbsPath.length != ABS_PATH_QUERY_DELIMITER_LENGTH) {
            return new HashMap<>();
        }
        return HttpRequestUtils.parseParamToMap(splitAbsPath[QUERY_STRING_INDEX]);
    }

    public static String[] parseAbsPathAndQuery(String absPathAndQuery) {
        return absPathAndQuery.split(ABS_PATH_QUERY_DELIMITER);
    }

    public static boolean isLineEmpty(String line){
        return !"".equals(line) && null != line;
    }
}
