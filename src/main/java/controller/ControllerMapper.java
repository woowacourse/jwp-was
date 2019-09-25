package controller;

import controller.exception.NotFoundUrlException;
import model.http.HttpRequest;

public class ControllerMapper {
    public static Controller mapping(HttpRequest httpRequest) {
        return Controllers.LIST.stream()
                .filter(target -> target.isMapping(httpRequest))
                .findFirst()
                .orElseThrow(NotFoundUrlException::new);
    }
}
