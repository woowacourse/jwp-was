package controller;

import db.DataBase;
import http.ModelAndView;
import http.request.Request;
import http.response.Response;
import http.support.HttpStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {

    private static final String MODEL_NAME = "users";
    private static final String USER_LIST = "/user/list";

    @Override
    public void doGet(Request request, Response response) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Collection> map = new HashMap<>();
        map.put(MODEL_NAME, DataBase.findAll());
        modelAndView.setView(USER_LIST);
        modelAndView.addAllObjects(map);
        String apply = modelAndView.render();
        response.setHttpStatus(HttpStatus.OK);
        response.setBody(apply.getBytes());
        response.forward(USER_LIST, HttpStatus.OK);
    }
}
