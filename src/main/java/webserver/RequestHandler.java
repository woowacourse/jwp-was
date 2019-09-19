package webserver;

import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.MimeTypesUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

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
            HttpRequest httpRequest = new HttpRequest(in);
            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse(dos);

            if (httpRequest.isFileRequest()) {
                byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getUri());

                httpResponse.setStatus(200);
                httpResponse.addHeader("Content-Type", MimeTypesUtils.getMimeType(httpRequest.getUri()));
                httpResponse.setBody(body);
                httpResponse.send();
                dos.close();
                return;
            }

            if (httpRequest.getUri().equals("/user/create")) {
                User user = new User(
                        httpRequest.getRequestBody("userId"),
                        httpRequest.getRequestBody("password"),
                        httpRequest.getRequestBody("name"),
                        httpRequest.getRequestBody("email"));
                logger.info(user.toString());

                httpResponse.setStatus(200);
                httpResponse.send();
                dos.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
