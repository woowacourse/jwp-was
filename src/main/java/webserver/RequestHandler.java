package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import web.HttpRequest;
import web.HttpResponse;
import web.ResourceMatcher;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = new HttpRequest(br);
            HttpResponse httpResponse = new HttpResponse(dos);

            if (httpRequest.isStaticFileRequest()) {
                handleStaticFileRequest(httpRequest, httpResponse);
            } else {
                handleAPIRequest(httpRequest, httpResponse);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void handleStaticFileRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        String filePath = ResourceMatcher.findBaseDirectory(path) + path;
        byte[] staticFile = FileIoUtils.loadFileFromClasspath(filePath);

        httpResponse.response200Header(ResourceMatcher.findContentType(path), staticFile.length);
        httpResponse.responseBody(staticFile);
    }

    private void handleAPIRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getMethod().equals("POST") && httpRequest.getPath().equals("/user/create")) {
            Map<String, String> formData = httpRequest.getFormData();
            User user = new User(formData.get("userId"), formData.get("password"), formData.get("name"), formData.get("email"));
            DataBase.addUser(user);

            httpResponse.response302Header("/index.html");
        }
    }
}
