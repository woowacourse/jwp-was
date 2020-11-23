package controller;

import java.util.List;
import request.HttpRequest;

public interface ControllerMapperInterface {

    // Todo: Class<Controller> 이렇게 하는게 더 좋아보이는데 왜 안되는가...
    Class findController(HttpRequest httpRequest);

    List<Class> readAllControllerInfo();
}
