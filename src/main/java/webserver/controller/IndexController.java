package webserver.controller;

import java.util.LinkedHashMap;

import webserver.RequestHeader;
import webserver.ServletResponse;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;

@Controller
public class IndexController implements Handlers {

    @RequestMapping(type = RequestHeader.MethodType.GET, value = {"/", "/index"})
    public ServletResponse index() {
        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("View", "index");

        return new ServletResponse(ServletResponse.StatusCode.OK, response);
    }
}
