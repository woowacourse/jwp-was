package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;
import webserver.ServerErrorException;

import java.util.Map;

public class SignUpController extends BasicController {
    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);

    @Override
    public ModelAndView doGet(HttpRequest request, HttpResponse response) {
        if (request.hasParameters()) {
            DataBase.addUser(createUser(request));
            return new ModelAndView("/index.html");
        }
        throw new ServerErrorException("파라미터가 필요합니다.");
    }

    @Override
    public ModelAndView doPost(HttpRequest request, HttpResponse response) {
        log.debug("{}", request.hasBody());

        if (request.hasBody()) {
            DataBase.addUser(createUser(request.convertBodyToMap()));
            return new ModelAndView("/index.html");
        }
        throw new ServerErrorException("바디가 필요합니다.");
    }

    private User createUser(HttpRequest request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        return new User(userId, password, name, email);
    }

    private User createUser(Map<String, String> bodyData) {
        String userId = bodyData.get("userId");
        String password = bodyData.get("password");
        String name = bodyData.get("name");
        String email = bodyData.get("email");

        return new User(userId, password, name, email);
    }
}
