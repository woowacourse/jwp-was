package http.controller;

import java.util.Map;

import db.DataBase;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.response.HttpResponse;
import model.User;
import model.UserDto;

public class UserCreateController extends HttpRequestMappingAbstractController {

    public UserCreateController(HttpRequestMapping httpRequestMapping) {
        super(httpRequestMapping);
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> parsedBody = httpRequest.getRequestBody().parseBody();
        UserDto userDto = UserDto.from(parsedBody);
        User user = userDto.toEntity();

        DataBase.addUser(user);

        httpResponse.redirect("/index.html");

        // Model model = new Model();
        // model.addAttributes("user", user);
        // View view = new View("redirect:/");
        // ModelAndView.of(model, view);
    }
}
