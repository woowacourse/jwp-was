package controller;

import db.DataBase;
import model.UserAssembler;
import utils.UrlEncodedParser;
import webserver.Request;
import webserver.Response;

public class SignUpController extends AbstractController {

    private static final String USER_CREATE_URL = "/user/create";

    @Override
    public Response doGet(Request request) {
        throw createUnsupportedException();
    }

    @Override
    public Response doPost(Request request) {
        DataBase.addUser(UserAssembler.toEntity(UrlEncodedParser.parse(new String(request.getBody()))));
        return Response.ResponseBuilder.redirect("/index.html")
                .build();
    }

    @Override
    public String getPath() {
        return USER_CREATE_URL;
    }
}
