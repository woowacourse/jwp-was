package webserver.controller.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpRequestParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);
    private static final String BODY_KEY_VALUE_DELEMETER = "=";
    private static final String BODY_FIELD_SEPERATE_DELEMETER = "&";
    private static final String HEADER_LINE_SEPERATE_DELEMETER = " ";
    private static final String HEADER_FIELD_DELEMETER = ": ";

    public static String[] parseRequestLine(BufferedReader bufferedReader) throws IOException {
        String requestLine = bufferedReader.readLine();
        return requestLine.split(HEADER_LINE_SEPERATE_DELEMETER);
    }

    public static HashMap<String, String> parseBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        HashMap<String, String> bodyFields = new HashMap<>();
        String requestParams = IOUtils.readData(bufferedReader, contentLength);
        requestParams = URLDecoder.decode(requestParams, StandardCharsets.UTF_8);
        String[] splitedParams = requestParams.split(BODY_FIELD_SEPERATE_DELEMETER);

        for (String splitedParam : splitedParams) {
            String[] queryParam = splitedParam.split(BODY_KEY_VALUE_DELEMETER);
            bodyFields.put(queryParam[0], queryParam[1]);
        }

        return bodyFields;
    }

    public static HashMap<String, String> parseHeaderFields(BufferedReader bufferedReader) throws IOException {
        String headerField = bufferedReader.readLine();
        HashMap<String, String> headerFields = new HashMap<>();
        while (headerField != null && !headerField.equals("") && !headerField.equals("\n")) {
            saveHeaderFiled(headerField.split(HEADER_FIELD_DELEMETER), headerFields);
            logger.debug("HttpHeaderField : {}", headerField);
            headerField = bufferedReader.readLine();
        }
        return headerFields;
    }

    private static void saveHeaderFiled(String[] splitedHeaderLine, HashMap<String, String> headerFields) {
        headerFields.put(splitedHeaderLine[0], splitedHeaderLine[1]);
    }
}
