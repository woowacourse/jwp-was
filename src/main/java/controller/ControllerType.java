package controller;

import java.util.Arrays;

public enum ControllerType {

    INDEX("/index.html", new IndexController()),
    FORM("/user/form.html", new FormController()),
    USER("/user/create", new UserController()),
    NONE("/", new HomeController());

    private final String uri;
    private final Controller controller;

    ControllerType(String uri, Controller controller) {
        this.uri = uri;
        this.controller = controller;
    }

    public static Controller find(String uri) {
        return Arrays.stream(ControllerType.values())
                .filter(uriType -> uriType.uri.equals(uri))
                .findFirst()
                .orElse(NONE)
                .controller;
    }
}
