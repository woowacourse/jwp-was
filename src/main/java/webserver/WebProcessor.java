package webserver;

import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class WebProcessor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WebProcessor.class);

    private Socket connection;

    public WebProcessor(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        webStart(new HttpProcess());
    }

    private void webStart(HttpProcess httpProcess) {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            webResponse(out, httpProcess.create(br));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void webResponse(OutputStream out, HttpResponse httpResponse) {
        try {
            DataOutputStream dos = new DataOutputStream(out);

            dos.writeBytes(httpResponse.toString());
            dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
