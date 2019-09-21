package webserver;

import db.DataBase;
import factory.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = br.readLine();
            String[] splitedLine = line.split(" ");

            if (splitedLine[0].equals("GET")) {
                byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates" + splitedLine[1]);
                DataOutputStream dos = new DataOutputStream(out);
                if (splitedLine[1].contains(".html")) {
                    response200Header(dos, bytes.length, "html");
                }
                responseBody(dos, bytes);

                if (splitedLine[1].contains("/user/create?")) {
                    String[] signUpUrl = splitedLine[1].split("\\?");
                    DataBase.addUser(UserFactory.of(URLDecoder.decode(signUpUrl[1], "UTF-8").split("&")));
                }
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }


    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentsType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/" + contentsType + ";charset=utf-8\r\n");
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
