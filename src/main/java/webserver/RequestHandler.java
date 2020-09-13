package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import utils.IOUtils;
import utils.RequestUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final int URI_INDEX = 1;

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = bufferedReader.readLine();

            if(line == null) {
                return;
            }
            String[] request = RequestUtils.separateUrl(line);
            String uri = request[URI_INDEX];
            HashMap<String, String> requestUrl = new HashMap<>();
            while(!"".equals(line)) {
                logger.debug("header Line: {} " + line);
                String[] headerToken = line.split(": ");
                if(headerToken.length == 2) {
                    requestUrl.put(headerToken[0], headerToken[1]);
                }
                line = bufferedReader.readLine();
            }
            if("POST".equals(request[0])) {
                String userInfoUrl = IOUtils.readData(bufferedReader, Integer.parseInt(requestUrl.get("Content-Length")));
                RequestUtils.signIn(request, userInfoUrl);
                logger.debug("userInfo: {}" +userInfoUrl);
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos);
            } else {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates/" + uri);
                DataOutputStream dos = new DataOutputStream(out);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }


        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
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

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html\r\n");
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
