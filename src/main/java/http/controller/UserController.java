package http.controller;

import http.model.request.ServletRequest;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import model.User;
import model.UserDto;

public class UserController extends AbstractController {
    public UserController(RequestMapping mapping) {
        super(mapping);
    }

    public UserController(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {
        UserDto userDto = new UserDto(servletRequest.getParameters());
        User user = userDto.toEntity();

        servletResponse.redirect("/index.html");
    }
}
