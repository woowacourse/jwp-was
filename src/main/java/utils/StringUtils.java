package utils;

import static model.ContentType.HTML;
import static model.ContentType.ICO;

import java.io.BufferedReader;
import java.io.IOException;
import model.ContentType;
import model.Method;
import model.Request;

public class StringUtils {

    private static final int VALID_REQUEST_SIZE = 3;
    private static final String TEMPLATE_LOCATION = "./templates";
    private static final String STATIC_LOCATION = "./static";

    public static String extractRequestMethod(String line) {
        String[] requestLine = line.split(" ");
        if (requestLine.length != VALID_REQUEST_SIZE) {
            throw new IllegalArgumentException("Invalid Request");
        }
        return requestLine[0];
    }

    public static String extractRequestLocation(String line) {
        String[] requestLine = line.split(" ");
        if (requestLine.length != VALID_REQUEST_SIZE) {
            throw new IllegalArgumentException("Invalid Request");
        }
        return requestLine[1];
    }

    public static String extractParameterValue(String params, String key) {
        return params
            .split(key + "=")[1]
            .split("&")[0];
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
        String location = line.split(" ")[1];
        String[] sections = location.split("\\.");

        if (sections.length == 1) {
            return null;
        }
        return "." + sections[sections.length - 1];
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
        return null;
    }

    private static String extractGetParameters(String line) throws IOException {
        String[] sections = line.split("\\?");
        if (sections.length == 1) {
            return null;
        }
        return sections[1].split(" ")[0];
    }

    private static String extractPostParameters(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while (!line.contains("Content-Length:")) {
            line = bufferedReader.readLine();
        }
        int contentLength = Integer.parseInt(line.split("Content-Length: ")[1]);

        while (!line.equals("")) {
            line = bufferedReader.readLine();
        }
        return IOUtils.readData(bufferedReader, contentLength);
    }
}
