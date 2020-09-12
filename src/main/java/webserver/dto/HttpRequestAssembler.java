package webserver.dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.FileNameExtension;
import webserver.handler.RequestHandler;

public class HttpRequestAssembler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String LINE_DELIMITER = " ";
    private static final String HEADER_DELIMITER = ": ";

    public static HttpRequest assemble(BufferedReader br) throws IOException {
        String line = br.readLine();
        logger.debug("request line : {}", line);
        String[] requestLine = line.split(LINE_DELIMITER);

        String httpMethod = extractHttpMethod(requestLine);
        String urlPath = extractUrlPath(requestLine);
        String protocol = extractProtocol(requestLine);
        Map<String, String> headers = extractHeaders(br);
        FileNameExtension fileNameExtension = FileNameExtension.from(urlPath);
        return new HttpRequest(httpMethod, urlPath, protocol, headers, fileNameExtension);
    }

    private static String extractHttpMethod(String[] requestLine) {
        return requestLine[0];
    }

    private static String extractUrlPath(String[] requestLine) {
        return URLDecoder.decode(requestLine[1], StandardCharsets.UTF_8);
    }

    private static String extractProtocol(String[] requestLine) {
        return requestLine[2];
    }

    private static Map<String, String> extractHeaders(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();

        while (true) {
            String header = br.readLine();
            if (header.equals("")) {
                break;
            }
            logger.debug("header : {}", header);
            String[] splitHeader = header.split(HEADER_DELIMITER);
            headers.put(splitHeader[0], splitHeader[1]);
        }
        return headers;
    }
}
