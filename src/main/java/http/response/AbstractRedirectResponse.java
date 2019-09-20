package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class AbstractRedirectResponse implements Response {
    private static final Logger log = LoggerFactory.getLogger(AbstractResponse.class);

    @Override
    public void doResponse(DataOutputStream dos) {
        response300Header(dos);
    }

    private void response300Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: http://localhost:8080/" + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
