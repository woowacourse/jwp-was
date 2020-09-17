package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.StringUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATE_LOCATION = "./templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger
            .debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            String requestLocation = StringUtils.getRequestLocation(line);

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            if (requestLocation.endsWith(".html")) {
                byte[] body = FileIoUtils
                    .loadFileFromClasspath(TEMPLATE_LOCATION + requestLocation);
                response200Header(dataOutputStream, body.length);
                responseBody(dataOutputStream, body);
                return;
            }

            String userId = StringUtils.extractParameterValue(requestLocation, "userId");
            String password = StringUtils.extractParameterValue(requestLocation, "password");
            String name = StringUtils.extractParameterValue(requestLocation, "name");
            String email = StringUtils.extractParameterValue(requestLocation, "email");

            User user = new User(userId, password, name, email);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dataOutputStream, int lengthOfBodyContent) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dataOutputStream, byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
