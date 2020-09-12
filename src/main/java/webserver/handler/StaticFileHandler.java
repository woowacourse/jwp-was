package webserver.handler;

import static webserver.Kind.STATIC_FILE;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.FileNotExitsException;
import webserver.dto.HttpRequest;

public class StaticFileHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    protected static void loadStaticFile(OutputStream out, HttpRequest httpRequest)
        throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            try {
                byte[] body = FileIoUtils
                    .loadFileFromClasspath(httpRequest.getUrlPath(), STATIC_FILE.getDirectory());
                response200Header(dos, body.length);
                responseBody(dos, body);
            } catch (URISyntaxException | FileNotExitsException e) {
                byte[] body = e.getMessage().getBytes(StandardCharsets.UTF_8);
                response404Header(dos, body.length);
                responseBody(dos, body);
            }
        }
    }

    private static void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void response404Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 404 NOT FOUND \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeUTF(Arrays.toString(body));
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
