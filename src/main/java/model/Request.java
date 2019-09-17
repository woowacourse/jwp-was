package model;

import exception.NotFoundRequestElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Map<String, String> request;
    private String url;

    public Request(BufferedReader bufferedReader) throws IOException {
        request = new HashMap<>();

        String line = bufferedReader.readLine();
        url = line.split(" ")[1];

        while (!"".equals(line)) {
            String key = line.split(":")[0].trim();
            String value = line.split(":")[1].trim();

            request.put(key, value);
            logger.debug("header : {}", line);

            line = bufferedReader.readLine();

            if (line == null) {
                return;
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getRequestElement(String key) {
        String element = request.get(key);

        if (Objects.isNull(element)) {
            throw new NotFoundRequestElementException();
        }

        return element;
    }
}
