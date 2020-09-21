package webserver.user;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseBuilder;
import webserver.http.response.HttpStatus;

public class UserController {
    public static HttpResponse doPost(HttpRequest httpRequest) {
        UserService.join(httpRequest.getBody());
        HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();
        httpResponseBuilder.httpStatus(HttpStatus.FOUND);
        httpResponseBuilder.location("http://localhost:8080/index.html");
        return httpResponseBuilder.build();
    }
}
