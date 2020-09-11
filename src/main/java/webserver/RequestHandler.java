package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.FileNotExitsException;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug(
            "New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(),
            connection.getPort()
        );

        try (InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            logger.debug("request line : {}", line);
            String filePath = extractFilePath(line);

            while (!line.equals("")) {
                line = br.readLine();
                logger.debug("header : {}", line);
            }
            loadFile(out, filePath);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void loadFile(OutputStream out, String filePath)
        throws IOException, URISyntaxException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            try {
                byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
                response200Header(dos, body.length);
                responseBody(dos, body);
            } catch (FileNotExitsException e) {
                byte[] body = e.getMessage().getBytes(StandardCharsets.UTF_8);
                response404Header(dos, body.length);
                responseBody(dos, body);
            }
        }
    }

    private String extractFilePath(String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        return URLDecoder.decode(splitRequestLine[1], StandardCharsets.UTF_8);
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

    private void response404Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 404 NOT FOUND \r\n");
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
            dos.writeUTF(Arrays.toString(body));
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
