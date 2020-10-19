package model;

import static utils.FileIoUtils.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpGetService extends AbstractHttpService {
    private static final Logger logger = LoggerFactory.getLogger(HttpGetService.class);
    private static final String FILE_PATH = "./templates";

    public HttpGetService(HttpHeader header, HttpBody body) {
        super(header, body);
    }

    @Override
    public void doService(OutputStream out) throws URISyntaxException, IOException {
        DataOutputStream dos = new DataOutputStream(out);
        String classPath = FILE_PATH + header.getRequestURI().getUri();
        logger.debug(classPath);

        byte[] body = loadFileFromClasspath(classPath);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
