package webserver;

import controller.Controller;
import controller.FileController;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Router {
    private static final Logger log = LoggerFactory.getLogger(Router.class);
    private static final ResourceRequestResolver resourceRequestResolver;
    private static final FileController fileController;
    private static final Map<String, Controller> controllers;

    static {
        fileController = new FileController();
        controllers = new HashMap<>();
        controllers.put("/user/create", new UserController());
        resourceRequestResolver = new ResourceRequestResolver();
    }

    public static void route(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String path = httpRequest.getPath();
            if (httpRequest.isFileRequest()) {
                fileController.service(httpRequest, httpResponse);
                return;
            }
            Controller controller = Optional.ofNullable(controllers.get(path)).orElseThrow(BadRequestException::new);
            controller.service(httpRequest, httpResponse);
        } catch (BadRequestException e) {
            log.error("Bad Request!!");
            httpResponse.badRequest();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 2019-09-23  
        } catch (URISyntaxException e) {
            e.printStackTrace();
            // TODO: 2019-09-23  
        }
    }

}
