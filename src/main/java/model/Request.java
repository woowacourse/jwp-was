package model;

import exception.NotFoundRequestElementException;
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
import java.util.Objects;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Map<String, String> request = new HashMap<>();
    private Map<String, String> parameter;
    private String body;
    private String method;

    public Request(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = bufferedReader.readLine();
        method = line;
        logger.info("header : {}", line);
        boolean postMethod = line.contains("POST");

        while (!"".equals(line)) {
            line = bufferedReader.readLine();

            if (line == null || line.equals("")) {
                if (postMethod) {
                    body = IOUtils.readData(bufferedReader, Integer.parseInt(request.get("Content-Length")));
                    logger.info(body);
                    parameter = ExtractInformationUtils.extractInformation(body);
                }
                return;
            }

            String key = line.split(": ")[0].trim();
            String value = line.split(": ")[1].trim();

            request.put(key, value);

            logger.info("header : {}", line);
            if ("?".equals(method)) {
                parameter = ExtractInformationUtils.extractInformation(method.substring(method.split(" ")[1].indexOf("?") + 1));
            }
        }
    }

    public String getUrl() {
        return method.split(" ")[1];
    }

    public String getBody() {
        return body;
    }

    public String getHeader(String key) {
        String element = request.get(key);

        if (Objects.isNull(element)) {
            throw new NotFoundRequestElementException();
        }

        return element;
    }

    public String getMethod() {
        return method.split(" ")[0];
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }
}
