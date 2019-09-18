package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            HttpStartLine startLine = new HttpStartLine(br.readLine());
            HttpRequestHeader httpRequestHeader = new HttpRequestHeader(br);

            if (startLine.isGet()) {
                if (startLine.hasParameters()) {
                    Map<String, String> userParams = startLine.getParameters();


                    User user = new User(userParams.get("userId"), userParams.get("password"), userParams.get("name"), userParams.get("email"));
                    DataBase.addUser(user);
                }
                String file = startLine.getSource();
                logger.debug("file : {}", file);


                if (startLine.getContentType().equals("html")) {
                    byte[] body = FileIoUtils.loadFileFromClasspath("./templates/" + file);
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                } else {
                    byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + file);
                    responseContent(dos, body.length, startLine.getContentType());
                    responseBody(dos, body);
                }
            }

            if (startLine.isPost()) {

                HttpRequestBody httpRequestBody = new HttpRequestBody(br, httpRequestHeader.getContentLength());
                User user = new User(httpRequestBody.get("userId"), httpRequestBody.get("password"), httpRequestBody.get("name"), httpRequestBody.get("email"));
                DataBase.addUser(user);
                response302Header(dos, httpRequestHeader.getHost(), "/index.html");
            }


        } catch (IOException |
                URISyntaxException e) {
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

    private void responseContent(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/" + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String host, String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: http://" + host + path + "\r\n");
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
