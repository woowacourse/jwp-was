package controller;

import db.DataBase;
import model.UserAssembler;
import utils.UrlEncodedParser;
import webserver.Request;
import webserver.Response;

public class SignUpController {

    public static Response doPost(Request request) {
        DataBase.addUser(UserAssembler.toEntity(UrlEncodedParser.parse(new String(request.getBody()))));
        return Response.ResponseBuilder.redirect("/index.html")
                .build();
    }
}
