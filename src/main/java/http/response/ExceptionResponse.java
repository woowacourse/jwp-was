package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class ExceptionResponse implements Response {
    private static final Logger log = LoggerFactory.getLogger(ExceptionResponse.class);

    @Override
    public void doResponse(DataOutputStream dos) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("../resources/templates/error.html");
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            dos.writeBytes("Content-Type: text/html" + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
