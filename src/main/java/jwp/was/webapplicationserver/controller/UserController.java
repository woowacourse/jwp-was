package jwp.was.webapplicationserver.controller;

import static com.google.common.net.HttpHeaders.LOCATION;

import java.util.HashMap;
import java.util.Map;
import jwp.was.webapplicationserver.configure.annotation.Autowired;
import jwp.was.webapplicationserver.configure.annotation.Controller;
import jwp.was.webapplicationserver.configure.annotation.RequestMapping;
import jwp.was.webapplicationserver.controller.dto.UserRequest;
import jwp.was.webapplicationserver.controller.dto.UserRequestAssembler;
import jwp.was.webapplicationserver.service.UserService;
import jwp.was.webserver.HttpMethod;
import jwp.was.webserver.HttpStatusCode;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;

@Controller
public class UserController {

    private static final String INDEX_HTML = "/index.html";

    @Autowired
    private UserService userService;

    @RequestMapping(method = HttpMethod.POST, urlPath = "/user/create")
    public HttpResponse createUser(HttpRequest httpRequest) {
        UserRequest userRequest = UserRequestAssembler.assemble(httpRequest.getParameters());
        userService.createUser(userRequest);
        Map<String, String> headers = new HashMap<>();
        headers.put(LOCATION, INDEX_HTML);
        return HttpResponse.of(httpRequest.getHttpVersion(), HttpStatusCode.FOUND, headers, "");
    }
}
