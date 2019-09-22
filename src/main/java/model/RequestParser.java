package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ExtractInformationUtils;
import utils.IOUtils;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String METHOD = "Method";
    private static final String QUERY_STRING_SEPARATOR = "?";
    private static final String EMPTY_STRING = "";
    private static final String SEPARATOR = ": ";
    private static final int NEXT_INT = 1;

    private Map<String, String> header;
    private Map<String, String> parameter;

    public RequestParser(InputStream in) throws IOException {
        header = new HashMap<>();
        parameter = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        parseRequest(bufferedReader);
    }

    private void parseRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        header.put(METHOD, line);
        logger.info("header : {}", line);

        while (!EMPTY_STRING.equals(line)) {
            line = bufferedReader.readLine();

            if (line == null || line.equals("")) {
                processPostRequest(bufferedReader);
                return;
            }

            separate(line);

            logger.info("header : {}", line);
            processQueryString();
        }
    }

    private void separate(String line) {
        String key = line.split(SEPARATOR)[0].trim();
        String value = line.split(SEPARATOR)[1].trim();

        header.put(key, value);
    }

    private void processPostRequest(BufferedReader bufferedReader) throws IOException {
        if (header.get(METHOD).contains(HttpMethod.POST.name())) {
            String body = IOUtils.readData(bufferedReader, Integer.parseInt(header.get(CONTENT_LENGTH)));
            parameter.putAll(ExtractInformationUtils.extractInformation(body));
        }
    }

    private void processQueryString() {
        String method = header.get(METHOD);

        if (method.contains(QUERY_STRING_SEPARATOR)) {
            String url = method.split(" ")[1];
            Map<String, String> queryParams = ExtractInformationUtils
                    .extractInformation(url.substring(url.lastIndexOf(QUERY_STRING_SEPARATOR) + NEXT_INT));
            parameter.putAll(queryParams);
        }
    }

    public Map<String, String> getHeaderInfo() {
        return header;
    }

    public Map<String, String> getParameter() {
        return parameter;
    }
}
