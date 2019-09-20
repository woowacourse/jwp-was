package controller;

import db.DataBase;
import model.UserAssembler;
import utils.UrlEncodedParser;
import webserver.Request;
import webserver.Response;
import webserver.Status;

public class UserController {

    public static final String USER_CREATE_URL = "/user/create";
    private static final String LOCATION_HEADER_KEY = "Location";

    public static Response signUp(Request req) {
        DataBase.addUser(UserAssembler.toEntity(UrlEncodedParser.parse(new String(req.getBody()))));

        return Response.ResponseBuilder.createBuilder()
                .withStatus(Status.FOUND)
                .withHeader(LOCATION_HEADER_KEY, "/index.html")
                .build();
    }
}
