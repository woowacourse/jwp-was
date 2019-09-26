package webserver.controller;

import webserver.utils.FilePathUtils;
import webserver.exception.MethodNotAllowedException;
import webserver.response.MediaType;

import java.util.HashMap;
import java.util.Map;

import static webserver.controller.RequestMapping.getMapping;

public class ControllerHandler {
    private Map<RequestMapping, Responsive> controllerMapping;

    public ControllerHandler() {
        this.controllerMapping = new HashMap<>();

        // static file controller
        controllerMapping.put(getMapping("/*"), (request, response) -> {
            String path = request.getPath();
            response.forward(path);

            String extension = FilePathUtils.getExtension(path);
            response.setContentType(MediaType.of(extension));
        });
    }

    public void put(RequestMapping requestMapping, Responsive responsive) {
        controllerMapping.put(requestMapping, responsive);
    }

    public boolean containsKey(RequestMapping requestMapping) {
        return controllerMapping.containsKey(requestMapping);
    }

    public Responsive get(RequestMapping requestMapping) {
        if (containsKey(requestMapping)) {
            return controllerMapping.get(requestMapping);
        }
        if (containsUri(requestMapping)) {
            throw new MethodNotAllowedException("지원하지 않는 method");
        }
        return controllerMapping.get(getMapping("/*"));
    }

    private boolean containsUri(RequestMapping requestMapping) {
        return controllerMapping.keySet().stream()
                .anyMatch(rm -> rm.isSameUri(requestMapping));
    }
}
