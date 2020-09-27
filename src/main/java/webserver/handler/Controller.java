package webserver.handler;

import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

import db.DataBase;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import model.User;
import webserver.request.Request;
import webserver.request.RequestType;
import webserver.request.RequestTypeMatcher;
import webserver.response.Response;

public class Controller {

    private static Map<RequestTypeMatcher, BiFunction<Request, Response, Response>> mapper = new HashMap<>();

    static {
        mapper.put(
            RequestTypeMatcher.of(GET, ""),
            (request, response) -> {
                response.getResponse(request);
                return response;
            }
        );
        mapper.put(
            RequestTypeMatcher.of(POST, "/user/create"),
            (request, response) -> {
                User user = request.getBody(User.class);
                DataBase.addUser(user);

                response.redirectTo(request, "http://localhost:8080/index.html ");
                return response;
            }
        );
    }

    public static BiFunction<Request, Response, Response> mapping(RequestType requestType) {
        return mapper.get(RequestTypeMatcher.of(requestType));
    }
}
