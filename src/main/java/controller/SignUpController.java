package controller;

import db.DataBase;
import model.UserAssembler;
import utils.UrlEncodedParser;
import webserver.HttpMethod;
import webserver.Request;
import webserver.RequestMapping;
import webserver.Response;

import java.util.HashMap;

public class SignUpController extends AbstractController {

    private static final String USER_CREATE_URL = "/user/create";

    public SignUpController() {
        methods = new HashMap<>();
        methods.put(new RequestMapping(HttpMethod.POST, USER_CREATE_URL), this::doPost);
    }

    private Response doPost(Request request) {
        DataBase.addUser(UserAssembler.toEntity(UrlEncodedParser.parse(new String(request.getBody()))));
        return Response.ResponseBuilder.redirect("/index.html")
                .build();
    }
}
