package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {

    private static final String WHOLE_URL_DELIMITER = " ";
    private static final String PATH_PARAM_DELIMITER = "\\?";
    private static final String PARAM_BUNDLE_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String HEADER_DELIMITER = ": ";

    public static String extractHeader(BufferedReader bufferedReaderRequest) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        String line = bufferedReaderRequest.readLine();
        while (line != null && !line.isEmpty()) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
            line = bufferedReaderRequest.readLine();
        }

        return stringBuilder.toString();
    }

    public static String extractHeaderValue(String requestHeader, String key) {
        String[] headers = requestHeader.split(System.lineSeparator());
        for (String header : headers) {
            String[] keyValue = header.split(HEADER_DELIMITER, 2);
            if (keyValue[0].equals(key)) {
                return keyValue[1];
            }
        }
        throw new IllegalArgumentException("키에 해당하는 헤더 값이 존재 하지 않습니다");
    }

    public static String extractBody(BufferedReader request, String contentLength) throws IOException {
        return IOUtils.readData(request, Integer.parseInt(contentLength));
    }

    public static String extractWholeUrl(String requestHeader) {
        String wholeUrl = requestHeader.split(WHOLE_URL_DELIMITER)[1];
        try {
            return URLDecoder.decode(wholeUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    public static String extractPath(String wholeUrl) {
        return wholeUrl.split(PATH_PARAM_DELIMITER)[0];
    }

    public static Map<String, String> extractParams(String wholeUrl) {
        validateParams(wholeUrl);

        Map<String, String> output = new HashMap<>();
        String paramBundle = wholeUrl.split(PATH_PARAM_DELIMITER)[1];
        String[] params = paramBundle.split(PARAM_BUNDLE_DELIMITER);
        for (String param : params) {
            String[] keyValues = param.split(KEY_VALUE_DELIMITER);
            output.put(keyValues[0], keyValues[1]);
        }
        return output;
    }

    private static void validateParams(String wholeUrl) {
        if (wholeUrl.split(PATH_PARAM_DELIMITER).length != 2) {
            throw new IllegalArgumentException("쿼리 파라미터가 존재하지 않습니다");
        }
    }
}
