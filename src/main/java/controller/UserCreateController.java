package controller;

import static http.RequestMethod.*;
import static http.ResponseHeader.*;

import java.io.DataOutputStream;
import java.util.Map;

import db.DataBase;
import http.Request;
import mapper.QueryParams;
import model.User;

public class UserCreateController implements Controller {
    @Override
    public void service(Request request, DataOutputStream dos) {
        if (request.isMethod(GET)) {
            doGet(request, dos);
        } else if (request.isMethod(POST)) {
            doPost(request, dos);
        }
    }

    public void doGet(Request request, DataOutputStream dos) {
        String requestUrl = request.getRequestLine().getUrl();
        QueryParams queryParams = new QueryParams(requestUrl);
        Map<String, String> queryParamsMap = queryParams.getQueryParams();

        if (!queryParamsMap.isEmpty()) {
            User user = new User(queryParamsMap.get("userId"), queryParamsMap.get("password"),
                    queryParamsMap.get("name"), queryParamsMap.get("email"));
            DataBase.addUser(user);
            response302Header(dos, "/index.html");
        }
    }

    public void doPost(Request request, DataOutputStream dos) {
        Map<String, String> requestBodies = request.getRequestBody().getRequestBodies();
        User user = new User(requestBodies.get("userId"), requestBodies.get("password"),
                requestBodies.get("name"), requestBodies.get("email"));
        DataBase.addUser(user);

        response302Header(dos, "/index.html");
    }
}
