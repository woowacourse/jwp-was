package webserver;

import db.DataBase;
import model.Request;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ExtractInformationUtils;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private String classPath;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            Request request = new Request(new BufferedReader(new InputStreamReader(in)));
            String url = request.getUrl();

            String extension = url.substring(url.lastIndexOf(".") + 1);
            String type = "text/html";

            if (extension.equals("html") || extension.equals("ico")) {
                classPath = "./templates" + url;
            } else if (extension.equals("css")) {
                classPath = "./static" + url;
                type = "text/css";
            } else {
                classPath = "./static" + url;
            }

            Response response = new Response(dos, classPath);
            if (url.contains("/user/create")) {
                saveUser(request.getBody());
                response.response300(request.getRequestElement("Origin") + "/index.html");
            }
            response.response200(type);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void saveUser(String url) {
        Map<String, String> userInfo = ExtractInformationUtils.extractInformation(url);
        User user = new User(userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"), userInfo.get("email"));
        DataBase.addUser(user);
    }
}
