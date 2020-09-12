package webserver.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.FileNotExitsException;
import webserver.FileNameExtension;
import webserver.dto.HttpRequest;

public class FileHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_CONTENT_TYPE = "text/plane";
    private static final String CSS_CONTENT_TYPE = "text/css";

    protected static void loadFile(OutputStream out, HttpRequest httpRequest)
        throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            try {
                byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getUrlPath(),
                    httpRequest.getKind().getDirectory());
                String contentType = getContentType(httpRequest);
                response200Header(dos, body.length, contentType);
                responseBody(dos, body);
            } catch (URISyntaxException | FileNotExitsException e) {
                byte[] body = e.getMessage().getBytes(StandardCharsets.UTF_8);
                response404Header(dos, body.length);
                responseBody(dos, body);
            }
        }
    }

    private static String getContentType(HttpRequest httpRequest) throws IOException {
        String contentType = Files.probeContentType(Paths.get(httpRequest.getUrlPath()));
        if (Objects.nonNull(contentType)) {
            return contentType;
        }
        if (httpRequest.getFileNameExtension().equals(FileNameExtension.CSS)) {
            return CSS_CONTENT_TYPE;
        }
        return DEFAULT_CONTENT_TYPE;
    }

    private static void response200Header(DataOutputStream dos, int lengthOfBodyContent,
        String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void response404Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 404 NOT FOUND \r\n");
            dos.writeBytes("Content-Type: " + DEFAULT_CONTENT_TYPE + ";charset=utf-8\r\n");
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
