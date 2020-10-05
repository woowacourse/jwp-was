package utils;

import static model.ContentType.HTML;
import static model.ContentType.ICO;

import java.io.BufferedReader;
import java.io.IOException;
import model.ContentType;
import model.Method;
import model.Request;

public class StringUtils {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int METHOD_INDEX = 0;
    private static final int LOCATION_INDEX = 1;
    private static final int VALID_REQUEST_SIZE = 3;
    private static final String KEY_VALUE_LINK_LETTER = "=";
    private static final String PARAMETER_DELIMITER = "&";
    private static final int VALUE_RIGHT_SIDE_INDEX = 1;
    private static final int VALUE_INDEX = 0;
    private static final String TEMPLATE_LOCATION = "./templates";
    private static final String STATIC_LOCATION = "./static";
    private static final String EXTENSION_DELIMITER = "\\.";
    private static final int NO_EXTENSION_SIZE = 1;
    private static final int SIZE_CORRECTION_NUMBER = 1;
    private static final String EXTENSION_LETTER = ".";
    private static final int NO_PARAMETER_SIZE = 1;
    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final int PARAMETERS_RIGHT_SIDE_INDEX = 1;
    private static final int PARAMETERS_INDEX = 0;
    private static final int CONTENT_LENGTH_VALUE_INDEX = 1;
    private static final String EMPTY_LIME = "";

    public static String extractRequestMethod(String line) {
        String[] requestLine = line.split(REQUEST_LINE_DELIMITER);
        if (requestLine.length != VALID_REQUEST_SIZE) {
            throw new IllegalArgumentException("Invalid Request");
        }
        return requestLine[METHOD_INDEX];
    }

    public static String extractRequestLocation(String line) {
        String[] requestLine = line.split(REQUEST_LINE_DELIMITER);
        if (requestLine.length != VALID_REQUEST_SIZE) {
            throw new IllegalArgumentException("Invalid Request");
        }
        return requestLine[LOCATION_INDEX];
    }

    public static String extractParameterValue(String params, String key) {
        return params
            .split(key + KEY_VALUE_LINK_LETTER)[VALUE_RIGHT_SIDE_INDEX]
            .split(PARAMETER_DELIMITER)[VALUE_INDEX];
    }

    public static String generatePath(Request request) {
        ContentType contentType = request.getContentType();
        String location = request.getLocation();

        if (HTML.equals(contentType) || ICO.equals(contentType)) {
            return TEMPLATE_LOCATION + location;
        }
        return STATIC_LOCATION + location;
    }

    public static String extractExtension(String line) {
        String location = line.split(REQUEST_LINE_DELIMITER)[LOCATION_INDEX];
        String[] sections = location.split(EXTENSION_DELIMITER);

        if (sections.length == NO_EXTENSION_SIZE) {
            return null;
        }
        return EXTENSION_LETTER + sections[sections.length - SIZE_CORRECTION_NUMBER];
    }

    public static String getParameters(String line, String requestMethod,
        BufferedReader bufferedReader) throws IOException {
        Method method = Method.of(requestMethod);

        if (method.equals(Method.GET)) {
            return extractGetParameters(line);
        }
        if (method.equals(Method.POST)) {
            return extractPostParameters(bufferedReader);
        }
        throw new IllegalArgumentException("Not Implemented Method");
    }

    private static String extractGetParameters(String line) {
        String location = extractRequestLocation(line);
        String[] sections = location.split(QUERY_STRING_DELIMITER);
        if (sections.length == NO_PARAMETER_SIZE) {
            return null;
        }
        return sections[PARAMETERS_RIGHT_SIDE_INDEX]
            .split(REQUEST_LINE_DELIMITER)[PARAMETERS_INDEX];
    }

    private static String extractPostParameters(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while (!line.contains("Content-Length:")) {
            line = bufferedReader.readLine();
        }
        int contentLength
            = Integer.parseInt(line.split("Content-Length: ")[CONTENT_LENGTH_VALUE_INDEX]);

        while (!line.equals(EMPTY_LIME)) {
            line = bufferedReader.readLine();
        }
        return IOUtils.readData(bufferedReader, contentLength);
    }
}
