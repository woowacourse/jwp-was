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
import utils.IOUtils;
import utils.StringUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATE_LOCATION = "./templates";
    private static final String STATIC_LOCATION = "./static";

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
                response200Header(dataOutputStream, body.length, "text/html");
                responseBody(dataOutputStream, body);
                return;
            }
            if(line.contains("/user/create")){
                while (!line.contains("Content-Length:")) {
                    line = bufferedReader.readLine();
                }
                int contentLength = Integer.parseInt(line.split("Content-Length: ")[1]);

                while (!line.equals("")) {
                    line = bufferedReader.readLine();
                }

                String parameters = IOUtils.readData(bufferedReader, contentLength);

                String userId = StringUtils.extractParameterValue(parameters, "userId");
                String password = StringUtils.extractParameterValue(parameters, "password");
                String name = StringUtils.extractParameterValue(parameters, "name");
                String email = StringUtils.extractParameterValue(parameters, "email");

                User user = new User(userId, password, name, email);

                response302Header(dataOutputStream);
                return;
            }
            if (requestLocation.endsWith(".css")) {
                byte[] body = FileIoUtils
                    .loadFileFromClasspath(STATIC_LOCATION + requestLocation);
                response200Header(dataOutputStream, body.length, "text/css");
                responseBody(dataOutputStream, body);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dataOutputStream, int lengthOfBodyContent,
        String contentType) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 Found \r\n");
            dataOutputStream.writeBytes("Location: /index.html \r\n");
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
