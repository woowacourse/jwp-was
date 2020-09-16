package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.RequestEntity;
import http.response.ResponseEntity;
import webserver.requestmapping.RequestMapping;
import webserver.requestmapping.RequestMappingStorage;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            RequestEntity requestEntity = RequestEntity.from(bufferedReader);

            RequestMapping matchingMapping = RequestMappingStorage.findMatchingMapping(requestEntity);
            ResponseEntity response = matchingMapping.generateResponse(requestEntity);

            String responseString = response.convertToString();
            dos.write(responseString.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
