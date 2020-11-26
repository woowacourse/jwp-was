package controller;

import java.util.Collections;
import java.util.List;
import request.HttpRequest;

public class ControllerMapperForTest implements ControllerMapperInterface {

    @Override
    public Class findController(HttpRequest httpRequest) {
        return ControllerForTest.class;
    }

    @Override
    public List<Class> readAllControllerInfo() {
        return Collections.singletonList(ControllerForTest.class);
    }
}
