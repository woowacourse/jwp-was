package webserver.user;

import webserver.controller.AbstractController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class UserController extends AbstractController {
    public HttpResponse doPost(HttpRequest httpRequest) {
//        UserService.join(httpRequest.getBody());
//        HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();
//        httpResponseBuilder.httpStatus(HttpStatus.FOUND);
//        httpResponseBuilder.location("http://localhost:8080/index.html");
//        return httpResponseBuilder.build();
        return null;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.join(httpRequest.getBody());
        httpResponse.redirect("http://localhost:8080/index.html");
    }
}
