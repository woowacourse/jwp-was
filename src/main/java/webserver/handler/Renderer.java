package webserver.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Renderer {
    private static final Logger logger = LoggerFactory.getLogger(Renderer.class);

    public static void renderer(DataOutputStream dos, List<String> responses) {
        try {
            for (String response : responses) {
                dos.write(response.getBytes());
            }
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
