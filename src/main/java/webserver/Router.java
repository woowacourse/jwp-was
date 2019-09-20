package webserver;

import controller.Controller;
import controller.FileController;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Router {
    private static Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/user/create", new UserController());
    }

    public static HttpResponse route(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String requestUrl = httpRequest.getPath();

            FileController fileController = new FileController();
            HttpResponse response = fileController.service(httpRequest, httpResponse);
            if (response == null) {
                Controller controller = Optional.ofNullable(controllers.get(requestUrl)).orElseThrow(BadRequestException::new);
                return controller.service(httpRequest, httpResponse);
            }
            return null; // TODO: 2019-09-20 없는 요청
        } catch (Exception e) {
            // TODO: 2019-09-20 400해야함
//            return httpResponse.addHeader();
            return null; // TODO: 2019-09-20  
        }
    }
}
