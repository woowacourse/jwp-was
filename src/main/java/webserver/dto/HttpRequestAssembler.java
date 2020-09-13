package webserver.dto;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;

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
import webserver.HttpMethod;
import webserver.utils.IOUtils;

public class HttpRequestAssembler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestAssembler.class);
    private static final String LINE_DELIMITER = " ";
    private static final String HEADER_DELIMITER = ": ";
    private static final String URL_PATH_DELIMITER = "\\?";
    private static final String PARAMETER_VALUE_DELIMITER = "=";
    private static final String PARAMETER_DELIMITER = "&";
    private static final String EMPTY = "";

    public static HttpRequest assemble(BufferedReader br) throws IOException {
        String line = br.readLine();
        LOGGER.debug("request line : {}", line);
        String[] requestLine = line.split(LINE_DELIMITER);

        String httpMethod = extractHttpMethod(requestLine);
        String urlPath = extractUrlPath(requestLine);
        Map<String, String> parameters = extractQueryString(requestLine);
        String protocol = extractProtocol(requestLine);
        Map<String, String> headers = extractHeaders(br);

        if (HttpMethod.from(httpMethod).hasRequestBody()) {
            addParametersExtractFromBody(br, parameters, getContentLength(headers));
        }
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

    private static Map<String, String> extractQueryString(String[] requestLine) {
        String url = requestLine[1];

        String[] urlParameters = splitUrlParameters(url);
        if (urlParameters.length == 1) {
            return new HashMap<>();
        }

        String queryString = urlParameters[1];
        return extractParameters(queryString);
    }

    private static Map<String, String> extractParameters(String encodedParameter) {
        String decodedParameter = URLDecoder.decode(encodedParameter, StandardCharsets.UTF_8);

        if (Objects.isNull(decodedParameter) || decodedParameter.isEmpty()) {
            return new HashMap<>();
        }
        return makeParameters(decodedParameter);
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
            LOGGER.debug("header : {}", header);
            String[] splitHeader = header.split(HEADER_DELIMITER);
            headers.put(splitHeader[0], splitHeader[1]);
        }
        return headers;
    }

    private static Integer getContentLength(Map<String, String> headers) {
        return headers.keySet().stream()
            .filter(key -> key.equalsIgnoreCase(CONTENT_LENGTH))
            .map(headers::get)
            .map(Integer::parseInt)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("ContentLength가 없습니다."));
    }

    private static void addParametersExtractFromBody(BufferedReader br,
        Map<String, String> parameters, int contentLength) throws IOException {
        String body = IOUtils.readData(br, contentLength);
        LOGGER.debug("body : {}", body);
        Map<String, String> parametersFromBody = extractParameters(body);
        parameters.putAll(parametersFromBody);
    }
}
