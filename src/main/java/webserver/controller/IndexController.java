package webserver.controller;

import com.google.common.collect.Maps;
import webserver.request.MethodType;
import webserver.response.ModelAndView;
import webserver.response.StatusCode;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;

@Controller
public class IndexController implements Handlers {

    @RequestMapping(type = MethodType.GET, value = {"/", "/index"})
    public ModelAndView index() {

        return ModelAndView.of(StatusCode.OK, Maps.newLinkedHashMap(), Maps.newLinkedHashMap(), "index");
    }
}
