package server.controller;

import db.DataBase;
import model.User;
import server.web.controller.Controller;
import server.web.controller.RequestMapping;
import server.web.request.HttpMethod;
import server.web.request.HttpRequest;
import server.web.request.RequestBody;
import server.web.response.HttpResponse;
import server.web.view.HandlebarView;
import server.web.view.ModelAndView;

@RequestMapping(uri = "/user/create", httpMethod = HttpMethod.POST)
public class UserCreateController implements Controller {
    @Override
    public ModelAndView doService(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        createUser(requestBody);

        return ModelAndView.view(new HandlebarView("redirect:/index.html"));
    }

    private void createUser(RequestBody requestBody) {
        User user = new User(requestBody.getParameter("userId"),
                requestBody.getParameter("password"),
                requestBody.getParameter("name"),
                requestBody.getParameter("email"));
        DataBase.addUser(user);
    }
}
