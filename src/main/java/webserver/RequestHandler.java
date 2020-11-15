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
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import http.HttpRequest;
import http.QueryParameters;
import http.SimpleHttpRequest;
import model.User;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (
            InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = SimpleHttpRequest.of(bufferedReader);
            logger.debug(System.lineSeparator() + httpRequest.toString());
            if (httpRequest.getURI().contains("/user/create")) {
                QueryParameters queryParameters = QueryParameters.of(httpRequest.getQuery());
                User user = new User(
                    queryParameters.getParameter("userId"),
                    queryParameters.getParameter("password"),
                    queryParameters.getParameter("name"),
                    queryParameters.getParameter("email"));
                DataBase.addUser(user);
            } else {
                DataOutputStream dos = new DataOutputStream(outputStream);
                byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getURI());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String extractRequestHeader(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (Objects.nonNull(line = bufferedReader.readLine()) && !line.isEmpty()) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        return stringBuilder.toString();
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
