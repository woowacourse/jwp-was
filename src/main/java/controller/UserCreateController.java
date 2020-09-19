package controller;

import java.util.Map;

import db.DataBase;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import model.User;
import model.UserDto;
import view.Model;
import view.ModelAndView;
import view.View;

public class UserCreateController extends HttpRequestMappingAbstractController {

    public UserCreateController(HttpRequestMapping httpRequestMapping) {
        super(httpRequestMapping);
    }

    @Override
    public ModelAndView handle(HttpRequest httpRequest) {
        Map<String, String> parsedBody = httpRequest.getRequestBody().parseBody();
        UserDto userDto = UserDto.from(parsedBody);
        User user = userDto.toEntity();

        DataBase.addUser(user);

        Model model = new Model();
        model.addAttributes("user", user);
        View view = new View("redirect:/index.html");

        return ModelAndView.from(model, view);
    }
}
