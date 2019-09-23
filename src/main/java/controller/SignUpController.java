package controller;

import db.DataBase;
import model.UserAssembler;
import utils.UrlEncodedParser;
import webserver.Request;
import webserver.Response;

public class SignUpController {

    public static final String USER_CREATE_URL = "/user/create";

    public static Response signUp(Request req) {
        DataBase.addUser(UserAssembler.toEntity(UrlEncodedParser.parse(new String(req.getBody()))));

        return Response.ResponseBuilder.redirect("/index.html")
                .build();
    }
}
