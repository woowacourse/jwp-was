package webserver.dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.FileNameExtension;
import webserver.handler.RequestHandler;

public class HttpRequestAssembler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String LINE_DELIMITER = " ";
    private static final String HEADER_DELIMITER = ": ";
    private static final String URL_PATH_DELIMITER = "\\?";
    private static final String PARAMETER_VALUE_DELIMITER = "=";
    private static final String PARAMETER_DELIMITER = "&";
    private static final String EMPTY = "";

    public static HttpRequest assemble(BufferedReader br) throws IOException {
        String line = br.readLine();
        logger.debug("request line : {}", line);
        String[] requestLine = line.split(LINE_DELIMITER);

        String httpMethod = extractHttpMethod(requestLine);
        String urlPath = extractUrlPath(requestLine);
        Map<String, String> parameters = extractParameters(requestLine);
        String protocol = extractProtocol(requestLine);
        Map<String, String> headers = extractHeaders(br);
        FileNameExtension fileNameExtension = FileNameExtension.from(urlPath);
        return new HttpRequest(httpMethod, urlPath, parameters, protocol, headers, fileNameExtension
        );
    }

    private static String extractHttpMethod(String[] requestLine) {
        return requestLine[0];
    }

    private static String extractUrlPath(String[] requestLine) {
        String decode = URLDecoder.decode(requestLine[1], StandardCharsets.UTF_8);
        return splitUrlParameters(decode)[0];
    }

    private static String[] splitUrlParameters(String decode) {
        return decode.split(URL_PATH_DELIMITER);
    }

    private static Map<String, String> extractParameters(String[] requestLine) {
        String decode = URLDecoder.decode(requestLine[1], StandardCharsets.UTF_8);
        String[] urlParameters = splitUrlParameters(decode);
        if (urlParameters.length == 1) {
            return new HashMap<>();
        }

        String parameters = urlParameters[1];
        if (Objects.isNull(parameters) || parameters.isEmpty()) {
            return new HashMap<>();
        }
        return makeParameters(parameters);
    }

    private static Map<String, String> makeParameters(String parameters) {
        return Arrays.stream(parameters.split(PARAMETER_DELIMITER))
            .filter(parameter -> parameter.contains(PARAMETER_VALUE_DELIMITER))
            .map(parameter -> parameter.split(PARAMETER_VALUE_DELIMITER))
            .collect(Collectors
                .toMap(parameter -> parameter[0], HttpRequestAssembler::makeParameterValue));
    }

    private static String makeParameterValue(String[] parameter) {
        if (parameter.length == 1) {
            return EMPTY;
        }
        return parameter[1];
    }

    private static String extractProtocol(String[] requestLine) {
        return requestLine[2];
    }

    private static Map<String, String> extractHeaders(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();

        while (true) {
            String header = br.readLine();
            if (header.equals(EMPTY)) {
                break;
            }
            logger.debug("header : {}", header);
            String[] splitHeader = header.split(HEADER_DELIMITER);
            headers.put(splitHeader[0], splitHeader[1]);
        }
        return headers;
    }
}
