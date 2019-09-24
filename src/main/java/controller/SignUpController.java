package controller;

import db.DataBase;
import model.UserAssembler;
import utils.UrlEncodedParser;
import webserver.HttpMethod;
import webserver.Request;
import webserver.Response;

import java.util.HashMap;
import java.util.Map;

public class SignUpController implements Controller {

    private static final String USER_CREATE_URL = "/user/create";

    private static Map<HttpMethod, HandleHttpMethod> methods;

    static {
        methods = new HashMap<>();
        methods.put(HttpMethod.POST, HandleHttpMethod.POST);
    }

    @Override
    public Response service(Request request) {
        return methods.get(request.getMethod()).method(request);
    }

    @Override
    public String getPath() {
        return USER_CREATE_URL;
    }

    private enum HandleHttpMethod {
        POST {
            Response method(Request request) {
                DataBase.addUser(UserAssembler.toEntity(UrlEncodedParser.parse(new String(request.getBody()))));
                return Response.ResponseBuilder.redirect("/index.html")
                        .build();
            }
        };

        abstract Response method(Request request);
    }
}
