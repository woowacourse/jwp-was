package utils;

import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static void response(DataOutputStream dos, HttpResponse httpResponse) {
        String header = httpResponse.getHeader();
        byte[] body = httpResponse.getBody();
        try {
            dos.writeBytes(header);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
