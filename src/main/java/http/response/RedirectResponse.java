package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class RedirectResponse implements Response {
    private static final Logger log = LoggerFactory.getLogger(RedirectResponse.class);

    @Override
    public void doResponse(DataOutputStream dos) throws IOException, URISyntaxException {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: http://localhost:8080/index.html" + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
