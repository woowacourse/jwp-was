package webserver.controller;

import java.util.Collections;
import java.util.LinkedHashMap;

import com.google.common.collect.Maps;
import webserver.MethodType;
import webserver.ModelAndView;
import webserver.RequestHeader;
import webserver.ServletResponse;
import webserver.StatusCode;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;

@Controller
public class IndexController implements Handlers {

    @RequestMapping(type = MethodType.GET, value = {"/", "/index"})
    public ModelAndView index() {

        return ModelAndView.of(StatusCode.OK, Maps.newLinkedHashMap(), "index");
    }
}
