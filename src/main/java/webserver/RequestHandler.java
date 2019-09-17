package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.MimeTypesUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            List<String> lines = new ArrayList<>();
            String line = br.readLine();
            while (!"".equals(line)) {
                if (line == null) {
                    break;
                }
                lines.add(line);
                line = br.readLine();
            }
            logger.info(String.join("\n", lines));

            String[] tokens = lines.get(0).split(" ");
            String path = tokens[1];
            byte[] body = FileIoUtils.loadFileFromClasspath(path);

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, MimeTypesUtils.getMimeType(path), body.length);
            responseBody(dos, body);
            dos.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void response200Header(DataOutputStream dos, String extension, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            logger.info("extension : " + extension);
            dos.writeBytes("Content-Type: " + extension + ";charset=utf-8\r\n");
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
