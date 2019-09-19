package webserver;

import controller.HomeController;
import controller.UserController;
import http.request.HttpRequestFactory;
import http.request.Request;
import http.response.Response;
import http.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATE_PATH = "../resources/templates";
    private static final String STATIC_PATH = "../resources/static";

    private Socket connection;
    private UserController userController;
    private HomeController homeController;

    public RequestHandler(Socket connection) {
        this.connection = connection;
        homeController = HomeController.getInstance();
        userController = UserController.getInstance();
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            Request request = HttpRequestFactory.getRequest(br);
            DataOutputStream dos = new DataOutputStream(out);

            request.getParams();
            if(request.getRequestPath().getPath().equals(TEMPLATE_PATH + "/") || request.getRequestPath().getPath().equals(TEMPLATE_PATH+"/index.html")) {
                Response response = ResponseFactory.getResponse(request.getRequestPath().getPath(), "../resources/templates/");
                response.doResponse(dos, "Content-Type: text/html;charset=utf-8");
//                homeController.home(request);
            }

            if(request.getRequestPath().getPath().equals(TEMPLATE_PATH + "/favicon.ico")) {
                Response response = ResponseFactory.getResponse(request.getRequestPath().getPath(), "../resources/templates/");
                response.doResponse(dos, "Content-Type: text/html;charset=utf-8");
            }

            if(request.getRequestPath().getPath().contains(STATIC_PATH + "/css")) {
                Response response = ResponseFactory.getResponse(request.getRequestPath().getPath(), "../resources/static/css/");
                response.doResponse(dos, "Content-Type: text/css");
            }

            if(request.getRequestPath().getPath().contains(STATIC_PATH + "/js")) {
                Response response = ResponseFactory.getResponse(request.getRequestPath().getPath(), "../resources/static/js/");
                response.doResponse(dos, "Content-Type: text/javascript");
            }

            if(request.getRequestPath().getPath().contains(STATIC_PATH + "/fonts")) {
                Response response = ResponseFactory.getResponse(request.getRequestPath().getPath(), "../resources/static/fonts/");
                response.doResponse(dos, "Content-Type: font/opentype");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
