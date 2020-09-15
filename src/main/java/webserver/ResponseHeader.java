package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.UrlUtils;

public class ResponseHeader {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHeader.class);
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private final DataOutputStream dos;

    public ResponseHeader(DataOutputStream dos) {
        this.dos = dos;
    }

    public void createResponse302Header(String location) throws IOException, URISyntaxException {
        byte[] responseFile = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + location);

        try {
            dos.writeBytes("HTTP/1.1 302 Found " + System.lineSeparator());
            dos.writeBytes("Location: " + location + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
            createResponseBody(responseFile);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void createResponse200Header(String resourcePath) throws IOException, URISyntaxException {
        byte[] responseFile;

        if (HttpContentType.isStaticFile(resourcePath)) {
            responseFile = FileIoUtils.loadFileFromClasspath(STATIC_PATH + resourcePath);
        } else {
            responseFile = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + UrlUtils.extractFilePath(resourcePath));

        }
        try {
            dos.writeBytes("HTTP/1.1 200 OK " + System.lineSeparator());
            dos.writeBytes("Content-Type: " + HttpContentType.findContentType(resourcePath) + ";charset=utf-8" + System.lineSeparator());
            dos.writeBytes("Content-Length: " + responseFile.length + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
            createResponseBody(responseFile);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void createResponseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
