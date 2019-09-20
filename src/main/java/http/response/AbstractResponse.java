package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class AbstractResponse implements Response {
    private static final Logger log = LoggerFactory.getLogger(AbstractResponse.class);
    private final String path;

    AbstractResponse(String path) {
        this.path = path;
    }

    @Override
    public void doResponse(DataOutputStream dos, String contentType) throws IOException, URISyntaxException {
        if(contentType.contains("Location: ")) {
            response300Header(dos, contentType);
        } else{
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response300Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes(location + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    protected void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
