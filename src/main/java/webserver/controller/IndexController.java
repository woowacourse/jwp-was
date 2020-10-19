package webserver.controller;

import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;
import webserver.request.MethodType;
import webserver.response.ModelAndView;
import webserver.response.StatusCode;

@Controller
public class IndexController {

    @RequestMapping(type = MethodType.GET, value = {"/", "/index"})
    public ModelAndView index() {

        return ModelAndView.of(StatusCode.OK, "index");
    }
}
