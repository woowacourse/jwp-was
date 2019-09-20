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
    private static final String POST = "POST";
    private static final String QUERY_STRING_SEPARATOR = "?";

    private Map<String, String> header = new HashMap<>();
    private Map<String, String> parameter;

    public RequestParser(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        parseRequest(bufferedReader);
    }

    private void parseRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        header.put(METHOD, line);
        logger.info("header : {}", line);

        while (!"".equals(line)) {
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
        String key = line.split(": ")[0].trim();
        String value = line.split(": ")[1].trim();

        header.put(key, value);
    }

    private void processPostRequest(BufferedReader bufferedReader) throws IOException {
        if (header.get(METHOD).contains(POST)) {
            String body = IOUtils.readData(bufferedReader, Integer.parseInt(header.get(CONTENT_LENGTH)));
            parameter = ExtractInformationUtils.extractInformation(body);
        }
    }

    private void processQueryString() {
        String method = header.get(METHOD);

        if (QUERY_STRING_SEPARATOR.equals(method)) {
            parameter = ExtractInformationUtils
                    .extractInformation(method.substring(method.split(" ")[1].indexOf(QUERY_STRING_SEPARATOR) + 1));
        }
    }

    public Map<String, String> getHeaderInfo() {
        return header;
    }

    public Map<String, String> getParameter() {
        return parameter;
    }
}
