package webserver;

import db.DataBase;
import model.Request;
import model.RequestParser;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            DataOutputStream dos = new DataOutputStream(out);
            RequestParser requestParser = new RequestParser(in);
            Request request = new Request(requestParser.getHeaderInfo(),requestParser.getParameter());
            String url = request.getUrl();

            String extension = url.substring(url.lastIndexOf(".") + 1);

            String classPath = "./templates" + url;

            if (!"html".equals(extension)) {
                classPath = "./static" + url;
            }

            Response response = new Response(dos, classPath);
            if (url.contains("/user/create")) {
                saveUser(request);
                response.response300(request.getHeader("Origin") + "/index.html");
                return;
            }
            response.response200();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void saveUser(Request request) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        DataBase.addUser(user);
    }
}

