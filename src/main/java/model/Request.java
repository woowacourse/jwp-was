package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private List<String> request;

    public String getUrl() {
        return request.get(0).split(" ")[1];
    }

    public void print(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = bufferedReader.readLine();

        request = new ArrayList<>();

        while (!"".equals(line)) {
            request.add(line);
            logger.debug("header : {}", line);

            line = bufferedReader.readLine();

            if (line == null) {
                return;
            }
        }
    }
}
