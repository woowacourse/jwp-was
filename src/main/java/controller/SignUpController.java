package controller;

import db.DataBase;
import model.UserAssembler;
import utils.UrlEncodedParser;
import webserver.HttpMethod;
import webserver.Request;
import webserver.RequestMapping;
import webserver.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SignUpController implements Controller {

    private static final String USER_CREATE_URL = "/user/create";

    private static Map<RequestMapping, HandleHttpMethod> methods;

    static {
        methods = new HashMap<>();
        methods.put(new RequestMapping(HttpMethod.POST, USER_CREATE_URL), HandleHttpMethod.POST);
    }

    @Override
    public Response service(Request request) {
        return methods.get(request.getRequestMapping()).method(request);
    }

    @Override
    public Set<RequestMapping> getMethodKeys() {
        return methods.keySet();
    }

    @Override
    public boolean isMapping(Request request) {
        return methods.containsKey(request.getRequestMapping());
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
